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
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * @author: zhouyang
 * @created: 16/06/2020.
 * @updater:
 * @description:
 */
public class ShiroSessionRedisCacheManager<K,V> implements Cache<K,V> {


    private static final Logger log = LoggerFactory.getLogger(ShiroSessionRedisCacheManager.class);

    private RedisTemplate sessionRedisTemplate;

    private String cacheKey;

    private K getCacheKey(Object key){
        return (K) (this.cacheKey + key);
    }

    public ShiroSessionRedisCacheManager(RedisTemplate sessionRedisTemplate, String name) {
        this.sessionRedisTemplate = sessionRedisTemplate;
        this.cacheKey = SessionCacheConstants.SASS_SESSION_MANAGER_CACHE_KEY + name + ":";
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
        sessionRedisTemplate.delete(SessionCacheConstants.SASS_SESSION_MANAGER_CACHE_KEY + "*");
    }

    @Override
    public int size() {
        Long size = sessionRedisTemplate.opsForValue().size(SessionCacheConstants.SASS_SESSION_MANAGER_CACHE_KEY + "*");
        return size.intValue();
    }

    @Override
    public Set<K> keys() {
        Set keys = sessionRedisTemplate.keys(SessionCacheConstants.SASS_SESSION_MANAGER_CACHE_KEY + "*");
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
        Set keys = sessionRedisTemplate.keys(SessionCacheConstants.SASS_SESSION_MANAGER_CACHE_KEY + "*");
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
