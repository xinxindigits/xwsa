package cn.com.xinxin.sass.auth.repository;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: zhouyang
 * @created: 14/04/2020.
 * @updater:
 * @description: shiro auth redis cache 管理类
 */
public class SassSessionRedisCacheManager implements CacheManager {

    private final ConcurrentHashMap<String,Cache> caches = new ConcurrentHashMap<>();

    private RedisTemplate sessionRedisTemplate;

    @Override
    public <K, V> Cache<K, V> getCache(String s) throws CacheException {
        Cache cache = caches.get(s);
        if (cache == null){
            cache = new SassSessionRedisCache(sessionRedisTemplate,s);
            caches.put(s,cache);
        }

        return cache;
    }


    public RedisTemplate getSessionRedisTemplate() {
        return sessionRedisTemplate;
    }

    public void setSessionRedisTemplate(RedisTemplate sessionRedisTemplate) {
        this.sessionRedisTemplate = sessionRedisTemplate;
    }

}
