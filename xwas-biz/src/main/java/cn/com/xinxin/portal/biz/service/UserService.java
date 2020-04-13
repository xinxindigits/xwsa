package cn.com.xinxin.portal.biz.service;

import cn.com.xinxin.portal.biz.vo.QueryUserConditionVO;
import cn.com.xinxin.portal.common.Page;
import cn.com.xinxin.portal.repository.model.ResourceDO;
import cn.com.xinxin.portal.repository.model.RoleDO;
import cn.com.xinxin.portal.repository.model.UserDO;

import java.util.List;

/**
 * Created by dengyunhui on 2018/4/23
 **/
public interface UserService {

    int createUser(UserDO userDO);

    void resetPassword(Long userId,String newPassword,String updater);

    void modifyPassword(Long userId,String originPassword, String newPassword,String updater);

    UserDO findByUserName(String userName);

    UserDO findByUserNo(String userNo);

    List<RoleDO> findRoles(String userNo);

    List<ResourceDO> findResources(String userNo);

    List<ResourceDO> findPermissions(String userNo);

    List<ResourceDO> findRootMenus(String userNo);

    List<ResourceDO> findMenus(String userNo);

    Page<UserDO> findByConditionPage(Page page, QueryUserConditionVO queryUserConditionVO);

    boolean updateUser(UserDO userDO);

    Boolean delete(Long id);

    UserDO findById(Long id);

    UserDO getLoginUser(String sessionId);

    Boolean hasPermission(String sessionId,String url);
}
