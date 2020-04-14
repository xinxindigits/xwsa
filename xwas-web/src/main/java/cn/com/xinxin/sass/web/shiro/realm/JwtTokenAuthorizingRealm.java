package cn.com.xinxin.sass.web.shiro.realm;


import cn.com.xinxin.sass.repository.model.ResourceDO;
import cn.com.xinxin.sass.repository.model.RoleDO;
import cn.com.xinxin.sass.session.model.SassUserInfo;
import cn.com.xinxin.sass.session.repository.UserAclTokenRepository;
import cn.com.xinxin.sass.session.utils.JWTUtil;
import cn.com.xinxin.sass.biz.service.UserService;
import cn.com.xinxin.sass.repository.model.UserDO;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * @author: zhouyang
 * @created: 13/04/2020.
 * @updater:
 * @description: jwt认证类
 */
public class JwtTokenAuthorizingRealm extends AuthorizingRealm {


    private static final Logger logger = LoggerFactory.getLogger(JwtTokenAuthorizingRealm.class);


    private UserService userService;


    private UserAclTokenRepository userAclTokenRepository;


    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public UserAclTokenRepository getUserAclTokenRepository() {
        return userAclTokenRepository;
    }

    public void setUserAclTokenRepository(UserAclTokenRepository userAclTokenRepository) {
        this.userAclTokenRepository = userAclTokenRepository;
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

        String account = JWTUtil.getUsername(token);

        if (account == null) {
            throw new AuthenticationException("token验证无效，清重新尝试");
        }

        UserDO userDO = this.userService.findByUserAccount(account);

        if (userDO == null){
            throw new UnknownAccountException();
        }
        // 返回值
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                account, token, ByteSource.Util.bytes(account + "JwtTokenAuthorizingRealm"),getName());

        return authenticationInfo;
    }


    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

        logger.info("————权限认证 [ roles、permissions]————");

        String token = (String) principalCollection.getPrimaryPrincipal();

        String account = JWTUtil.getUsername(token);

        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();

        List<RoleDO> roleDOList = userService.findRolesByName(account);

        if (!CollectionUtils.isEmpty(roleDOList)){
            Set<String> roleCodes = new HashSet<>(roleDOList.size());
            roleDOList.forEach(roleDO -> roleCodes.add(roleDO.getCode()));
            authorizationInfo.setRoles(roleCodes);
        }

        List<ResourceDO> resourceDOS = userService.findResourcesByName(account);
        if (!CollectionUtils.isEmpty(resourceDOS)){
            Set<String> permissionUrls = new HashSet<>(resourceDOS.size());
            resourceDOS.forEach(resourceDO -> permissionUrls.add(resourceDO.getUrl()));
            authorizationInfo.setStringPermissions(permissionUrls);
        }

        return authorizationInfo;
    }


}
