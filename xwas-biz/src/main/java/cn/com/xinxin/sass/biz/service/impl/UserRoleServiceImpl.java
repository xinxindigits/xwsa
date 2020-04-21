package cn.com.xinxin.sass.biz.service.impl;

import cn.com.xinxin.sass.biz.service.UserRoleService;
import cn.com.xinxin.sass.common.model.PageResultVO;
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
        List<RoleDO> roleDOList = userRoleMapper.findRoleByUserAccount(account);
        return roleDOList;
    }

    @Override
    public boolean deleteUserRole(Long id) {
        int n = userRoleMapper.deleteByPrimaryKey(id);
        return true;
    }

    @Override
    public boolean deleteByRoleCode(String roleCode) {
        userRoleMapper.deleteByRoleCode(roleCode);
        return true;
    }

    @Override
    public PageResultVO<UserRoleDO> findByConditionPage(PageResultVO page, UserRoleDO condition) {
       com.github.pagehelper.Page page1 = PageHelper.startPage(page.getPageNumber(),page.getPageSize());
       List<UserRoleDO> userRoleDOS = userRoleMapper.findByCondition(condition);

        PageResultVO<UserRoleDO> result = new PageResultVO<UserRoleDO>();
       result.setPageNumber(page.getPageNumber());
       result.setItems(userRoleDOS);
       result.setPageSize(page.getPageSize());
       result.setTotal(page1.getTotal());

       return result;
    }

    @Override
    public UserRoleDO findById(Long id) {
        return userRoleMapper.selectByPrimaryKey(id);
    }

    @Override
    public boolean saveOrUpdate(UserRoleDO userRoleDO) {
        return userRoleMapper.saveOrUpdate(userRoleDO) == 1;
    }
}
