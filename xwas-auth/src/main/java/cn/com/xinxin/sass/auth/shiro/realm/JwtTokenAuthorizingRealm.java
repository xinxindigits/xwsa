package cn.com.xinxin.sass.auth.shiro.realm;


import cn.com.xinxin.sass.auth.model.JWTToken;
import cn.com.xinxin.sass.auth.model.SassUserInfo;
import cn.com.xinxin.sass.auth.protocol.SessionBizResultCodeEnum;
import cn.com.xinxin.sass.auth.repository.UserAclTokenRepository;
import cn.com.xinxin.sass.auth.utils.JWTUtil;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.xinxinfinance.commons.exception.BusinessException;
import com.xinxinfinance.commons.result.CommonResultCode;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author: zhouyang
 * @created: 13/04/2020.
 * @updater:
 * @description: jwt认证类
 */
public class JwtTokenAuthorizingRealm extends AuthorizingRealm {


    private static final Logger logger = LoggerFactory.getLogger(JwtTokenAuthorizingRealm.class);


    //private UserService userService;


    private UserAclTokenRepository userAclTokenRepository;


//    public UserService getUserService() {
//        return userService;
//    }
//
//    public void setUserService(UserService userService) {
//        this.userService = userService;
//    }

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

        try {
            // 从tokent中读取用户信息
            String token = (String) authenticationToken.getCredentials();

            String account = JWTUtil.getUserAccount(token);

            if (account == null) {
                throw new BusinessException(SessionBizResultCodeEnum.INVALID_TOKEN, "token验证无效，清重新尝试");
            }

            if (JWTUtil.isExpired(token)) {
                // 如果token过期，则直接返回token过期异常
                logger.info("————权限认证 token已经过期————");
                throw new TokenExpiredException("token已经过期");
            }

            SassUserInfo sassUserInfo = this.userAclTokenRepository.getSassUserByUserAccount(account);

            if (sassUserInfo == null) {
                throw new AuthenticationException("授权认证失败,无法查询到账户信息");
            }
            // 返回值
            SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                    account, token, ByteSource.Util.bytes(account + "JwtTokenAuthorizingRealm"), getName());

            return authenticationInfo;

        }catch (Exception ex){

            throw new BusinessException(SessionBizResultCodeEnum.INVALID_TOKEN, "授权认证失败,请重新登陆");
        }

    }


    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) throws AuthenticationException{

        logger.info("————权限认证 [ roles、permissions]————");

        try {
            String account = (String) principalCollection.getPrimaryPrincipal();

            SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();

            SassUserInfo sassUserInfo = this.userAclTokenRepository.getSassUserByUserAccount(account);

            if (sassUserInfo == null){
                throw new UnknownAccountException();
            }
            if (!CollectionUtils.isEmpty(sassUserInfo.getRoles())){
                authorizationInfo.setRoles(sassUserInfo.getRoles());
            }
            if (!CollectionUtils.isEmpty(sassUserInfo.getStringPermissions())){
                authorizationInfo.setStringPermissions(sassUserInfo.getStringPermissions());
            }else{
                throw new BusinessException(SessionBizResultCodeEnum.NO_PERMISSION,"无权限操作","无权限操作");
            }

            if (!CollectionUtils.isEmpty(sassUserInfo.getObjectPermissions())){
                authorizationInfo.setObjectPermissions(sassUserInfo.getObjectPermissions());
            }

            return authorizationInfo;

        }catch (Exception ex){
            throw new BusinessException(SessionBizResultCodeEnum.NO_PERMISSION,"无权限操作","无权限操作");
        }
    }

}
