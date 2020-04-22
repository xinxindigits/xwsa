package cn.com.xinxin.sass.biz.service.impl;

import cn.com.xinxin.sass.biz.service.RoleService;
import cn.com.xinxin.sass.common.model.PageResultVO;
import cn.com.xinxin.sass.repository.dao.ResourceMapper;
import cn.com.xinxin.sass.repository.dao.RoleMapper;
import cn.com.xinxin.sass.repository.dao.RoleResourceMapper;
import cn.com.xinxin.sass.repository.model.ResourceDO;
import cn.com.xinxin.sass.repository.model.RoleDO;
import cn.com.xinxin.sass.repository.model.RoleResourceDO;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by dengyunhui on 2018/5/1
 **/
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private RoleResourceMapper roleResourceMapper;

    @Autowired
    private ResourceMapper resourceMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RoleDO createRole(RoleDO roleDO, List<String> resourceList) {
        roleMapper.insertSelective(roleDO);
        if(!CollectionUtils.isEmpty(resourceList)){
        List<ResourceDO> resourceDOList = resourceMapper.findResources(resourceList);
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
            }
        }
        return roleDO;
    }

    @Override
    public int updateRole(RoleDO roleDO) {
        return roleMapper.updateByPrimaryKeySelective(roleDO);
    }

    @Override
    public int deleteRole(Long roleId) {
        return roleMapper.deleteByPrimaryKey(roleId);
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

}
