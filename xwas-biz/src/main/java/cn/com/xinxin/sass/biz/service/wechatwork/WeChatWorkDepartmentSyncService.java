package cn.com.xinxin.sass.biz.service.wechatwork;

import cn.com.xinxin.sass.repository.model.TenantDataSyncLogDO;

/**
 * @author: liuhangzhou
 * @created: 2020/4/22.
 * @updater:
 * @description: 企业微信同步部门服务
 */
public interface WeChatWorkDepartmentSyncService {
    /**
     * 同步部门信息
     *
     * @param tenantDataSyncLogDO 租户同步日志
     */
    void syncDepartment(TenantDataSyncLogDO tenantDataSyncLogDO);
}
