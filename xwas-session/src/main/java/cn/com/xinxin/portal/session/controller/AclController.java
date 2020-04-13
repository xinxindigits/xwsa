package cn.com.xinxin.portal.session.controller;


import cn.com.xinxin.portal.session.model.PortalUser;
import cn.com.xinxin.portal.session.protocol.SessionBizResultCodeEnum;
import cn.com.xinxin.portal.session.repository.UserAclSessionRepository;
import cn.com.xinxin.portal.session.utils.HttpRequestUtil;
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
    private UserAclSessionRepository userAclSessionRepository;

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

    protected PortalUser getPortalUser(HttpServletRequest request){

        String userSessionId = this.getSessionId(request);

        PortalUser portalUser = userAclSessionRepository.getPortalUserBySessionId(userSessionId);

        portalUser.setIp(HttpRequestUtil.getIpAddress(request));
        portalUser.setDevice(HttpRequestUtil.getRequestDevice(request));
        return portalUser;

    }

    protected Long getUserId(HttpServletRequest request) {

        PortalUser portalUser = this.getPortalUser(request);
        if(null == portalUser){
            throw new BusinessException(SessionBizResultCodeEnum.INVALID_SESSION_ID,"无法获取用户登陆信息，清查看是否登陆成功");
        }
        return portalUser.getId();
    }


    protected String getUserName(HttpServletRequest request){

        PortalUser portalUser = this.getPortalUser(request);
        if(null == portalUser){
            throw new BusinessException(SessionBizResultCodeEnum.INVALID_SESSION_ID,"无法获取用户登陆信息，清查看是否登陆成功");
        }
        return portalUser.getName();
    }

    protected String getUserAccount(HttpServletRequest request){

        PortalUser portalUser = this.getPortalUser(request);
        if(null == portalUser){
            throw new BusinessException(SessionBizResultCodeEnum.INVALID_SESSION_ID,"无法获取用户登陆信息，清查看是否登陆成功");
        }
        return portalUser.getAccount();
    }

    protected String getUserNo(HttpServletRequest request){
        PortalUser portalUser = this.getPortalUser(request);
        if(null == portalUser){
            throw new BusinessException(SessionBizResultCodeEnum.INVALID_SESSION_ID,"无法获取用户登陆信息，清查看是否登陆成功");
        }
        return portalUser.getNo();
    }

    protected String getUserIp(HttpServletRequest request){

        PortalUser portalUser = this.getPortalUser(request);
        if(null == portalUser){
            throw new BusinessException(SessionBizResultCodeEnum.INVALID_SESSION_ID,"无法获取用户登陆信息，清查看是否登陆成功");
        }
        return portalUser.getIp();
    }

    protected String getUserDevice(HttpServletRequest request){

        PortalUser portalUser = this.getPortalUser(request);
        if(null == portalUser){
            throw new BusinessException(SessionBizResultCodeEnum.INVALID_SESSION_ID,"无法获取用户登陆信息，清查看是否登陆成功");
        }
        return portalUser.getDevice();
    }

}
