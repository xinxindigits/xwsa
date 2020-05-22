package cn.com.xinxin.sass.biz.service;

/*
 *
 * Copyright 2020 www.xinxindigits.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software
 * and associated documentation files (the "Software"),to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice
 * shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 * Redistribution and selling copies of the software are prohibited, only if the authorization from xinxin digits
 * was obtained.Neither the name of the xinxin digits; nor the names of its contributors may be used to
 * endorse or promote products derived from this software without specific prior written permission.
 *
 */

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


    /**
     * 部门列表
     * @param deptIds
     * @return
     */
    List<DepartmentDO> queryDeptsByIds(List<String> deptIds);
}
