package cn.com.xinxin.sass.auth.web;


import cn.com.xinxin.sass.auth.model.SassUserInfo;
import cn.com.xinxin.sass.auth.protocol.SessionBizResultCodeEnum;
import cn.com.xinxin.sass.auth.repository.UserAclTokenRepository;
import cn.com.xinxin.sass.auth.utils.HttpRequestUtil;
import cn.com.xinxin.sass.auth.utils.JWTUtil;
import com.xinxinfinance.commons.exception.BusinessException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;

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

}
