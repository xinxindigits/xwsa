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

import cn.com.xinxin.sass.biz.service.RoleResourceService;
import cn.com.xinxin.sass.common.model.PageResultVO;
import cn.com.xinxin.sass.repository.dao.RoleResourceMapper;
import cn.com.xinxin.sass.repository.model.ResourceDO;
import cn.com.xinxin.sass.repository.model.RoleDO;
import cn.com.xinxin.sass.repository.model.RoleResourceDO;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by dengyunhui on 2018/5/1
 **/
@Service
public class RoleResourceServiceImpl implements RoleResourceService {

    @Autowired
    private RoleResourceMapper roleResourceMapper;

    @Override
    public RoleResourceDO createRoleResource(RoleResourceDO roleResourceDO) {
        roleResourceMapper.insertSelective(roleResourceDO);
        return roleResourceDO;
    }

    @Override
    public boolean createRoleResources(List<RoleResourceDO> roleResources) {
        roleResourceMapper.batchInsert(roleResources);
        return true;
    }

    @Override
    public boolean createRoleResources(RoleDO roleDO, List<ResourceDO> resourceDOList) {
        if(!CollectionUtils.isEmpty(resourceDOList)){
            List<RoleResourceDO> roleResourceDOList = resourceDOList.stream().map(resourceDO -> {
                RoleResourceDO roleResourceDO = new RoleResourceDO();
                roleResourceDO.setTenantId(roleDO.getTenantId());
                roleResourceDO.setRoleCode(roleDO.getCode());
                roleResourceDO.setRoleName(roleDO.getName());
                roleResourceDO.setResourceCode(resourceDO.getCode());
                roleResourceDO.setResourceName(resourceDO.getName());
                roleResourceDO.setGmtUpdater(roleDO.getGmtUpdater());
                roleResourceDO.setGmtCreator(roleDO.getGmtCreator());
                return roleResourceDO;
            }).collect(Collectors.toList());
            roleResourceMapper.batchInsert(roleResourceDOList);
            return true;
        }else {
            return false;
        }
    }

    @Override
    public boolean updateRoleResource(RoleResourceDO roleResourceDO) {
        int n = roleResourceMapper.updateByPrimaryKeySelective(roleResourceDO);

        return n == 1;
    }

    @Override
    public boolean deleteRoleResource(long id) {
        int n = roleResourceMapper.deleteByPrimaryKey(id);
        return n == 1;
    }

    @Override
    public List<ResourceDO> findResourcesByRoleCode(String roleCode) {
        return roleResourceMapper.findResourceByRoleCode(roleCode);
    }

    @Override
    public List<ResourceDO> findResourcesByRoleCode(List<String> roleCodes) {
        return roleResourceMapper.findResources(roleCodes);
    }

    @Override
    public PageResultVO<RoleResourceDO> findByConditionPage(PageResultVO page,RoleResourceDO condition) {
        com.github.pagehelper.Page page1 = PageHelper.startPage(page.getPageNumber(),page.getPageSize());
        List<RoleResourceDO> resourceDOS = roleResourceMapper.findByCondition(condition);

        PageResultVO<RoleResourceDO> result = new PageResultVO<>();
        result.setTotal(page1.getTotal());
        result.setPageSize(page.getPageSize());
        result.setItems(resourceDOS);
        result.setPageNumber(page.getPageNumber());

        return result;
    }

    @Override
    public RoleResourceDO findById(Long id) {
        return roleResourceMapper.selectByPrimaryKey(id);
    }

    @Override
    public Boolean delete(String roleCode, List<String> resourceCodes) {
        roleResourceMapper.delete(roleCode,resourceCodes);
        return true;
    }


    @Override
    public List<RoleResourceDO> queryRolesAndResourcesByRSCode(String rsCode) {

        List<RoleResourceDO> roleResourceDOS = this.roleResourceMapper.queryRolesAndResourcesByRSCode(rsCode);

        return roleResourceDOS;
    }

    @Override
    public List<RoleResourceDO> queryRolesAndResourcesByRSCodeList(List<String> rsCodes) {
        return null;
    }

    @Override
    public boolean updateByRoleCode(RoleResourceDO roleResourceDO) {
        roleResourceMapper.updateByRoleCode(roleResourceDO);
        return true;
    }

    @Override
    public boolean updateByResourceCode(RoleResourceDO roleResourceDO) {
        roleResourceMapper.updateByResourceCode(roleResourceDO);
        return true;
    }

    @Override
    public boolean deleteByRoleCodes(List<String> roleCode) {
        roleResourceMapper.deleteByRoleCodes(roleCode);
        return true;
    }
}
