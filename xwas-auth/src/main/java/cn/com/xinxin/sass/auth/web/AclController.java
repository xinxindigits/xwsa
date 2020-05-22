package cn.com.xinxin.sass.auth.web;


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

import cn.com.xinxin.sass.auth.model.SassUserInfo;
import cn.com.xinxin.sass.auth.protocol.SessionBizResultCodeEnum;
import cn.com.xinxin.sass.auth.repository.UserAclTokenRepository;
import cn.com.xinxin.sass.auth.utils.HttpRequestUtil;
import cn.com.xinxin.sass.auth.utils.JWTUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xinxinfinance.commons.exception.BusinessException;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: zhouyang
 * @created: 13/07/2018.
 * @updater:
 * @description:
 */
public abstract class AclController {

    private static final Logger logger = LoggerFactory.getLogger(AclController.class);

    @Autowired
    private UserAclTokenRepository userAclTokenRepository;

    public AclController() {
    }


    protected SassUserInfo getSassUser(HttpServletRequest request){
        // 从tokent中获取对应的权限
        String token = HttpRequestUtil.getLoginToken(request);
        String account = JWTUtil.getUserAccount(token);
        SassUserInfo sassUserInfo = userAclTokenRepository.getSassUserByUserAccount(account);
        sassUserInfo.setIp(HttpRequestUtil.getIpAddress(request));
        sassUserInfo.setDevice(HttpRequestUtil.getRequestDevice(request));
        return sassUserInfo;

    }

    protected Long getUserId(HttpServletRequest request) {

        SassUserInfo sassUserInfo = this.getSassUser(request);
        if(null == sassUserInfo){
            throw new BusinessException(SessionBizResultCodeEnum.INVALID_SESSION_ID,"无法获取用户登陆信息，清查看是否登陆成功");
        }
        return sassUserInfo.getId();
    }


    protected String getUserName(HttpServletRequest request){

        SassUserInfo sassUserInfo = this.getSassUser(request);
        if(null == sassUserInfo){
            throw new BusinessException(SessionBizResultCodeEnum.INVALID_SESSION_ID,"无法获取用户登陆信息，清查看是否登陆成功");
        }
        return sassUserInfo.getName();
    }

    protected String getUserAccount(HttpServletRequest request){

        SassUserInfo sassUserInfo = this.getSassUser(request);
        if(null == sassUserInfo){
            throw new BusinessException(SessionBizResultCodeEnum.INVALID_SESSION_ID,"无法获取用户登陆信息，清查看是否登陆成功");
        }
        return sassUserInfo.getAccount();
    }


    protected String getUserIp(HttpServletRequest request){

        SassUserInfo sassUserInfo = this.getSassUser(request);
        if(null == sassUserInfo){
            throw new BusinessException(SessionBizResultCodeEnum.INVALID_SESSION_ID,"无法获取用户登陆信息，清查看是否登陆成功");
        }
        return sassUserInfo.getIp();
    }

    protected String getUserDevice(HttpServletRequest request){

        SassUserInfo sassUserInfo = this.getSassUser(request);
        if(null == sassUserInfo){
            throw new BusinessException(SessionBizResultCodeEnum.INVALID_SESSION_ID,"无法获取用户登陆信息，清查看是否登陆成功");
        }
        return sassUserInfo.getDevice();
    }



    @ExceptionHandler({ UnauthenticatedException.class, AuthenticationException.class })
    public Object authenticationException(HttpServletRequest request, HttpServletResponse response) {
        logger.info("登陆授权失败");

        throw new BusinessException(SessionBizResultCodeEnum.AUTHENTICATE_FAIL,"认证失败","认证失败");

    }
    /**
     * 权限异常
     *
     * @param request
     * @param response
     * @return
     */
    @ExceptionHandler({UnauthorizedException.class, AuthorizationException.class})
    public Object authorizationException(HttpServletRequest request, HttpServletResponse response) {

        logger.info("未授权的操作请求");
        throw new BusinessException(SessionBizResultCodeEnum.NO_PERMISSION,"无权限操作","无权限操作");
    }
    
}
