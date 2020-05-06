package cn.com.xinxin.sass.biz.service;


import cn.com.xinxin.sass.repository.model.TenantDataSyncLogDO;

/**
 * @author: liuhangzhou
 * @created: 2020/4/27.
 * @updater:
 * @description: 机构同步任务日志数据库服务
 */
public interface TenantDataSyncLogService {

    /**
     * 根据id进行更新
     * @param tenantDataSyncLogDO 记录
     * @return 更新记录成功的条数
     */
    int updateById(TenantDataSyncLogDO tenantDataSyncLogDO);

    /**
     * 插入记录并将生成的id保存到orgDataSyncLogDO的id字段
     * @param tenantDataSyncLogDO 机构同步任务日志
     * @return 插入记录成功的条数
     */
    int insertReturnId(TenantDataSyncLogDO tenantDataSyncLogDO);
}
