package cn.com.xinxin.sass.biz.service;

import cn.com.xinxin.sass.biz.vo.QueryUserConditionVO;
import cn.com.xinxin.sass.common.model.PageResultVO;
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
}
