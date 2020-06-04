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

import cn.com.xinxin.sass.biz.service.DepartmentService;
import cn.com.xinxin.sass.common.CommonUtils;
import cn.com.xinxin.sass.common.enums.SassBizResultCodeEnum;
import cn.com.xinxin.sass.repository.dao.DepartmentDOMapper;
import cn.com.xinxin.sass.repository.model.DepartmentDO;
import com.xinxinfinance.commons.exception.BusinessException;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: liuhangzhou
 * @created: 2020/4/22.
 * @updater:
 * @description: 部门信息数据库服务
 */
@Service
public class DepartmentServiceImpl implements DepartmentService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DepartmentServiceImpl.class);

    private final DepartmentDOMapper departmentDOMapper;

    public DepartmentServiceImpl(final DepartmentDOMapper departmentDOMapper) {
        this.departmentDOMapper = departmentDOMapper;
    }

    /**
     * 通过机构id查询部门列表
     * @param orgId 机构id
     * @return 部门列表
     */
    @Override
    public List<DepartmentDO> selectByOrgId(String orgId) {
        if (StringUtils.isBlank(orgId)) {
            LOGGER.error("通过机构id查询部门列表, orgId不能为空");
            throw new BusinessException(SassBizResultCodeEnum.ILLEGAL_PARAMETER, "通过机构id查询部门列表, orgId不能为空");
        }
        return departmentDOMapper.selectByOrgId(orgId);
    }

    /**
     * 批量插入记录
     * @param departmentDOS 部门信息
     * @return 插入成功条数
     */
    @Override
    public int insertBatch(List<DepartmentDO> departmentDOS) {
        if (CollectionUtils.isEmpty(departmentDOS)) {
            LOGGER.warn("批量插入记录, 记录为空，请注意");
            return 0;
        }
        return departmentDOMapper.insertBatch(departmentDOS);
    }

    /**
     * 批量更新记录
     * @param departmentDOS 部门信息
     * @return 更新成功条数
     */
    @Override
    public int updateBatchById(List<DepartmentDO> departmentDOS) {
        if (CollectionUtils.isEmpty(departmentDOS)) {
            LOGGER.warn("批量更新记录, 记录为空，请注意");
            return 0;
        }
        return departmentDOMapper.updateBatchById(departmentDOS);
    }


    @Override
    public List<DepartmentDO> listAllWechatDepts() {


        List<DepartmentDO> departmentDOList = this.departmentDOMapper.listAllWechatDepts();

        if (CollectionUtils.isEmpty(departmentDOList)) {
            LOGGER.warn("DepartmentServiceImpl,listAllWechatDepts, is null"  );
            throw  new BusinessException(SassBizResultCodeEnum.DATA_NOT_EXIST,"查询不到任何数据");
        }
        return departmentDOList;
    }

    @Override
    public List<DepartmentDO> queryDeptsByNameOrId(String deptId, String deptName, String deptEngName) {


        List<DepartmentDO> departmentDOList = this.departmentDOMapper.queryDeptsByNameOrId(deptId,deptName,deptEngName);

        if (CollectionUtils.isEmpty(departmentDOList)) {
            LOGGER.warn("DepartmentServiceImpl,queryDeptsByNameOrId, is null"  );
            throw  new BusinessException(SassBizResultCodeEnum.DATA_NOT_EXIST,"查询不到任何数据");
        }

        return departmentDOList;


    }

    @Override
    public DepartmentDO queryDeptByDeptId(String deptId) {

        DepartmentDO departmentDO = this.departmentDOMapper.selectByDeptId(deptId);

        return departmentDO;
    }

    /**
     * 分批批量插入记录
     * @param departmentDOS 部门信息
     * @param size 大小
     */
    @Override
    public void insertBatchPartially(List<DepartmentDO> departmentDOS, int size) {
        List<List<DepartmentDO>> departmentDOSList = CommonUtils.split(departmentDOS, size);
        departmentDOSList.forEach(this::insertBatch);
    }

    /**
     * 分批批量更新记录
     * @param departmentDOS 部门信息
     * @param size 大小
     */
    @Override
    public void updateBatchByIdPartially(List<DepartmentDO> departmentDOS, int size) {
        List<List<DepartmentDO>> departmentDOSList = CommonUtils.split(departmentDOS, size);
        departmentDOSList.forEach(this::updateBatchById);
    }

    /**
     * 将记录状态置为失效
     * @param tenantId 租户id
     * @param taskId 任务流水
     * @return 成功更新的条数
     */
    @Override
    public int updateInactiveStatus(String tenantId, String taskId) {
        if (StringUtils.isBlank(tenantId)) {
            LOGGER.error("将记录状态置为失效, tenantId不能为空");
            throw new BusinessException(SassBizResultCodeEnum.ILLEGAL_PARAMETER, "将记录状态置为失效, tenantId不能为空");
        }
        if (StringUtils.isBlank(taskId)) {
            LOGGER.error("将记录状态置为失效, taskId不能为空");
            throw new BusinessException(SassBizResultCodeEnum.ILLEGAL_PARAMETER, "将记录状态置为失效, taskId不能为空");
        }
        return departmentDOMapper.updateInactiveStatus(tenantId, taskId);
    }

    @Override
    public List<DepartmentDO> queryDeptsByIds(List<String> deptIds) {

        List<DepartmentDO> departmentDOSList = this.departmentDOMapper.listAllWechatDepts();

        List<DepartmentDO> result = departmentDOSList.stream()
                .filter(x->deptIds.contains(String.valueOf(x.getDepartmentId())))
                .collect(Collectors.toList());
        return result;
    }
}
