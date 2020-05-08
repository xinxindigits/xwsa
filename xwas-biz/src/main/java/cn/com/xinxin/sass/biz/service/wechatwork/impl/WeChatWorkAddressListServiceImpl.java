package cn.com.xinxin.sass.biz.service.wechatwork.impl;

import cn.com.xinxin.sass.biz.convert.CustomerConvert;
import cn.com.xinxin.sass.biz.convert.DepartmentConvert;
import cn.com.xinxin.sass.biz.convert.MemberConvert;
import cn.com.xinxin.sass.biz.service.CustomerReceivedService;
import cn.com.xinxin.sass.biz.service.DepartmentReceivedService;
import cn.com.xinxin.sass.biz.service.MemberReceivedService;
import cn.com.xinxin.sass.biz.service.wechatwork.WeChatWorkAddressListService;
import cn.com.xinxin.sass.common.constants.CommonConstants;
import cn.com.xinxin.sass.common.enums.SassBizResultCodeEnum;
import cn.com.xinxin.sass.repository.model.CustomerReceivedDO;
import cn.com.xinxin.sass.repository.model.DepartmentReceivedDO;
import cn.com.xinxin.sass.repository.model.MemberReceivedDO;
import cn.com.xinxin.sass.sal.model.wechatwork.WeChatWorkCustomerBO;
import cn.com.xinxin.sass.sal.model.wechatwork.WeChatWorkDepartmentBO;
import cn.com.xinxin.sass.sal.model.wechatwork.WeChatWorkUserBO;
import cn.com.xinxin.sass.sal.wechatwork.WeChatWorkCustomerClient;
import cn.com.xinxin.sass.sal.wechatwork.WeChatWorkDepartmentClient;
import cn.com.xinxin.sass.sal.wechatwork.WeChatWorkInteractionClient;
import cn.com.xinxin.sass.sal.wechatwork.WeChatWorkUserClient;
import com.xinxinfinance.commons.exception.BusinessException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author: liuhangzhou
 * @created: 2020/4/21.
 * @updater:
 * @description: 企业微信通讯录相关服务
 */
@Service
@Deprecated
public class WeChatWorkAddressListServiceImpl implements WeChatWorkAddressListService {

    private static final Logger LOGGER = LoggerFactory.getLogger(WeChatWorkAddressListServiceImpl.class);

    private final WeChatWorkInteractionClient weChatWorkInteractionClient;
    private final WeChatWorkDepartmentClient weChatWorkDepartmentClient;
    private final WeChatWorkUserClient weChatWorkUserClient;
    private final WeChatWorkCustomerClient weChatWorkCustomerClient;
    private final CustomerReceivedService customerReceivedService;
    private final MemberReceivedService memberReceivedService;
    private final DepartmentReceivedService departmentReceivedService;

    public WeChatWorkAddressListServiceImpl(final WeChatWorkInteractionClient weChatWorkInteractionClient,
                                            final WeChatWorkDepartmentClient weChatWorkDepartmentClient,
                                            final WeChatWorkUserClient weChatWorkUserClient,
                                            final WeChatWorkCustomerClient weChatWorkCustomerClient,
                                            final CustomerReceivedService customerReceivedService,
                                            final MemberReceivedService memberReceivedService,
                                            final DepartmentReceivedService departmentReceivedService) {
        this.weChatWorkInteractionClient = weChatWorkInteractionClient;
        this.weChatWorkDepartmentClient = weChatWorkDepartmentClient;
        this.weChatWorkUserClient = weChatWorkUserClient;
        this.weChatWorkCustomerClient = weChatWorkCustomerClient;
        this.customerReceivedService = customerReceivedService;
        this.memberReceivedService = memberReceivedService;
        this.departmentReceivedService = departmentReceivedService;
    }

