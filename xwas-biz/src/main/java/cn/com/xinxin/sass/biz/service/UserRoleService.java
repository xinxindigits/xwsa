package cn.com.xinxin.sass.biz.service;

import cn.com.xinxin.sass.common.model.PageResultVO;
import cn.com.xinxin.sass.repository.model.RoleDO;
import cn.com.xinxin.sass.repository.model.UserRoleDO;

import java.util.List;

/**
 * Created by dengyunhui on 2018/5/1
 **/
public interface UserRoleService {

    UserRoleDO createUserRole(UserRoleDO userRoleDO);

    boolean createUserRoles(List<UserRoleDO> userRoles);

    boolean updateUserRole(UserRoleDO userRoleDO);

    List<RoleDO> findRoleByUserAccount(String account);

    boolean deleteUserRole(Long id);

    boolean deleteByRoleCode(String roleCode);

    /**
     * @param accounts
     * @return
     */
    boolean deleteByAccounts(List<String> accounts);

    PageResultVO<UserRoleDO> findByConditionPage(PageResultVO page, UserRoleDO condition);

    UserRoleDO findById(Long id);

    boolean saveOrUpdate(UserRoleDO userRoleDO);

    int updateByUserAccount(UserRoleDO userRoleDO);

    int updateByRoleCode(UserRoleDO userRoleDO);

    int countByRoleCodes(List<String> roleCode);

    List<UserRoleDO> findByRoleCode(String roleCode);

    boolean deleteByRoleCodes(List<String> roleCodes);

    boolean deleteByAccountsAndRoleCode(String roleCode, List<String> accounts);
}
