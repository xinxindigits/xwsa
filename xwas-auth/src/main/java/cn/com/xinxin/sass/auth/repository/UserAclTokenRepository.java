package cn.com.xinxin.sass.auth.repository;


import cn.com.xinxin.sass.auth.constant.SessionCacheConstants;
import cn.com.xinxin.sass.auth.model.SassUserInfo;
import cn.com.xinxin.sass.auth.protocol.SessionBizResultCodeEnum;
import cn.com.xinxin.sass.auth.utils.JWTUtil;
import com.xinxinfinance.commons.exception.BusinessException;
import com.xinxinfinance.commons.result.CommonResultCode;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;


import javax.servlet.http.HttpServletResponse;
import java.util.Date;
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

    // 暂时不失效
//    public void setSassUserTokenCache(String account, String token){
//        sessionRedisTemplate.opsForValue().set(
//                SessionCacheConstants.SASS_USER_TOKEN_CACHE_KEY + account,
//                token, JWTUtil.TOKEN_EXPIRE_TIME,TimeUnit.MILLISECONDS);
//
//    }

    public void setSassUserTokenCache(String account, String token){
        sessionRedisTemplate.opsForValue().set(
                SessionCacheConstants.SASS_USER_TOKEN_CACHE_KEY + account,
                token);

    }

    public SassUserInfo getSassUserByUserAccount(String account){

        SassUserInfo sassUserInfo = (SassUserInfo) sessionRedisTemplate.opsForValue().get(
            SessionCacheConstants.SASS_USER_INFO_CACHE_KEY + account);

        return sassUserInfo;

    }

    public void setSassUserByUserAccount(String account, SassUserInfo sassUserInfo){
        sessionRedisTemplate.opsForValue().set(
            SessionCacheConstants.SASS_USER_INFO_CACHE_KEY + account,
                sassUserInfo);
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


    public void refreshToken(String token,  HttpServletResponse httpServletResponse) throws Exception{
        // 刷新token
        if (StringUtils.isBlank(token)) {
            throw new BusinessException(CommonResultCode.SYSTEM_ERROR,"无效token","无效token");
        }
        // 获取过期时间
        Date expireDate = JWTUtil.getExpiresTime(token);
        String account = JWTUtil.getUserAccount(token);
        // 如果(当前时间+倒计时)>过期时间，则刷新token
        boolean isNeedrefresh = DateUtils.addSeconds(new Date(),JWTUtil.TOKEN_EXPIRE_TIME_COUNT).after(expireDate);
        if (isNeedrefresh) {
            // 不需要刷新
            // 需要刷新token
            // 如果在redis中已经存在token，则表示在某个地方已经刷新了缓存
            String cachedToken = this.getSassUserCacheToken(account);
            if(StringUtils.isEmpty(cachedToken)){
                httpServletResponse.setStatus(SessionCacheConstants.JWT_INVALID_TOKEN_CODE);
                throw new BusinessException(SessionBizResultCodeEnum.INVALID_TOKEN,"token已无效，请使用已刷新的token");
            }

            Date cachedTokenExpiredDate = JWTUtil.getExpiresTime(cachedToken);

            if(!DateUtils.isSameInstant(expireDate,cachedTokenExpiredDate)){
                // 如果两个token的时间不一致，则表示使用的不是同一个token，需要重新登陆使用新的token
                throw new BusinessException(SessionBizResultCodeEnum.INVALID_TOKEN,"token已无效，请重新登陆");
            }

            // 获取用户的信息重新生成token
            SassUserInfo sassUserInfo = this.getSassUserByUserAccount(account);
            String newToken = JWTUtil.sign(sassUserInfo.getAccount(),sassUserInfo.getPassword());
            // 更新缓存中的token
            this.setSassUserTokenCache(account,newToken);
            // 设置响应头
            // 刷新token
            httpServletResponse.setStatus(SessionCacheConstants.JWT_REFRESH_TOKEN_CODE);
            httpServletResponse.setHeader(JWTUtil.TOKEN_NAME, newToken);
        }else{
            logger.error("UserAclTokenRepository.refreshToken no need account :\n[]", account);
            return;
        }

    }
}