    /**
     * 获取并导入通讯录数据到待导入表
     *
     * @param corporationId 机构id
     * @param addressListSecret 通讯录应用Secret
     * @param customerContactSecret 客户联系应用Secret
     * @param taskId 任务id
     * @param orgId 机构id
     */
    @Override
    public void fetchAndImportAddressList(String corporationId, String addressListSecret, String customerContactSecret,
                                          String taskId, String orgId) {
        //参数检查
        checkParam(corporationId, addressListSecret, customerContactSecret, taskId, orgId);

        //获取通讯录应用api所需要的token
        String addressListToken = weChatWorkInteractionClient.fetchToken(corporationId, addressListSecret);

        //获取部门列表
        List<WeChatWorkDepartmentBO> weChatWorkDepartmentBOS = weChatWorkDepartmentClient.queryDepartmentList(addressListToken);
        List<DepartmentReceivedDO> departmentReceivedDOS = DepartmentConvert.convert2DepartmentReceivedDOList(
                weChatWorkDepartmentBOS, taskId, orgId);
        departmentReceivedService.insertBatchPartially(departmentReceivedDOS, CommonConstants.ONE_HUNDRED);

        //获取成员列表
        List<WeChatWorkUserBO> weChatWorkUserBOS = weChatWorkUserClient.queryUserList(addressListToken,
                weChatWorkDepartmentBOS.stream().map(WeChatWorkDepartmentBO::getDepartmentId).collect(Collectors.toList()));
        List<MemberReceivedDO> memberReceivedDOS = MemberConvert.convert2MemberReceivedDOList(
                weChatWorkUserBOS, taskId, orgId);
        memberReceivedService.insertBatchPartially(memberReceivedDOS.stream()
                .filter(distinctByKey(m -> (m.getUserId()))).collect(Collectors.toList()), CommonConstants.ONE_HUNDRED);

        //获取外部联系人管理应用api所需要的token
        String customerContactToken =  weChatWorkInteractionClient.fetchToken(corporationId, customerContactSecret);

        //获取客户列表
        List<CustomerReceivedDO> customerReceivedDOS = new ArrayList<>();
        weChatWorkUserBOS.forEach(u -> {
            List<String> customerUserIdS = weChatWorkCustomerClient.queryCustomerUserId(customerContactToken, u.getUserId());
            List<WeChatWorkCustomerBO> weChatWorkCustomerBOS = weChatWorkCustomerClient.queryCustomerDetail(
                    customerContactToken, customerUserIdS);
            customerReceivedDOS.addAll(CustomerConvert.convert2CustomerReceivedDOList(
                    weChatWorkCustomerBOS, taskId, orgId, u.getUserId()));
        });
        customerReceivedService.insertBatchPartially(customerReceivedDOS.stream()
                .filter(distinctByKey(m -> (m.getUserId()))).collect(Collectors.toList()), CommonConstants.ONE_HUNDRED);
    }

    /**
     * 参数检查
     *
     * @param corporationId 机构id
     * @param addressListSecret 通讯录应用Secret
     * @param customerContactSecret 客户联系应用Secret
     * @param taskId 任务id
     * @param orgId 机构id
     */
    private void checkParam(String corporationId, String addressListSecret, String customerContactSecret,
            String taskId, String orgId) {
        if (StringUtils.isBlank(corporationId)) {
            LOGGER.error("获取并导入企业微信通讯录，corporationId不能为空");
            throw new BusinessException(SassBizResultCodeEnum.ILLEGAL_PARAMETER, "获取并导入企业微信通讯录，corporationId不能为空");
        }
        if (StringUtils.isBlank(addressListSecret)) {
            LOGGER.error("获取并导入企业微信通讯录，addressListSecret不能为空");
            throw new BusinessException(SassBizResultCodeEnum.ILLEGAL_PARAMETER, "获取并导入企业微信通讯录，addressListSecret不能为空");
        }
        if (StringUtils.isBlank(customerContactSecret)) {
            LOGGER.error("获取并导入企业微信通讯录，customerContactSecret不能为空");
            throw new BusinessException(SassBizResultCodeEnum.ILLEGAL_PARAMETER, "获取并导入企业微信通讯录，customerContactSecret不能为空");
        }

        if (StringUtils.isBlank(taskId)) {
            LOGGER.error("获取并导入企业微信通讯录，taskId不能为空");
            throw new BusinessException(SassBizResultCodeEnum.ILLEGAL_PARAMETER, "获取并导入企业微信通讯录，taskId不能为空");
        }

        if (StringUtils.isBlank(orgId)) {
            LOGGER.error("获取并导入企业微信通讯录，orgId不能为空");
            throw new BusinessException(SassBizResultCodeEnum.ILLEGAL_PARAMETER, "获取并导入企业微信通讯录，orgId不能为空");
        }
    }

    private  <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor) {
        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }
}
