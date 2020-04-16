package cn.com.xinxin.sass.web.shiro.filter;

import cn.com.xinxin.sass.auth.repository.UserAclTokenRepository;
import cn.com.xinxin.sass.auth.utils.HttpRequestUtil;
import cn.com.xinxin.sass.auth.utils.JWTUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.LogoutFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * @author: zhouyang
 * @created: 16/04/2020.
 * @updater:
 * @description:
 */
public class JwtLogoutFilter extends LogoutFilter {


    private static final Logger log = LoggerFactory.getLogger(JwtLogoutFilter.class);

    private UserAclTokenRepository userAclTokenRepository;

    public UserAclTokenRepository getUserAclTokenRepository() {
        return userAclTokenRepository;
    }

    public void setUserAclTokenRepository(UserAclTokenRepository userAclTokenRepository) {
        this.userAclTokenRepository = userAclTokenRepository;
    }

    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {

        HttpServletRequest httpServletRequest = WebUtils.toHttp(request);
        // 获取用户token并且拿到用户信息
        String token = HttpRequestUtil.getLoginToken(httpServletRequest);
        if(StringUtils.isBlank(token)){
            return super.preHandle(request, response);
        }else{
            String account = JWTUtil.getUserAccount(token);
            // 清除用户的token、缓存信息等
            userAclTokenRepository.cleanSassUserInfoCache(account);
            userAclTokenRepository.cleanSassUserTokenCache(account);
        }
        return super.preHandle(request, response);
    }


}
