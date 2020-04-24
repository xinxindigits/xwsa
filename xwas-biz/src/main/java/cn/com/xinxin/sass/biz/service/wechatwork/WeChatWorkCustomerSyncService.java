package cn.com.xinxin.sass.biz.service.wechatwork;

import java.util.List;

/**
 * @author: liuhangzhou
 * @created: 2020/4/24.
 * @updater:
 * @description: 企业微信同步客户服务
 */
public interface WeChatWorkCustomerSyncService {

    /**
     * 同步客户信息
     * @param orgId 机构id
     * @param taskId 任务Id
     * @param memberUserIdS 成员userId列表
     */
    void syncCustomer(String orgId, String taskId, List<String> memberUserIdS);
}
