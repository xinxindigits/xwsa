package cn.com.xinxin.sass.biz.service;


import cn.com.xinxin.sass.common.model.PageResultVO;
import cn.com.xinxin.sass.repository.model.TenantDataSyncLogDO;
import cn.com.xinxin.sass.repository.model.bo.TenantDataSyncLogBO;

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


    /**
     * 分页查询租户任务执行日志
     * @param tenantDataSyncLogBO 参数
     * @param page 分页参数
     * @return 日志
     */
    PageResultVO<TenantDataSyncLogDO> selectRecordSPage(TenantDataSyncLogBO tenantDataSyncLogBO, PageResultVO page);
}
