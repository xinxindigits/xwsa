package cn.com.xinxin.portal.web.shiro.service;

import cn.com.xinxin.portal.web.shiro.realm.JWTToken;

import javax.servlet.http.HttpServletResponse;

/**
 * @author: zhouyang
 * @created: 13/04/2020.
 * @updater:
 * @description:
 */
public interface ShiroLoginService {

    /**
     * 如果(当前时间+倒计时) > 过期时间，则刷新token
     * 并更新缓存
     * 当前token失效，返回新token
     * 当前请求有效，返回状态码：460
     * 前端下次使用新token
     * 如果token继续发往后台，则提示，此token已失效，请使用新token，不在返回新token，返回状态码：461
     *
     * @param jwtToken
     * @param httpServletResponse
     * @throws Exception
     */
    void refreshToken(JWTToken jwtToken, HttpServletResponse httpServletResponse) throws Exception;
}
