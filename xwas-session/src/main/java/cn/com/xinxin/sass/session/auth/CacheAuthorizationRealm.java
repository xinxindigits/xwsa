package cn.com.xinxin.sass.session.auth;

import cn.com.xinxin.sass.session.model.PortalUser;
import cn.com.xinxin.sass.session.repository.UserAclSessionRepository;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;


/**
 *  认证、授权
 * Created by dengyunhui on 2018/4/23
 **/
public class CacheAuthorizationRealm extends AuthorizingRealm {

    private static final Logger logger = LoggerFactory.getLogger(CacheAuthorizationRealm.class);

    private UserAclSessionRepository userAclSessionRepository;


    public UserAclSessionRepository getUserAclSessionRepository() {
        return userAclSessionRepository;
    }

    public void setUserAclSessionRepository(UserAclSessionRepository userAclSessionRepository) {
        this.userAclSessionRepository = userAclSessionRepository;
    }

    /**
     *  根据用户身份获取授权信息
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

        String userNo = (String) principalCollection.getPrimaryPrincipal();

        PortalUser portalUser = this.userAclSessionRepository.getPortalUserByUserNo(userNo);

        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();

        if (!CollectionUtils.isEmpty(portalUser.getRoles())){
            authorizationInfo.setRoles(portalUser.getRoles());
        }


        if (!CollectionUtils.isEmpty(portalUser.getStringPermissions())){
            authorizationInfo.setStringPermissions(portalUser.getStringPermissions());
        }

        return authorizationInfo;
    }

    /**
     * 获取身份认证信息，登陆时调用
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        String userNo = (String) authenticationToken.getPrincipal();

        PortalUser portalUser = this.userAclSessionRepository.getPortalUserByUserNo(userNo);

        if (portalUser == null){
            throw new UnknownAccountException();
        }

        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
            userNo, portalUser.getPassword(),
            ByteSource.Util.bytes(userNo + portalUser.getSalt()),getName());

        return authenticationInfo;
    }

    public void clearAllCachedAuthorizationInfo(){
        getAuthorizationCache().clear();
    }

    public void clearAllCachedAuthenticationInfo(){
        getAuthenticationCache().clear();
    }

    public void clearAllCache(){
        clearAllCachedAuthenticationInfo();
        clearAllCachedAuthorizationInfo();
    }

}