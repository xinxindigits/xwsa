package cn.com.xinxin.sass.auth.constant;

/**
 * @author: zhouyang
 * @created: 16/07/2018.
 * @updater:
 * @description:
 */
public class SessionCacheConstants {

    /**
     * auth cache key
     */
    public static final String SASS_SESSION_MANAGER_CACHE_KEY = "shiro_redis_session_manager:";

    /**
     * CHIRO_USER_CACHE_KEY
     */
    public static final String SASS_USER_SESSION_CACHE_KEY = "shiro_session_cache:";

    /**
     * session默认超时时间, 分钟为单位
     */
    public static final Integer DEFAULT_SESSION_TIME_OUT = 2160;

    /**
     * 用户信息缓存
     */
    public static final String SASS_USER_INFO_CACHE_KEY = "SASS_USER_INFO:";

    /**
     * token cache key
     */
    public static final String SASS_USER_TOKEN_CACHE_KEY = "sass_user_token:";



}
