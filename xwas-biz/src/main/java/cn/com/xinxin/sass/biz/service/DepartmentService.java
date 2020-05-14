package cn.com.xinxin.sass.biz.service;

import cn.com.xinxin.sass.repository.model.DepartmentDO;

import java.util.List;

/**
 * @author: liuhangzhou
 * @created: 2020/4/22.
 * @updater:
 * @description: 部门信息数据库服务
 */
public interface DepartmentService {
    /**
     * 通过机构id查询部门列表
     * @param orgId 机构id
     * @return 部门列表
     */
    List<DepartmentDO> selectByOrgId(String orgId);

    /**
     * 批量插入记录
     * @param departmentDOS 部门信息
     * @return 插入成功条数
     */
    int insertBatch(List<DepartmentDO> departmentDOS);

    /**
     * 批量更新记录
     * @param departmentDOS 部门信息
     * @return 更新成功条数
     */
    int updateBatchById(List<DepartmentDO> departmentDOS);


    /**
     *
     * @return
     */
    List<DepartmentDO>  listAllWechatDepts();


    /**
     * 查询微信部门ID
     * @param deptId
     * @param deptName
     * @param deptEngName
     * @return
     */
    List<DepartmentDO>  queryDeptsByNameOrId(String deptId,
                                             String deptName,
                                             String deptEngName);



    /**
     * 查询微信部门ID
     * @param deptId
     * @return
     */
    DepartmentDO  queryDeptByDeptId(String deptId);

    /**
     * 分批批量插入记录
     * @param departmentDOS 部门信息
     * @param size 大小
     */
    void insertBatchPartially(List<DepartmentDO> departmentDOS, int size);

    /**
     * 分批批量更新记录
     * @param departmentDOS 部门信息
     * @param size 大小
     */
    void updateBatchByIdPartially(List<DepartmentDO> departmentDOS, int size);

    /**
     * 将记录状态置为失效
     * @param tenantId 租户id
     * @param taskId 任务流水
     * @return 成功更新的条数
     */
    int updateInactiveStatus(String tenantId, String taskId);
}
