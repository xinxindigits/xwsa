package cn.com.xinxin.sass.session.auth;

import cn.com.xinxin.sass.session.model.SassUserInfo;
import cn.com.xinxin.sass.session.repository.UserAclTokenRepository;
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

    private UserAclTokenRepository userAclTokenRepository;


    public UserAclTokenRepository getUserAclTokenRepository() {
        return userAclTokenRepository;
    }

    public void setUserAclTokenRepository(UserAclTokenRepository userAclTokenRepository) {
        this.userAclTokenRepository = userAclTokenRepository;
    }

    /**
     *  根据用户身份获取授权信息
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

        String userNo = (String) principalCollection.getPrimaryPrincipal();

        SassUserInfo sassUserInfo = this.userAclTokenRepository.getPortalUserByUserNo(userNo);

        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();

        if (!CollectionUtils.isEmpty(sassUserInfo.getRoles())){
            authorizationInfo.setRoles(sassUserInfo.getRoles());
        }


        if (!CollectionUtils.isEmpty(sassUserInfo.getStringPermissions())){
            authorizationInfo.setStringPermissions(sassUserInfo.getStringPermissions());
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

        SassUserInfo sassUserInfo = this.userAclTokenRepository.getPortalUserByUserNo(userNo);

        if (sassUserInfo == null){
            throw new UnknownAccountException();
        }

        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
            userNo, sassUserInfo.getPassword(),
            ByteSource.Util.bytes(userNo + sassUserInfo.getSalt()),getName());

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