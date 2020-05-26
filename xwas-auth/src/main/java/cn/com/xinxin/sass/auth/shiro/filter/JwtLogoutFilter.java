package cn.com.xinxin.sass.auth.shiro.filter;

/*
 *
 * Copyright 2020 www.xinxindigits.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software
 * and associated documentation files (the "Software"),to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice
 * shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 * Redistribution and selling copies of the software are prohibited, only if the authorization from xinxin digits
 * was obtained.Neither the name of the xinxin digits; nor the names of its contributors may be used to
 * endorse or promote products derived from this software without specific prior written permission.
 *
 */

import cn.com.xinxin.sass.auth.context.SassBaseContextHolder;
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
