package cn.com.xinxin.sass.sal.wechatwork;


import cn.com.xinxin.sass.sal.model.wechatwork.WeChatWorkUserBO;

import java.util.List;

/**
 * @author: liuhangzhou
 * @created: 2020/4/20.
 * @updater:
 * @description: 企业微信成员信息管理Service
 */
public interface WeChatWorkUserClient {

    /**
     * 查询用户信息列表
     * @param token 企业微信token
     * @param departmentId 部门列表
     * @return 用户信息列表
     */
    List<WeChatWorkUserBO> queryUserList(String token, Integer departmentId);
}
