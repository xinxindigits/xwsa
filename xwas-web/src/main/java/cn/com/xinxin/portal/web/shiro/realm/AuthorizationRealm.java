package cn.com.xinxin.portal.web.shiro.realm;

import cn.com.xinxin.portal.biz.service.UserService;
import cn.com.xinxin.portal.repository.model.ResourceDO;
import cn.com.xinxin.portal.repository.model.RoleDO;
import cn.com.xinxin.portal.repository.model.UserDO;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *  认证、授权
 * Created by dengyunhui on 2018/4/23
 **/
public class AuthorizationRealm extends AuthorizingRealm {

    private UserService userService;

    public AuthorizationRealm(UserService userService) {
        this.userService = userService;
    }

    /**
     *  根据用户身份获取授权信息
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String userNo = (String) principalCollection.getPrimaryPrincipal();
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();

        List<RoleDO> roleDOList = userService.findRoles(userNo);
        if (!CollectionUtils.isEmpty(roleDOList)){
            Set<String> roleCodes = new HashSet<>(roleDOList.size());
            roleDOList.forEach(roleDO -> roleCodes.add(roleDO.getCode()));
            authorizationInfo.setRoles(roleCodes);
        }

        List<ResourceDO> resourceDOS = userService.findResources(userNo);
        if (!CollectionUtils.isEmpty(resourceDOS)){
            Set<String> permissionUrls = new HashSet<>(resourceDOS.size());
            resourceDOS.forEach(resourceDO -> permissionUrls.add(resourceDO.getUrl()));
            authorizationInfo.setStringPermissions(permissionUrls);
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
        UserDO user = userService.findByUserNo(userNo);

        if (user == null){
            throw new UnknownAccountException();
        }

        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                userNo,//SecurityUtils.getSubject().getPrincipal(),principalCollection.getPrimaryPrincipal()
                user.getPassword(),
                ByteSource.Util.bytes(userNo + user.getSalt()),getName());//slat = username + slat

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