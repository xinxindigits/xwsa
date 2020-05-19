package cn.com.xinxin.sass.biz.service.wechatwork.impl;

import cn.com.xinxin.sass.biz.convert.CustomerConvert;
import cn.com.xinxin.sass.biz.model.bo.WeChatWorkFetchDataBO;
import cn.com.xinxin.sass.biz.model.bo.WeChatWorkImportDataBO;
import cn.com.xinxin.sass.biz.service.CustomerReceivedService;
import cn.com.xinxin.sass.biz.service.CustomerService;
import cn.com.xinxin.sass.biz.service.wechatwork.WeChatWorkDataService;
import cn.com.xinxin.sass.common.CommonUtils;
import cn.com.xinxin.sass.common.constants.CommonConstants;
import cn.com.xinxin.sass.common.enums.AddressListEnum;
import cn.com.xinxin.sass.repository.model.CustomerDO;
import cn.com.xinxin.sass.repository.model.CustomerReceivedDO;
import cn.com.xinxin.sass.sal.model.wechatwork.WeChatWorkCustomerBO;
import cn.com.xinxin.sass.sal.wechatwork.WeChatWorkCustomerClient;
import cn.com.xinxin.sass.sal.wechatwork.WeChatWorkInteractionClient;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: liuhangzhou
 * @created: 2020/5/8.
 * @updater:
 * @description: 企业微信客户相关数据服务
 */
@Service(value = "weChatWorkCustomerDataServiceImpl")
public class WeChatWorkCustomerDataServiceImpl implements WeChatWorkDataService {

    private static final Logger LOGGER = LoggerFactory.getLogger(WeChatWorkCustomerDataServiceImpl.class);

    private final WeChatWorkInteractionClient weChatWorkInteractionClient;
    private final WeChatWorkCustomerClient weChatWorkCustomerClient;
    private final CustomerReceivedService customerReceivedService;
    private final CustomerService customerService;

    public WeChatWorkCustomerDataServiceImpl(final WeChatWorkInteractionClient weChatWorkInteractionClient,
                                             final WeChatWorkCustomerClient weChatWorkCustomerClient,
                                             final CustomerReceivedService customerReceivedService,
                                             final CustomerService customerService) {
        this.weChatWorkInteractionClient = weChatWorkInteractionClient;
        this.weChatWorkCustomerClient = weChatWorkCustomerClient;
        this.customerReceivedService = customerReceivedService;
        this.customerService = customerService;
    }

    /**
     * 获取客户相关数据
     * @param weChatWorkFetchDataBO 获取数据BO
     */
    @Override
    public void fetchData(WeChatWorkFetchDataBO weChatWorkFetchDataBO) {
        //获取外部联系人管理应用api所需要的token
        String token =  weChatWorkInteractionClient.fetchToken(
                weChatWorkFetchDataBO.getTenantBaseInfoDO().getCorpId(),
                weChatWorkFetchDataBO.getTenantBaseInfoDO().getCustomerContactSecret());

        //获取客户列表
        List<CustomerReceivedDO> customerReceivedDOS = new ArrayList<>();
        weChatWorkFetchDataBO.getWeChatWorkUserBOS().forEach(u -> {
            List<String> customerUserIdS = weChatWorkCustomerClient.queryCustomerUserId(token, u.getUserId());
            List<WeChatWorkCustomerBO> weChatWorkCustomerBOS = new ArrayList<>();
            customerUserIdS.forEach(c -> weChatWorkCustomerBOS.add(weChatWorkCustomerClient.queryCustomerDetail(token, c)));
            customerReceivedDOS.addAll(CustomerConvert.convert2CustomerReceivedDOList(
                    weChatWorkCustomerBOS, weChatWorkFetchDataBO.getTaskId(),
                    weChatWorkFetchDataBO.getTenantBaseInfoDO().getTenantId(), u.getUserId()));
        });

        //将数据保存在客户数据记录表中
        customerReceivedService.insertBatchPartially(customerReceivedDOS.stream()
                .filter(CommonUtils.distinctByKey(m -> (m.getUserId()))).collect(Collectors.toList()),
                CommonConstants.ONE_HUNDRED);
    }

