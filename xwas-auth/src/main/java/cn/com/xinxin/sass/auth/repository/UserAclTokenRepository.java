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


    public void cleanSassUserTokenCache(String account){
        try {
            sessionRedisTemplate.delete(SessionCacheConstants.SASS_USER_TOKEN_CACHE_KEY + account);
            logger.error("UserAclTokenRepository.delete user cache account :\n[]", account);
        } catch (Exception e) {
            logger.error("UserAclTokenRepository.delete occurs exception:\n[]",e);
        }
    }

    public void cleanSassUserInfoCache(String account){
        try {
            sessionRedisTemplate.delete(SessionCacheConstants.SASS_USER_INFO_CACHE_KEY + account);
            logger.error("UserAclTokenRepository.delete user cache account :\n[]", account);
        } catch (Exception e) {
            logger.error("UserAclTokenRepository.delete occurs exception:\n[]",e);
        }
    }
}
