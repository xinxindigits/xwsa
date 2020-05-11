package cn.com.xinxin.sass.biz.service;

import cn.com.xinxin.sass.biz.vo.QueryUserConditionVO;
import cn.com.xinxin.sass.common.model.PageResultVO;
import cn.com.xinxin.sass.repository.model.ResourceDO;
import cn.com.xinxin.sass.repository.model.RoleDO;
import cn.com.xinxin.sass.repository.model.UserDO;
import cn.com.xinxin.sass.repository.model.UserOrgDO;

import java.util.List;

/**
 * Created by dengyunhui on 2018/4/23
 **/
public interface UserService {

    int createUser(UserDO userDO);

    /**
     * 重置用户密码
     * @param userAccount
     * @param newPassword
     * @param updater
     */
    void resetPassword(String userAccount,
                       String newPassword,
                       String updater);

    /**
     * 修改用户密码
     * @param userId
     * @param originPassword
     * @param newPassword
     * @param updater
     */
    void modifyPassword(String userId,
                        String originPassword,
                        String newPassword,
                        String updater);

    /**
     * 批量删除用户信息
     * @param accounts
     */
    void deleteUserByAccounts(List<String> accounts);

    List<UserDO> findUserByAccounts(List<String> accounts);

    UserDO findByUserAccount(String account);

    List<RoleDO> findRolesByAccount(String account);

    List<ResourceDO> findResourcesByAccount(String account);

    List<ResourceDO> findPermissionsByAccount(String account);

    List<ResourceDO> findRootMenusByAccount(String account);

    List<ResourceDO> findMenusByAccount(String userAccount);

    List<ResourceDO> findFunctionsByAccount(String account);

    PageResultVO<UserDO> findByConditionPage(PageResultVO page, QueryUserConditionVO queryUserConditionVO);

    boolean updateUser(UserDO userDO);

    Boolean delete(Long id);

    UserDO findById(Long id);

    UserDO getLoginUser(String sessionId);

    Boolean hasPermission(String sessionId,String url);

    void refreshSassUserInfo(String account);

    /**
     * 创建用户组织关系
     * @param userOrgDO
     * @return
     */
    int createUserOrgRelations(UserOrgDO userOrgDO);


    /**
     * 创建用户组织关系
     * @param userOrgDOList
     * @return
     */
    int createUserOrgRelationsByList(List<UserOrgDO> userOrgDOList);



    /**
     * 查询用户的组织关系
     * @param account
     * @return
     */
    List<UserOrgDO> queryUserOrgsByAccount(String account);


    /**
     * 删除用户与组织关系
     * @param account
     * @return
     */
    int removeUserOrgRelationByAccount(String account);

    /**
     * 删除用户与组织关系
     * @param orgCode
     * @return
     */
    int removeUserOrgRelationByOrgCode(String orgCode);


    /**
     * 删除
     * @param accounts
     * @return
     */
    int removeUserOrgRelationByAccountList(List<String> accounts);
}
