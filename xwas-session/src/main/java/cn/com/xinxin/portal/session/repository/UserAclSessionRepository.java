package cn.com.xinxin.portal.session.repository;

import cn.com.xinxin.portal.session.BizResultCodeEnum;
import cn.com.xinxin.portal.session.constant.SessionCacheConstants;
import cn.com.xinxin.portal.session.model.PortalUser;
import com.xinxinfinance.commons.exception.BusinessException;
import org.apache.shiro.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;


import java.util.Collection;
import java.util.concurrent.TimeUnit;

/**
 * @author: zhouyang
 * @created: 16/07/2018.
 * @updater:
 * @description: session用户读取类信息
 */
public class UserAclSessionRepository {

    private static final Logger logger = LoggerFactory.getLogger(UserAclSessionRepository.class);

    private RedisTemplate sessionRedisTemplate;


    public RedisTemplate getSessionRedisTemplate() {
        return sessionRedisTemplate;
    }

    public void setSessionRedisTemplate(RedisTemplate sessionRedisTemplate) {
        this.sessionRedisTemplate = sessionRedisTemplate;
    }


    private String getKey(String originalKey) {
        return SessionCacheConstants.XPORTAL_SHIRO_USER_SESSION_CACHE_KEY + originalKey;
    }

    public Session getPortalUserLoginSession(String sessionId){
        Session session = (Session) sessionRedisTemplate.opsForValue().get(
            SessionCacheConstants.XPORTAL_SHIRO_USER_SESSION_CACHE_KEY + sessionId);

        return session;

    }

    public void setPortalUserSession(String sessionId, Session session){
        sessionRedisTemplate.opsForValue().set(
            SessionCacheConstants.XPORTAL_SHIRO_USER_SESSION_CACHE_KEY + sessionId,
            session, SessionCacheConstants.DEFAULT_SESSION_TIME_OUT,TimeUnit.MINUTES);
    }

    public void updatePortalUserSession(String sessionId){
        sessionRedisTemplate.expire(SessionCacheConstants.XPORTAL_SHIRO_USER_SESSION_CACHE_KEY + sessionId,
                SessionCacheConstants.DEFAULT_SESSION_TIME_OUT,TimeUnit.MINUTES);
    }

    public boolean exist(String sessionId){
        return sessionRedisTemplate.hasKey(SessionCacheConstants.XPORTAL_SHIRO_USER_SESSION_CACHE_KEY + sessionId);
    }

    public void cleanSession(String sessionId){
        try {
            String key = getKey(sessionId);
            sessionRedisTemplate.delete(key);
        } catch (Exception e) {
            logger.error("UserAclSessionRepository.cleanSession occurs exception:\n[]",e);
        }
    }


    public PortalUser getPortalUserBySessionId(String sessionId){

        Session session = (Session) sessionRedisTemplate.opsForValue().get(
            SessionCacheConstants.XPORTAL_SHIRO_USER_SESSION_CACHE_KEY + sessionId);

        if (session == null){
            //TODO
            throw new BusinessException(BizResultCodeEnum.NO_EXIST_SESSION,"登陆失效，请重新登陆系统");
        }
        PortalUser PortalUser = new PortalUser();

        Collection<Object> keys = session.getAttributeKeys();
        if (keys != null && keys.size() > 0){
            for (Object key : keys) {
                Object value = session.getAttribute(key);
                if (value instanceof PortalUser){
                    PortalUser = (PortalUser) value;
                    break;
                }
            }
        }

        return PortalUser;
    }


    public PortalUser getPortalUserByUserNo(String userNo){

        PortalUser portalUser = (PortalUser) sessionRedisTemplate.opsForValue().get(
            SessionCacheConstants.PORTAL_USER_INFO_CACHE_KEY + userNo);

        return portalUser;

    }

    public void setPortalUserByUserNo(String userNo, PortalUser portalUser){
        sessionRedisTemplate.opsForValue().set(
            SessionCacheConstants.PORTAL_USER_INFO_CACHE_KEY + userNo,
            portalUser, SessionCacheConstants.DEFAULT_SESSION_TIME_OUT,TimeUnit.MINUTES);
    }

    public void cleanPortalUser(String userNo){
        try {
            sessionRedisTemplate.delete(SessionCacheConstants.PORTAL_USER_INFO_CACHE_KEY + userNo);
            logger.error("UserAclSessionRepository.delete user cache userNo :\n[]",userNo);
        } catch (Exception e) {
            logger.error("UserAclSessionRepository.delete occurs exception:\n[]",e);
        }
    }
}
