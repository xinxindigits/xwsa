package cn.com.xinxin.portal.web.shiro.filter;

import cn.com.xinxin.portal.web.shiro.realm.JWTToken;
import cn.com.xinxin.portal.web.shiro.service.ShiroLoginService;
import cn.com.xinxin.sass.biz.service.UserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author: zhouyang
 * @created: 13/04/2020.
 * @updater:
 * @description:
 */
public class JWTTokenFilter extends AuthenticatingFilter {


    private Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserService userService;

    @Autowired
    private ShiroLoginService loginService;


    @Override
    protected AuthenticationToken createToken(ServletRequest servletRequest,
                                              ServletResponse servletResponse) throws Exception {
        return null;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest,
                                     ServletResponse servletResponse) throws Exception {
        return false;
    }


    /**
     * 判断是否允许访问
     *
     * @param request
     * @param response
     * @param mappedValue
     * @return
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        String url = WebUtils.toHttp(request).getRequestURI();
        LOGGER.debug("isAccessAllowed url:{}", url);
        if (this.isLoginRequest(request, response)) {
            return true;
        }
        boolean allowed = false;
        try {
            allowed = executeLogin(request, response);
        } catch (IllegalStateException e) { //not found any token
            LOGGER.error("Token不能为空", e);
        } catch (Exception e) {
            LOGGER.error("访问错误", e);
        }
        return allowed || super.isPermissive(mappedValue);
    }

    /**
     * 登陆成功处理
     *
     * @param token
     * @param subject
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @Override
    protected boolean onLoginSuccess(AuthenticationToken token,
                                     Subject subject,
                                     ServletRequest request,
                                     ServletResponse response) throws Exception {
        String url = WebUtils.toHttp(request).getRequestURI();
        LOGGER.debug("鉴权成功,token:{},url:{}", token, url);
        // 刷新token
        JWTToken jwtToken = (JWTToken) token;
        HttpServletResponse httpServletResponse = WebUtils.toHttp(response);
        loginService.refreshToken(jwtToken, httpServletResponse);
        return true;
    }

    /**
     * 登陆失败处理
     *
     * @param token
     * @param e
     * @param request
     * @param response
     * @return
     */
    @Override
    protected boolean onLoginFailure(AuthenticationToken token,
                                     AuthenticationException e,
                                     ServletRequest request,
                                     ServletResponse response) {
        LOGGER.error("登陆失败，token:" + token + ",error:" + e.getMessage(), e);
        return false;
    }
}
