package cn.com.xinxin.sass.biz.service;

import cn.com.xinxin.sass.repository.model.TenantDataSyncConfigDO;

/**
 * @author: liuhangzhou
 * @created: 2020/4/27.
 * @updater:
 * @description: 机构同步配置信息DB服务
 */
public interface TenantDataSyncConfigService {

    /**
     * 通过机构id和任务类型查询记录
     * @param orgId 机构id
     * @param taskType 任务类型
     * @return 机构同步配置信息
     */
    TenantDataSyncConfigDO selectByOrgIdAndTaskType(String orgId, String taskType);

    /**
     * 根据id更新记录
     * @param tenantDataSyncConfigDO 记录
     * @return 更新成功的条数
     */
    int updateById(TenantDataSyncConfigDO tenantDataSyncConfigDO);
}
