package cn.com.xinxin.sass.biz.service;

import cn.com.xinxin.sass.repository.model.TenantDataSyncConfigDO;

import java.util.List;

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

    /**
     * 上锁
     * @param tenantId 租户id
     * @param taskType 任务类型
     * @return 成功更新记录条数
     */
    int updateLockByTenantIdAndTaskType(String tenantId, String taskType);

    /**
     * 解锁
     * @param tenantId 租户id
     * @param taskType 任务类型
     * @return 成功更新记录条数
     */
    int updateUnLockByTenantIdAndTaskType(String tenantId, String taskType);

    /**
     * 查询所有未被删除的记录
     * @return 未被删除的记录
     */
    List<TenantDataSyncConfigDO> queryValidRecord();

    /**
     * 通过机构id查询记录
     * @param tenantId
     * @return
     */
    List<TenantDataSyncConfigDO> selectByTenantId(String tenantId);
}
