package cn.com.xinxin.sass.biz.service.impl;

import cn.com.xinxin.sass.biz.service.UserRoleService;
import cn.com.xinxin.sass.common.Page;
import cn.com.xinxin.sass.repository.dao.UserRoleMapper;
import cn.com.xinxin.sass.repository.model.RoleDO;
import cn.com.xinxin.sass.repository.model.UserRoleDO;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by dengyunhui on 2018/5/1
 **/
@Service
public class UserRoleServiceImpl implements UserRoleService {

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Override
    public UserRoleDO createUserRole(UserRoleDO userRoleDO) {
        userRoleMapper.insertSelective(userRoleDO);
        return userRoleDO;
    }

    @Override
    public boolean createUserRoles(List<UserRoleDO> userRoles) {
        userRoleMapper.batchInsert(userRoles);
        return true;
    }

    @Override
    public boolean updateUserRole(UserRoleDO userRoleDO) {
        int n = userRoleMapper.updateByPrimaryKeySelective(userRoleDO);
        return n == 1;
    }

    @Override
    public List<RoleDO> findRoleByUserAccount(String account) {
        return userRoleMapper.findRoleByUserAccount(account);
    }

    @Override
    public boolean deleteUserRole(Long id) {
        int n = userRoleMapper.deleteByPrimaryKey(id);
        return n == 1;
    }

    @Override
    public Page<UserRoleDO> findByConditionPage(Page page, UserRoleDO condition) {
       com.github.pagehelper.Page page1 = PageHelper.startPage(page.getPageNumber(),page.getPageSize());
       List<UserRoleDO> userRoleDOS = userRoleMapper.findByCondition(condition);

       Page<UserRoleDO> result = new Page<>();
       result.setPageNumber(page.getPageNumber());
       result.setRows(userRoleDOS);
       result.setPageSize(page.getPageSize());
       result.setTotal(page1.getTotal());

       return result;
    }

    @Override
    public UserRoleDO findById(Long id) {
        return userRoleMapper.selectByPrimaryKey(id);
    }
}
