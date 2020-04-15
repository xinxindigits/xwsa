package cn.com.xinxin.sass.auth.web;


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

    protected String getSessionId(HttpServletRequest request){

        String sessionId = request.getRequestedSessionId();
        if(StringUtils.isEmpty(sessionId)){
            throw new BusinessException(SessionBizResultCodeEnum.INVALID_SESSION_ID,"无法获取用户登陆信息，清查看是否登陆成功");
        }
        logger.info("getSessionId ,sessionid={}", sessionId);
        return sessionId;
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
        Map<String, String> resultMap = new HashMap();
        resultMap.put("code", SessionBizResultCodeEnum.AUTHENTICATE_FAIL.getCode());
        resultMap.put("message", SessionBizResultCodeEnum.AUTHENTICATE_FAIL.getAlertMessage());
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
            e.getMessage();
        }
        return null;

    }
    /**
     * 权限异常
     *
     * @param request
     * @param response
     * @return
     */
    @ExceptionHandler({UnauthorizedException.class,
            AuthorizationException.class,
            UnauthorizedException.class})
    public Object authorizationException(HttpServletRequest request, HttpServletResponse response) {

        logger.info("未授权的操作请求");
        Map<String, String> resultMap = new HashMap();
        resultMap.put("code", SessionBizResultCodeEnum.NO_PERMISSION.getCode());
        resultMap.put("message", SessionBizResultCodeEnum.NO_PERMISSION.getAlertMessage());
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
        //throw new BusinessException(SessionBizResultCodeEnum.NO_PERMISSION,"无权限操作","无权限操作");
        return null;
    }
    
}
