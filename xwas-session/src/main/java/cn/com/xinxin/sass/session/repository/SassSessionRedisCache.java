package cn.com.xinxin.sass.session.repository;

import cn.com.xinxin.sass.session.constant.SessionCacheConstants;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * @author: zhouyang
 * @created: 14/04/2020.
 * @updater:
 * @description: redis session cache的实现类
 */
public class SassSessionRedisCache<K,V> implements Cache<K,V> {


    private static final Logger log = LoggerFactory.getLogger(SassSessionRedisCache.class);


    private RedisTemplate sessionRedisTemplate;

    private String cacheKey;

    private K getCacheKey(Object key){
        return (K) (this.cacheKey + key);
    }

    public SassSessionRedisCache(RedisTemplate sessionRedisTemplate, String name) {
        this.sessionRedisTemplate = sessionRedisTemplate;
        this.cacheKey = SessionCacheConstants.SASS_USER_SESSION_CACHE_KEY + name + ":";
    }

    @Override
    public V get(K k) throws CacheException {
        log.debug("PortalShiroSessionRedisCache get key :" + k);
        return (V) sessionRedisTemplate.opsForValue().get(getCacheKey(k));
    }

    @Override
    public V put(K k, V v) throws CacheException {
        log.debug("PortalShiroSessionRedisCache put key :" + k);
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
        sessionRedisTemplate.delete(SessionCacheConstants.SASS_USER_SESSION_CACHE_KEY + "*");
    }

    @Override
    public int size() {
        Long size = sessionRedisTemplate.opsForValue().size(SessionCacheConstants.SASS_USER_SESSION_CACHE_KEY + "*");
        return size.intValue();
    }

    @Override
    public Set<K> keys() {
        Set keys = sessionRedisTemplate.keys(SessionCacheConstants.SASS_USER_SESSION_CACHE_KEY + "*");
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
        Set keys = sessionRedisTemplate.keys(SessionCacheConstants.SASS_USER_SESSION_CACHE_KEY + "*");
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
