package cn.com.xinxin.sass.web.shiro.realm;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * @author: zhouyang
 * @created: 13/04/2020.
 * @updater:
 * @description:
 */
public class JWTToken  implements AuthenticationToken {


    // 密钥
    private String token;

    public JWTToken(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
