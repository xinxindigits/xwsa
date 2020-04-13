package cn.com.xinxin.portal.session.repository;

import cn.com.xinxin.portal.session.constant.SessionCacheConstants;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * @author: zhouyang
 * @created: 16/07/2018.
 * @updater:
 * @description:
 */
public class SassShiroSessionRedisCache<K,V> implements Cache<K,V> {

    private static final Logger log = LoggerFactory.getLogger(SassShiroSessionRedisCache.class);

    private RedisTemplate sessionRedisTemplate;

    private String cacheKey;

    private K getCacheKey(Object key){
        return (K) (this.cacheKey + key);
    }

    public SassShiroSessionRedisCache(RedisTemplate sessionRedisTemplate, String name) {
        this.sessionRedisTemplate = sessionRedisTemplate;
        this.cacheKey = SessionCacheConstants.SHIRO_USER_CACHE_KEY + name + ":";
    }

    @Override
    public V get(K k) throws CacheException {
        log.debug("SassShiroSessionRedisCache get key :" + k);
        return (V) sessionRedisTemplate.opsForValue().get(getCacheKey(k));
    }

    @Override
    public V put(K k, V v) throws CacheException {
        log.debug("SassShiroSessionRedisCache put key :" + k);
        sessionRedisTemplate.opsForValue().set(getCacheKey(k),v);
        return v;
    }

    @Override
    public V remove(K k) throws CacheException {
        V v = get(k);
        sessionRedisTemplate.delete(getCacheKey(k));
        return v;
    }

    @Override
    public void clear() throws CacheException {
        sessionRedisTemplate.delete(SessionCacheConstants.SHIRO_USER_CACHE_KEY + "*");
    }

    @Override
    public int size() {
        Long size = sessionRedisTemplate.opsForValue().size(SessionCacheConstants.SHIRO_USER_CACHE_KEY + "*");
        return size.intValue();
    }

    @Override
    public Set<K> keys() {
        Set keys = sessionRedisTemplate.keys(SessionCacheConstants.SHIRO_USER_CACHE_KEY + "*");
        if (CollectionUtils.isEmpty(keys)){
            return Collections.EMPTY_SET;
        }else {
            Set<K> newKeys = new HashSet<>();
            newKeys.addAll(keys);
            return newKeys;
        }
    }

    @Override
    public Collection<V> values() {
        Set keys = sessionRedisTemplate.keys(SessionCacheConstants.SHIRO_USER_CACHE_KEY + "*");
        if (!CollectionUtils.isEmpty(keys)){
            List<V> values = new ArrayList<>(keys.size());
            for (Object key : keys){
                V value = (V) sessionRedisTemplate.opsForValue().get(getCacheKey(key));
                values.add(value);
            }
            return values;
        }else {
            return Collections.EMPTY_LIST;
        }
    }
}
