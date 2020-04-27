package cn.com.xinxin.sass.biz.service;


import cn.com.xinxin.sass.repository.model.OrgDataSyncLogDO;

/**
 * @author: liuhangzhou
 * @created: 2020/4/27.
 * @updater:
 * @description: 机构同步任务日志数据库服务
 */
public interface OrgDataSyncLogService {

    /**
     * 根据id进行更新
     * @param orgDataSyncLogDO 记录
     * @return 更新记录成功的条数
     */
    int updateById(OrgDataSyncLogDO orgDataSyncLogDO);

    /**
     * 插入记录并将生成的id保存到orgDataSyncLogDO的id字段
     * @param orgDataSyncLogDO 机构同步任务日志
     * @return 插入记录成功的条数
     */
    int insertReturnId(OrgDataSyncLogDO orgDataSyncLogDO);
}
