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

import cn.com.xinxin.sass.biz.service.*;
import cn.com.xinxin.sass.common.enums.SassBizResultCodeEnum;
import cn.com.xinxin.sass.common.model.PageResultVO;
import cn.com.xinxin.sass.repository.dao.ResourceMapper;
import cn.com.xinxin.sass.repository.dao.RoleMapper;
import cn.com.xinxin.sass.repository.model.ResourceDO;
import cn.com.xinxin.sass.repository.model.RoleDO;
import cn.com.xinxin.sass.repository.model.RoleResourceDO;
import cn.com.xinxin.sass.repository.model.UserRoleDO;
import com.github.pagehelper.PageHelper;
import com.xinxinfinance.commons.exception.BusinessException;
import com.xinxinfinance.commons.util.BaseConvert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * Created by dengyunhui on 2018/5/1
 **/
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private RoleResourceService roleResourceService;

    @Autowired
    private ResourceService resourceService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRoleService userRoleService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RoleDO createRole(RoleDO roleDO, List<String> resourceList) {
        roleMapper.insertSelective(roleDO);
        if(!CollectionUtils.isEmpty(resourceList)){
            List<ResourceDO> resourceDOList = resourceService.findResources(resourceList);
            roleResourceService.createRoleResources(roleDO, resourceDOList);
        }
        return roleDO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateRole(RoleDO roleDO) {
        roleMapper.updateByCodeSelective(roleDO);
        UserRoleDO userRoleDO = BaseConvert.convert(roleDO, UserRoleDO.class);
        userRoleDO.setRoleCode(roleDO.getCode());
        userRoleDO.setRoleName(roleDO.getName());
        userRoleService.updateByRoleCode(userRoleDO);
        RoleResourceDO roleResourceDO = BaseConvert.convert(roleDO, RoleResourceDO.class);
        roleResourceDO.setRoleName(roleDO.getName());
        roleResourceDO.setRoleCode(roleDO.getCode());
        roleResourceService.updateByRoleCode(roleResourceDO);
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteRoles(List<String> roleCodes) {
        if(userRoleService.countByRoleCodes(roleCodes) > 0){
            throw new BusinessException(SassBizResultCodeEnum.NOT_PERMIT_DELETE);
        }
        roleMapper.deleteByCodes(roleCodes);
        roleResourceService.deleteByRoleCodes(roleCodes);
        userRoleService.deleteByRoleCodes(roleCodes);
        return true;
    }

    @Override
    public RoleDO findOne(long roleId) {
        return roleMapper.selectByPrimaryKey(roleId);
    }

    @Override
    public PageResultVO<RoleDO> findByConditionPage(PageResultVO page, RoleDO condition) {
        com.github.pagehelper.Page page1 = PageHelper.startPage(page.getPageNumber(),page.getPageSize());
        List<RoleDO> roleDOS = roleMapper.findByCondition(condition);

        PageResultVO<RoleDO> result = new PageResultVO<>();
        result.setPageNumber(page.getPageNumber());
        result.setPageSize(page.getPageSize());
        result.setItems(roleDOS);
        result.setTotal(page1.getTotal());
        return result;
    }

    @Override
    public List<RoleDO> findRoles(List<Long> roleIds) {
        return roleMapper.findRoles(roleIds);
    }

    @Override
    public RoleDO findByRoleCode(String code) {
        return roleMapper.findByRoleCode(code);
    }

    @Override
    public List<RoleDO> queryByRoleCodes(List<String> roleCodes) {

        return roleMapper.findRolesByCodes(roleCodes);

    }

    @Override
    public List<RoleDO> queryAllRolesByTenantId(String tenantId) {

        List<RoleDO> roleDOList = this.roleMapper.queryAllRolesByTenantId(tenantId);

        return roleDOList;
    }
}
