package cn.com.xinxin.sass.biz.service.wechatwork;

import cn.com.xinxin.sass.repository.model.TenantDataSyncLogDO;

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
     *
     * @param memberUserIdS 成员userId列表
     * @param tenantDataSyncLogDO 租户同步日志
     */
    void syncCustomer(List<String> memberUserIdS, TenantDataSyncLogDO tenantDataSyncLogDO);
}
