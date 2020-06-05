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
