package cn.com.xinxin.sass.biz.service;

import cn.com.xinxin.sass.repository.model.DepartmentReceivedDO;

import java.util.List;

/**
 * @author: liuhangzhou
 * @created: 2020/4/21.
 * @updater:
 * @description: 部门信息暂存表数据库服务
 */
public interface DepartmentReceivedService {

    /**
     * 批量插入记录
     * @param departmentReceivedDOS 部门待导入表
     * @return 插入成功条数
     */
    int insertBatch(List<DepartmentReceivedDO> departmentReceivedDOS);

    /**
     * 通过任务id和机构id查询部门信息
     * @param taskId 任务id
     * @param orgId 机构id
     * @return 部门信息
     */
    List<DepartmentReceivedDO> selectByTaskIdAndOrgId(String taskId, String orgId);

    /**
     * 分批批量插入记录
     * @param departmentReceivedDOS 部门待导入表
     * @param size 每次插入的数量
     */
    void insertBatchPartially(List<DepartmentReceivedDO> departmentReceivedDOS, int size);
}