    /**
     * 导入客户相关数据
     * @param weChatWorkImportDataBO 导入数据BO
     */
    @Override
    public void importData(WeChatWorkImportDataBO weChatWorkImportDataBO) {
        //根据成员列表分页同步客户信息
        Long startId = 0L;
        int count = 0;
        while (true) {
            //客户暂存信息
            List<CustomerReceivedDO> customerReceivedDOS = customerReceivedService.selectByTaskIdMemberUserIdS(
                    weChatWorkImportDataBO.getTenantDataSyncLogDO().getTaskId(),
                    weChatWorkImportDataBO.getMemberReceivedUserIdS(), startId,
                    CommonConstants.PAGE_SIZE);

            if (CollectionUtils.isEmpty(customerReceivedDOS)) {
                LOGGER.info("查询客户信息为0，当前已处理[{}]条记录", count);
                break;
            }

            //客户信息
            List<CustomerDO> customerDOS = customerService.selectByOrgIdAndUserId(
                    weChatWorkImportDataBO.getTenantDataSyncLogDO().getTenantId(),
                    customerReceivedDOS.stream().map(CustomerReceivedDO::getUserId).collect(Collectors.toList()));

            //处理记录
            handleRecordS(customerReceivedDOS, customerDOS);

            //记录总数
            count = count + customerReceivedDOS.size();

            LOGGER.info("当前已处理[{}]条记录", count);

            startId = customerReceivedDOS.get(customerReceivedDOS.size() - 1).getId();
        }

        //失效记录
        int inactiveCount = customerService.updateInactiveStatus(weChatWorkImportDataBO.getTenantDataSyncLogDO().getTenantId(),
                weChatWorkImportDataBO.getTenantDataSyncLogDO().getTaskId());

        weChatWorkImportDataBO.getTenantDataSyncLogDO().setCustomerCount(count + inactiveCount);
    }

    /**
     * 处理记录
     * @param customerReceivedDOS 客户暂存信息
     * @param customerDOS 客户信息
     */
    private void handleRecordS(List<CustomerReceivedDO> customerReceivedDOS, List<CustomerDO> customerDOS) {
        //待插入的记录
        List<CustomerDO> insertCustomerDOS = new ArrayList<>();
        //待更新的记录
        List<CustomerDO> updateCustomerDOS = new ArrayList<>();

        customerReceivedDOS.forEach(c -> {
            //获取userId相同的成员
            CustomerDO customerDO = customerDOS.stream()
                    .filter(t -> StringUtils.equals(c.getUserId(), t.getUserId()))
                    .findFirst().orElse(null);

            if (null == customerDO) {
                //如果找不到客户，将该条记录添加到insertCustomerDOS
                fetchInsertCustomer(c, insertCustomerDOS);
            } else {
                //如果客户信息有更新，将该条记录添加到updateCustomerDOS
                fetchUpdateCustomer(c, customerDO, updateCustomerDOS);
            }
        });

        //插入记录
        customerService.insertBatch(insertCustomerDOS);
        //更新记录
        customerService.updateBatch(updateCustomerDOS);
    }

    /**
     * 获取待插入的记录
     * @param customerReceivedDO 客户暂存信息
     * @param insertCustomerDOS 待插入的记录
     */
    private void fetchInsertCustomer(CustomerReceivedDO customerReceivedDO, List<CustomerDO> insertCustomerDOS) {
        CustomerDO customerDO = new CustomerDO();
        customerDO.setTenantId(customerReceivedDO.getTenantId());
        customerDO.setMemberUserId(customerReceivedDO.getMemberUserId());
        customerDO.setUserId(customerReceivedDO.getUserId());
        customerDO.setCustomerName(customerReceivedDO.getCustomerName());
        customerDO.setAvatar(customerReceivedDO.getAvatar());
        customerDO.setCustomerType(customerReceivedDO.getCustomerType());
        customerDO.setGender(customerReceivedDO.getGender());
        customerDO.setUnionId(customerReceivedDO.getUnionId());
        customerDO.setCustomerPosition(customerReceivedDO.getCustomerPosition());
        customerDO.setCorpName(customerReceivedDO.getCorpName());
        customerDO.setCorpFullName(customerReceivedDO.getCorpFullName());
        customerDO.setExternalProfile(customerReceivedDO.getExternalProfile());
        customerDO.setFollowUser(customerReceivedDO.getFollowUser());
        customerDO.setStatus(AddressListEnum.ACTIVE.getStatus());
        customerDO.setTaskId(customerReceivedDO.getTaskId());
        customerDO.setGmtCreator(CommonConstants.GMT_CREATOR_SYSTEM);
        LOGGER.info("同步客户信息，新增客户[{}]", customerDO.getCustomerName());
        insertCustomerDOS.add(customerDO);
    }

