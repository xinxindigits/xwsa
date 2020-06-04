package cn.com.xinxin.sass.biz.convert;

/*
 *
 * Copyright 2020 www.xinxindigits.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software
 * and associated documentation files (the "Software"),to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice
 * shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 * Redistribution and selling copies of the software are prohibited, only if the authorization from xinxin digits
 * was obtained.Neither the name of the xinxin digits; nor the names of its contributors may be used to
 * endorse or promote products derived from this software without specific prior written permission.
 *
 */

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
     * @param memberUserId 成员memberUserId
     * @return CustomerReceivedDO
     */
    public static CustomerReceivedDO convert2CustomerReceivedDO(WeChatWorkCustomerBO weChatWorkCustomerBO,
                                                                String taskId, String orgId, String memberUserId) {
        CustomerReceivedDO customerReceivedDO = new CustomerReceivedDO();
        customerReceivedDO.setMemberUserId(memberUserId);
        customerReceivedDO.setTaskId(taskId);
        customerReceivedDO.setTenantId(orgId);
        if (null != weChatWorkCustomerBO) {
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
        }
        customerReceivedDO.setGmtCreator(CommonConstants.GMT_CREATOR_SYSTEM);
        return customerReceivedDO;
    }

    /**
     * 将weChatWorkCustomerBOList转化为CustomerReceivedDOList
     * @param weChatWorkCustomerBOS 企业微信客户BOList
     * @param taskId 任务id
     * @param orgId 机构id
     * @param memberUserId 成员memberUserId
     * @return CustomerReceivedDOList
     */
    public static List<CustomerReceivedDO> convert2CustomerReceivedDOList(List<WeChatWorkCustomerBO> weChatWorkCustomerBOS,
                                                                          String taskId,
                                                                          String orgId,
                                                                          String memberUserId) {
        List<CustomerReceivedDO> customerReceivedDOS = new ArrayList<>();
        weChatWorkCustomerBOS.forEach(c -> customerReceivedDOS.add(convert2CustomerReceivedDO(c, taskId, orgId, memberUserId)));
        return customerReceivedDOS;
    }

}
