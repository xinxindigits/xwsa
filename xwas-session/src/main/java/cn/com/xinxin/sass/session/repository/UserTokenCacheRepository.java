package cn.com.xinxin.sass.session.repository;

import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author: zhouyang
 * @created: 14/04/2020.
 * @updater:
 * @description:
 */
public class UserTokenCacheRepository {



    private RedisTemplate sessionRedisTemplate;

    public RedisTemplate getSessionRedisTemplate() {
        return sessionRedisTemplate;
    }

    public void setSessionRedisTemplate(RedisTemplate sessionRedisTemplate) {
        this.sessionRedisTemplate = sessionRedisTemplate;
    }




}
