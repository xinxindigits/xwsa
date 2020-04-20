package cn.com.xinxin.sass.sal.wechatwork;

import cn.com.xinxin.sass.sal.model.wechatwork.WeChatWorkCustomerBO;

import java.util.List;

/**
 * @author: liuhangzhou
 * @created: 2020/4/20.
 * @updater:
 * @description: 企业微信客户信息管理Service
 */
public interface WeChatWorkCustomerClient {

    /**
     * 根据成员的userID查询客户的userID
     * @param token 企业微信token
     * @param userId 成员的userID
     * @return 客户的userID
     */
    List<String> queryCustomerUserId(String token, String userId);

    /**
     * 根据客户的userID查询客户的详细信息
     * @param token 企业微信token
     * @param customerUserId 客户的userID
     * @return 客户的详细信息
     */
    WeChatWorkCustomerBO queryCustomerDetail(String token, String customerUserId);
}
