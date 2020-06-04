package cn.com.xinxin.sass.biz.service.impl;

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

import cn.com.xinxin.sass.biz.service.DepartmentReceivedService;
import cn.com.xinxin.sass.common.CommonUtils;
import cn.com.xinxin.sass.repository.dao.DepartmentReceivedDOMapper;
import cn.com.xinxin.sass.repository.model.DepartmentReceivedDO;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: liuhangzhou
 * @created: 2020/4/21.
 * @updater:
 * @description: 部门信息暂存表数据库服务
 */
@Service
public class DepartmentReceivedServiceImpl implements DepartmentReceivedService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DepartmentReceivedServiceImpl.class);

    private final DepartmentReceivedDOMapper departmentReceivedDOMapper;

    public DepartmentReceivedServiceImpl(final DepartmentReceivedDOMapper departmentReceivedDOMapper) {
        this.departmentReceivedDOMapper = departmentReceivedDOMapper;
    }

    /**
     * 批量插入记录
     * @param departmentReceivedDOS 部门待导入表
     * @return 插入成功条数
     */
    @Override
    public int insertBatch(List<DepartmentReceivedDO> departmentReceivedDOS) {
        if (CollectionUtils.isEmpty(departmentReceivedDOS)) {
            LOGGER.warn("此次批量插入数据到部门信息暂存表，数据为空");
            return 0;
        }

        return departmentReceivedDOMapper.insertBatch(departmentReceivedDOS);
    }

    /**
     * 通过任务id和机构id查询部门信息
     * @param taskId 任务id
     * @param orgId 机构id
     * @return 部门信息
     */
    @Override
    public List<DepartmentReceivedDO> selectByTaskIdAndOrgId(String taskId, String orgId) {
        if (StringUtils.isBlank(taskId)) {
            LOGGER.error("通过任务id和机构id查询部门信息, taskId不能为空");
        }
        if (StringUtils.isBlank(orgId)) {
            LOGGER.error("通过任务id和机构id查询部门信息, orgId不能为空");
        }
        return departmentReceivedDOMapper.selectByTaskIdAndOrgId(taskId, orgId);
    }

    /**
     * 分批批量插入记录
     * @param departmentReceivedDOS 部门待导入表
     * @param size 每次插入的数量
     */
    @Override
    public void insertBatchPartially(List<DepartmentReceivedDO> departmentReceivedDOS, int size) {
        List<List<DepartmentReceivedDO>> departmentReceivedDOSList =  CommonUtils.split(departmentReceivedDOS, size);
        departmentReceivedDOSList.forEach(this::insertBatch);
    }
}
