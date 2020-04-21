package cn.com.xinxin.sass.sal.wechatwork;

import cn.com.xinxin.sass.sal.model.wechatwork.WeChatWorkDepartmentBO;

import java.util.List;

/**
 * @author: liuhangzhou
 * @created: 2020/4/16.
 * @updater:
 * @description: 企业微信部门信息管理Service
 */
public interface WeChatWorkDepartmentClient {

    /**
     * 向企业微信查询部门列表
     * @param token 企业微信token
     * @return 部门列表
     */
    List<WeChatWorkDepartmentBO> queryDepartmentList(String token);
}
