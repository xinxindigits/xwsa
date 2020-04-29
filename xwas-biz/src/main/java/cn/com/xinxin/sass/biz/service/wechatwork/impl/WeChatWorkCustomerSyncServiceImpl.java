package cn.com.xinxin.sass.biz.service.wechatwork.impl;

import cn.com.xinxin.sass.biz.service.CustomerReceivedService;
import cn.com.xinxin.sass.biz.service.CustomerService;
import cn.com.xinxin.sass.biz.service.wechatwork.WeChatWorkCustomerSyncService;
import cn.com.xinxin.sass.common.constants.CommonConstants;
import cn.com.xinxin.sass.common.enums.AddressListEnum;
import cn.com.xinxin.sass.common.enums.SassBizResultCodeEnum;
import cn.com.xinxin.sass.repository.model.CustomerDO;
import cn.com.xinxin.sass.repository.model.CustomerReceivedDO;
import com.xinxinfinance.commons.exception.BusinessException;
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
 * @created: 2020/4/24.
 * @updater:
 * @description: 企业微信同步客户服务
 */
@Service
public class WeChatWorkCustomerSyncServiceImpl implements WeChatWorkCustomerSyncService {

    private static final Logger LOGGER = LoggerFactory.getLogger(WeChatWorkCustomerSyncServiceImpl.class);

    private final CustomerReceivedService customerReceivedService;
    private final CustomerService customerService;

    public WeChatWorkCustomerSyncServiceImpl(final CustomerReceivedService customerReceivedService,
                                             final CustomerService customerService) {
        this.customerReceivedService = customerReceivedService;
        this.customerService = customerService;
    }

    /**
     * 同步客户信息
     * @param orgId 机构id
     * @param taskId 任务Id
     * @param memberUserIdS 成员userId列表
     */
    @Override
    public void syncCustomer(String orgId, String taskId, List<String> memberUserIdS) {
        if (StringUtils.isBlank(taskId)) {
            LOGGER.error("同步客户信息，taskId不能为空");
            throw new BusinessException(SassBizResultCodeEnum.ILLEGAL_PARAMETER, "同步客户信息，taskId不能为空");
        }
        if (CollectionUtils.isEmpty(memberUserIdS)) {
            LOGGER.error("同步客户信息，memberUserIdS不能为空");
            throw new BusinessException(SassBizResultCodeEnum.ILLEGAL_PARAMETER, "同步客户信息，memberUserIdS不能为空");
        }

        //根据成员列表分页同步客户信息
        Long startId = 0L;
        int count = 0;
        while (true) {
            //客户暂存信息
            List<CustomerReceivedDO> customerReceivedDOS = customerReceivedService.selectByTaskIdMemberUserIdS(
                    taskId, memberUserIdS, startId, CommonConstants.PAGE_SIZE);

            if (CollectionUtils.isEmpty(customerReceivedDOS)) {
                LOGGER.info("查询客户信息为0，当前已处理[{}]条记录", count);
                break;
            }

            //客户信息
            List<CustomerDO> customerDOS = customerService.selectByOrgIdAndUserId(orgId,
                    customerReceivedDOS.stream().map(CustomerReceivedDO::getUserId).collect(Collectors.toList()));

            //处理记录
            handleRecordS(customerReceivedDOS, customerDOS);

            //记录总数
            count = count + customerReceivedDOS.size();

            LOGGER.info("当前已处理[{}]条记录", count);

            startId = customerReceivedDOS.get(customerReceivedDOS.size() - 1).getId();
        }
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
        customerDO.setOrgId(customerReceivedDO.getOrgId());
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
