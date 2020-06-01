package cn.com.xinxin.sass.auth.repository;


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
            logger.info("UserAclTokenRepository.cleanSassUserTokenCache user cache account:{}", account);
        } catch (Exception e) {
            logger.error("UserAclTokenRepository.cleanSassUserTokenCache occurs exception:{}",e);
        }
    }

    public void cleanSassUserInfoCache(String account){
        try {
            sessionRedisTemplate.delete(SessionCacheConstants.SASS_USER_INFO_CACHE_KEY + account);
            logger.info("UserAclTokenRepository.cleanSassUserInfoCache user cache account:{}", account);
        } catch (Exception e) {
            logger.error("UserAclTokenRepository.cleanSassUserInfoCache occurs exception:{}",e);
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
                // 如果两个token的时间不一致，则表示使用的不是同一个token，需要重新登录使用新的token
                throw new BusinessException(SessionBizResultCodeEnum.INVALID_TOKEN,"token已无效，请重新登录");
            }

            // 获取用户的信息重新生成token
            SassUserInfo sassUserInfo = this.getSassUserByUserAccount(account);
            String newToken = JWTUtil.sign(sassUserInfo.getAccount(),
                    sassUserInfo.getTenantId(),
                    sassUserInfo.getPassword());
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
