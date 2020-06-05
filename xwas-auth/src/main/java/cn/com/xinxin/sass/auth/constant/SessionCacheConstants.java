package cn.com.xinxin.sass.auth.constant;

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

    /**
     * JWT刷新新token响应状态码
     */
    public static final int JWT_REFRESH_TOKEN_CODE = 460;

    /**
     * JWT刷新新token响应状态码，
     * Redis中不存在，但jwt未过期，不生成新的token，返回361状态码
     */
    public static final int JWT_INVALID_TOKEN_CODE = 361;



}
