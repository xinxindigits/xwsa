package cn.com.xinxin.sass.auth.shiro.filter;

import cn.com.xinxin.sass.auth.protocol.SessionBizResultCodeEnum;
import cn.com.xinxin.sass.auth.repository.UserAclTokenRepository;
import cn.com.xinxin.sass.auth.utils.HttpRequestUtil;
import cn.com.xinxin.sass.auth.utils.JWTUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.LogoutFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

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
        Subject subject = getSubject(request, response);
        HttpServletRequest httpServletRequest = WebUtils.toHttp(request);
        // 获取用户token并且拿到用户信息
        String token = HttpRequestUtil.getLoginToken(httpServletRequest);
        if(StringUtils.isBlank(token)){
            return super.preHandle(request, response);
        }else{
            String account = JWTUtil.getUserAccount(token);
            // 清除用户的token、缓存信息等
            log.info("用户登logout请求 account = {}" ,account);
            userAclTokenRepository.cleanSassUserInfoCache(account);
            userAclTokenRepository.cleanSassUserTokenCache(account);
        }
        subject.logout();
        sendResultToRequest(response);
        return false;
    }


    protected void sendResultToRequest(ServletResponse response){

        Map<String, String> resultMap = new HashMap();
        resultMap.put("code", SessionBizResultCodeEnum.SUCCESS.getCode());
        resultMap.put("message", "logout成功");
        resultMap.put("data", null);
        ObjectMapper objectMapper = new ObjectMapper();
        PrintWriter out = null;
        try {
            String json = objectMapper.writeValueAsString(resultMap);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=utf-8");
            out = response.getWriter();
            out.write(json);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
