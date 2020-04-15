package cn.com.xinxin.sass.auth.repository;


import cn.com.xinxin.sass.auth.constant.SessionCacheConstants;
import cn.com.xinxin.sass.auth.model.SassUserInfo;
import com.xinxinfinance.commons.exception.BusinessException;
import com.xinxinfinance.commons.result.CommonResultCode;
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
public class UserAclTokenRepository {

    private static final Logger logger = LoggerFactory.getLogger(UserAclTokenRepository.class);

    private RedisTemplate sessionRedisTemplate;


    public RedisTemplate getSessionRedisTemplate() {
        return sessionRedisTemplate;
    }

    public void setSessionRedisTemplate(RedisTemplate sessionRedisTemplate) {
        this.sessionRedisTemplate = sessionRedisTemplate;
    }


    public String getSassUserCacheToken(String account){
        String cacheToken = (String)sessionRedisTemplate.opsForValue().get(
                SessionCacheConstants.SASS_USER_TOKEN_CACHE_KEY + account);
        return cacheToken;
    }

    public void setSassUserTokenCache(String account, String token){
        sessionRedisTemplate.opsForValue().set(
                SessionCacheConstants.SASS_USER_TOKEN_CACHE_KEY + account,
                token, SessionCacheConstants.DEFAULT_SESSION_TIME_OUT,TimeUnit.MINUTES);

    }

    public Session getSassUserLoginSession(String sessionId){
        Session session = (Session) sessionRedisTemplate.opsForValue().get(
            SessionCacheConstants.SASS_SESSION_MANAGER_CACHE_KEY + sessionId);
        return session;

    }

    public void setSassUserSession(String sessionId, Session session){
        sessionRedisTemplate.opsForValue().set(
            SessionCacheConstants.SASS_SESSION_MANAGER_CACHE_KEY + sessionId,
            session, SessionCacheConstants.DEFAULT_SESSION_TIME_OUT,TimeUnit.MINUTES);
    }

    public void updateSassUserSession(String sessionId){
        sessionRedisTemplate.expire(SessionCacheConstants.SASS_SESSION_MANAGER_CACHE_KEY + sessionId,
                SessionCacheConstants.DEFAULT_SESSION_TIME_OUT,TimeUnit.MINUTES);
    }

    public boolean exist(String sessionId){
        return sessionRedisTemplate.hasKey(SessionCacheConstants.SASS_SESSION_MANAGER_CACHE_KEY + sessionId);
    }

    public void cleanSession(String sessionId){
        try {
            String key = SessionCacheConstants.SASS_SESSION_MANAGER_CACHE_KEY + sessionId;
            sessionRedisTemplate.delete(key);
        } catch (Exception e) {
            logger.error("UserAclTokenRepository.cleanSession occurs exception:\n[]",e);
        }
    }


    public SassUserInfo getSassUserBySessionId(String sessionId){

        Session session = (Session) sessionRedisTemplate.opsForValue().get(
            SessionCacheConstants.SASS_SESSION_MANAGER_CACHE_KEY + sessionId);

        if (session == null){
            //TODO
            throw new BusinessException(CommonResultCode.ILLEGAL_ARGUMENT,"登陆失效，请重新登陆系统");
        }
        SassUserInfo SassUserInfo = new SassUserInfo();

        Collection<Object> keys = session.getAttributeKeys();
        if (keys != null && keys.size() > 0){
            for (Object key : keys) {
                Object value = session.getAttribute(key);
                if (value instanceof SassUserInfo){
                    SassUserInfo = (SassUserInfo) value;
                    break;
                }
            }
        }

        return SassUserInfo;
    }


    public SassUserInfo getSassUserByUserAccount(String account){

        SassUserInfo sassUserInfo = (SassUserInfo) sessionRedisTemplate.opsForValue().get(
            SessionCacheConstants.SASS_USER_INFO_CACHE_KEY + account);

        return sassUserInfo;

    }

    public void setSassUserByUserAccount(String account, SassUserInfo sassUserInfo){
        sessionRedisTemplate.opsForValue().set(
            SessionCacheConstants.SASS_USER_INFO_CACHE_KEY + account,
                sassUserInfo, SessionCacheConstants.DEFAULT_SESSION_TIME_OUT,TimeUnit.MINUTES);
    }

    public void cleanSassUserSessionCache(String account){
        try {
            sessionRedisTemplate.delete(SessionCacheConstants.SASS_USER_INFO_CACHE_KEY + account);
            logger.error("UserAclTokenRepository.delete user cache account :\n[]", account);
        } catch (Exception e) {
            logger.error("UserAclTokenRepository.delete occurs exception:\n[]",e);
        }
    }
}
