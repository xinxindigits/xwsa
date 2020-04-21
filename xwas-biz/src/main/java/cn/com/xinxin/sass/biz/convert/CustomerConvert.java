package cn.com.xinxin.sass.biz.convert;

import cn.com.xinxin.sass.common.constants.CommonConstants;
import cn.com.xinxin.sass.repository.model.CustomerReceivedDO;
import cn.com.xinxin.sass.sal.model.wechatwork.WeChatWorkCustomerBO;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: liuhangzhou
 * @created: 2020/4/21.
 * @updater:
 * @description: 客户转换类
 */
public class CustomerConvert {

    /**
     * 将weChatWorkCustomerBO转化为CustomerReceivedDO
     * @param weChatWorkCustomerBO 企业微信客户BO
     * @param taskId 任务id
     * @param orgId 机构id
     * @return CustomerReceivedDO
     */
    public static CustomerReceivedDO convert2CustomerReceivedDO(WeChatWorkCustomerBO weChatWorkCustomerBO,
                                                                String taskId, String orgId) {
        CustomerReceivedDO customerReceivedDO = new CustomerReceivedDO();
        customerReceivedDO.setTaskId(taskId);
        customerReceivedDO.setOrgId(orgId);
        customerReceivedDO.setUserId(weChatWorkCustomerBO.getExternalUserId());
        customerReceivedDO.setCustomerName(weChatWorkCustomerBO.getName());
        customerReceivedDO.setAvatar(weChatWorkCustomerBO.getAvatar());
        customerReceivedDO.setCustomerType(weChatWorkCustomerBO.getType());
        customerReceivedDO.setGender(weChatWorkCustomerBO.getGender());
        customerReceivedDO.setUnionId(weChatWorkCustomerBO.getUnionId());
        customerReceivedDO.setCustomerPosition(weChatWorkCustomerBO.getPosition());
        customerReceivedDO.setCorpName(weChatWorkCustomerBO.getCorporationName());
        customerReceivedDO.setCorpFullName(weChatWorkCustomerBO.getCorporationFullName());
        customerReceivedDO.setExternalProfile(weChatWorkCustomerBO.getExternalProfile());
        customerReceivedDO.setFollowUser(weChatWorkCustomerBO.getFollowUser());
        customerReceivedDO.setGmtCreator(CommonConstants.GMT_CREATOR_SYSTEM);
        return customerReceivedDO;
    }

    /**
     * 将weChatWorkCustomerBOList转化为CustomerReceivedDOList
     * @param weChatWorkCustomerBOS 企业微信客户BOList
     * @param taskId 任务id
     * @param orgId 机构id
     * @return CustomerReceivedDOList
     */
    public static List<CustomerReceivedDO> convert2CustomerReceivedDOList(List<WeChatWorkCustomerBO> weChatWorkCustomerBOS,
                                                                          String taskId, String orgId) {
        List<CustomerReceivedDO> customerReceivedDOS = new ArrayList<>();
        weChatWorkCustomerBOS.forEach(c -> customerReceivedDOS.add(convert2CustomerReceivedDO(c, taskId, orgId)));
        return customerReceivedDOS;
    }

}
