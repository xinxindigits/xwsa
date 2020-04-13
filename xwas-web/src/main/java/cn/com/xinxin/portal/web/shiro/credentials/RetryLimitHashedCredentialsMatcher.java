package cn.com.xinxin.portal.web.shiro.credentials;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by dengyunhui on 2018/4/23
 **/
@Deprecated
public class RetryLimitHashedCredentialsMatcher extends HashedCredentialsMatcher {

    private Cache<String,AtomicInteger> passwordRetryCache;
    private CacheManager cacheManager;

    public RetryLimitHashedCredentialsMatcher(CacheManager cacheManager){
        passwordRetryCache = cacheManager.getCache("passwordRetryCache");
    }

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        String username = (String) token.getPrincipal();
        AtomicInteger count = passwordRetryCache.get(username);
        if (count == null){
            count = new AtomicInteger(0);
            passwordRetryCache.put(username,count);
        }

        if (count.incrementAndGet() > 5){
            throw new ExcessiveAttemptsException("");
        }

        boolean matches = super.doCredentialsMatch(token, info);
        if (matches){
            passwordRetryCache.remove(username);
        }

        return matches;
    }
}
