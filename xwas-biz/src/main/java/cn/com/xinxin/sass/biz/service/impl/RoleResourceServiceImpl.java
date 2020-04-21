package cn.com.xinxin.sass.biz.service.impl;

import cn.com.xinxin.sass.biz.service.RoleResourceService;
import cn.com.xinxin.sass.common.model.PageResultVO;
import cn.com.xinxin.sass.repository.dao.RoleResourceMapper;
import cn.com.xinxin.sass.repository.model.ResourceDO;
import cn.com.xinxin.sass.repository.model.RoleResourceDO;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public List<ResourceDO> findResources(String roleCode) {
        return roleResourceMapper.findResourceByRoleCode(roleCode);
    }

    @Override
    public List<ResourceDO> findResources(List<String> roleCodes) {
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
}
