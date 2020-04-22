package cn.com.xinxin.sass.auth.shiro.credentials;

import cn.com.xinxin.sass.auth.utils.JWTUtil;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author: zhouyang
 * @created: 22/04/2020.
 * @updater:
 * @description:
 */
public class JwtCredentialsMatcher  implements CredentialsMatcher {


    public static final Logger log = LoggerFactory.getLogger(JWTUtil.class);

    @Override
    public boolean doCredentialsMatch(AuthenticationToken authenticationToken,
                                      AuthenticationInfo authenticationInfo) {
        String token = authenticationToken.getCredentials().toString();
        try {
            return JWTUtil.isExpired(token);
        } catch (Exception e) {
            log.error("JWT Token CredentialsMatch Exception:" + e.getMessage(), e);
        }
        return false;
    }


}