    /**
     * 获取待更新的记录
     * @param customerReceivedDO 客户暂存信息
     * @param customerDO 客户信息
     * @param updateCustomerDOS 待更新的客户信息
     */
    private void fetchUpdateCustomer(CustomerReceivedDO customerReceivedDO, CustomerDO customerDO,
                                     List<CustomerDO> updateCustomerDOS) {
        CustomerDO updateCustomerDO = new CustomerDO();
        int count = 0;
        if (!StringUtils.equals(customerReceivedDO.getMemberUserId(), customerDO.getMemberUserId())) {
            updateCustomerDO.setMemberUserId(customerReceivedDO.getMemberUserId());
            count++;
        }
        if (!StringUtils.equals(customerReceivedDO.getUserId(), customerDO.getUserId())) {
            updateCustomerDO.setUserId(customerReceivedDO.getUserId());
            count++;
        }
        if (!StringUtils.equals(customerReceivedDO.getCustomerName(), customerDO.getCustomerName())) {
            updateCustomerDO.setCustomerName(customerReceivedDO.getCustomerName());
            count++;
        }
        if (!StringUtils.equals(customerReceivedDO.getAvatar(), customerDO.getAvatar())) {
            updateCustomerDO.setAvatar(customerReceivedDO.getAvatar());
            count++;
        }
        if (null != customerReceivedDO.getCustomerType()
                && customerReceivedDO.getCustomerType().equals(customerDO.getCustomerType())) {
            updateCustomerDO.setCustomerType(customerReceivedDO.getCustomerType());
            count++;
        }
        if (null != customerReceivedDO.getGender()
                && customerReceivedDO.getGender().equals(customerDO.getGender())) {
            updateCustomerDO.setGender(customerReceivedDO.getGender());
            count++;
        }
        if (!StringUtils.equals(customerReceivedDO.getUnionId(), customerDO.getUnionId())) {
            updateCustomerDO.setUnionId(customerReceivedDO.getUnionId());
            count++;
        }
        if (!StringUtils.equals(customerReceivedDO.getCustomerPosition(), customerDO.getCustomerPosition())) {
            updateCustomerDO.setCustomerPosition(customerReceivedDO.getCustomerPosition());
            count++;
        }
        if (!StringUtils.equals(customerReceivedDO.getCorpName(), customerDO.getCorpName())) {
            updateCustomerDO.setCorpName(customerReceivedDO.getCorpName());
            count++;
        }
        if (!StringUtils.equals(customerReceivedDO.getCorpFullName(), customerDO.getCorpFullName())) {
            updateCustomerDO.setCorpFullName(customerReceivedDO.getCorpFullName());
            count++;
        }
        if (!StringUtils.equals(customerReceivedDO.getExternalProfile(), customerDO.getExternalProfile())) {
            updateCustomerDO.setExternalProfile(customerReceivedDO.getExternalProfile());
            count++;
        }
        if (!StringUtils.equals(customerReceivedDO.getFollowUser(), customerDO.getFollowUser())) {
            updateCustomerDO.setFollowUser(customerReceivedDO.getFollowUser());
            count++;
        }
        if (StringUtils.equals(customerDO.getStatus(), AddressListEnum.INACTIVE.getStatus())) {
            updateCustomerDO.setStatus(AddressListEnum.ACTIVE.getStatus());
            count++;
        }

        if (count > 0) {
            updateCustomerDO.setId(customerDO.getId());
            updateCustomerDO.setTaskId(customerReceivedDO.getTaskId());
            updateCustomerDO.setGmtUpdater(CommonConstants.GMT_CREATOR_SYSTEM);
            LOGGER.info("此次同步客户列表，客户[{}]更新[{}]个属性", customerReceivedDO.getCustomerName(), count);
            updateCustomerDOS.add(updateCustomerDO);
        }
    }
}
