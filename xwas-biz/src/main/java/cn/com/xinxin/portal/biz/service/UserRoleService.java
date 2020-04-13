package cn.com.xinxin.portal.biz.service;

import cn.com.xinxin.portal.common.Page;
import cn.com.xinxin.portal.repository.model.RoleDO;
import cn.com.xinxin.portal.repository.model.UserRoleDO;

import java.util.List;

/**
 * Created by dengyunhui on 2018/5/1
 **/
public interface UserRoleService {

    UserRoleDO createUserRole(UserRoleDO userRoleDO);

    boolean createUserRoles(List<UserRoleDO> userRoles);

    boolean updateUserRole(UserRoleDO userRoleDO);

    List<RoleDO> findRoleByUserNo(String userNo);

    boolean deleteUserRole(Long id);

    Page<UserRoleDO> findByConditionPage(Page page,UserRoleDO condition);

    UserRoleDO findById(Long id);
}
