package cn.com.xinxin.sass.biz.service.wechatwork;

import cn.com.xinxin.sass.repository.model.TenantDataSyncLogDO;

/**
 * @author: liuhangzhou
 * @created: 2020/4/22.
 * @updater:
 * @description: 企业微信同步成员服务
 */
public interface WeChatWorkMemberSyncService {
    /**
     * 同步部门成员
     *
     * @param departmentId 部门id
     * @param tenantDataSyncLogDO 租户同步日志
     */
    void syncMember(String departmentId, TenantDataSyncLogDO tenantDataSyncLogDO);
}
