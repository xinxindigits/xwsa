package cn.com.xinxin.sass.web.shiro.realm;


import cn.com.xinxin.sass.session.utils.JWTUtil;
import cn.com.xinxin.sass.biz.service.UserService;
import cn.com.xinxin.sass.repository.model.UserDO;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * @author: zhouyang
 * @created: 13/04/2020.
 * @updater:
 * @description: jwt认证类
 */
public class JwtTokenAuthorizingRealm extends AuthorizingRealm {


    private static final Logger logger = LoggerFactory.getLogger(JwtTokenAuthorizingRealm.class);


    private UserService userService;


    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    /**
     * 必须重写此方法，不然Shiro会报错
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JWTToken;
    }


    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        // 从tokent中读取用户信息

        String token = (String) authenticationToken.getCredentials();

        String userName = JWTUtil.getUsername(token);

        if (userName == null) {
            throw new AuthenticationException("token验证无效，清重新尝试");
        }

        UserDO userDO = this.userService.findByUserName(userName);

        if (userDO == null){
            throw new UnknownAccountException();
        }
        // 返回值
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                userName, token, ByteSource.Util.bytes(userName + "JwtTokenAuthorizingRealm"),getName());

        return authenticationInfo;
    }

    // FIXME: 需要继续完善权限认证功能
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

        logger.info("————权限认证 [ roles、permissions]————");

        String token = (String) principalCollection.getPrimaryPrincipal();

        String userName = JWTUtil.getUsername(token);

        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();

//        List<RoleDO> roleDOList = userService.findRolesByName(userName);
//
//        if (!CollectionUtils.isEmpty(roleDOList)){
//            Set<String> roleCodes = new HashSet<>(roleDOList.size());
//            roleDOList.forEach(roleDO -> roleCodes.add(roleDO.getCode()));
//            authorizationInfo.setRoles(roleCodes);
//        }
//
//        List<ResourceDO> resourceDOS = userService.findResourcesByName(userName);
//        if (!CollectionUtils.isEmpty(resourceDOS)){
//            Set<String> permissionUrls = new HashSet<>(resourceDOS.size());
//            resourceDOS.forEach(resourceDO -> permissionUrls.add(resourceDO.getUrl()));
//            authorizationInfo.setStringPermissions(permissionUrls);
//        }

        return authorizationInfo;
    }


}
