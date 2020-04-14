package cn.com.xinxin.sass.biz.service;

import cn.com.xinxin.sass.biz.vo.QueryUserConditionVO;
import cn.com.xinxin.sass.common.Page;
import cn.com.xinxin.sass.repository.model.ResourceDO;
import cn.com.xinxin.sass.repository.model.RoleDO;
import cn.com.xinxin.sass.repository.model.UserDO;

import java.util.List;

/**
 * Created by dengyunhui on 2018/4/23
 **/
public interface UserService {

    int createUser(UserDO userDO);

    void resetPassword(Long userId,String newPassword,String updater);

    void modifyPassword(Long userId,String originPassword, String newPassword,String updater);

    UserDO findByUserName(String userName);

    UserDO findByUserAccount(String userNo);

    List<RoleDO> findRoles(String userNo);

    List<RoleDO> findRolesByName(String userName);

    List<ResourceDO> findResources(String userNo);

    List<ResourceDO> findResourcesByName(String userName);

    List<ResourceDO> findPermissions(String userNo);

    List<ResourceDO> findPermissionsByName(String userName);

    List<ResourceDO> findRootMenus(String userNo);

    List<ResourceDO> findRootMenusByName(String userName);

    List<ResourceDO> findMenus(String userNo);

    List<ResourceDO> findMenusByName(String userName);

    Page<UserDO> findByConditionPage(Page page, QueryUserConditionVO queryUserConditionVO);

    boolean updateUser(UserDO userDO);

    Boolean delete(Long id);

    UserDO findById(Long id);

    UserDO getLoginUser(String sessionId);

    Boolean hasPermission(String sessionId,String url);
}
