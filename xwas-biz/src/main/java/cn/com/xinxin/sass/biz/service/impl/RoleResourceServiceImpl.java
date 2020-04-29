package cn.com.xinxin.sass.biz.service.impl;

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
