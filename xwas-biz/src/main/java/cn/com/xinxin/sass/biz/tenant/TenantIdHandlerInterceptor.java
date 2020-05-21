package cn.com.xinxin.sass.biz.tenant;

import cn.com.xinxin.sass.auth.utils.HttpRequestUtil;
import cn.com.xinxin.sass.auth.utils.JWTUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author: zhouyang
 * @created: 21/05/2020.
 * @updater:
 * @description:
 */
public class TenantIdHandlerInterceptor extends HandlerInterceptorAdapter {

    private final static String DEFAULT_TEANT_ID = "xinxin";

    private static final Logger log = LoggerFactory.getLogger(TenantIdHandlerInterceptor.class);

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler)
    {
        // 从请求中获取token，读取租户ID
        // 登陆请求除外
        String token = HttpRequestUtil.getLoginToken(request);
        if (StringUtils.isEmpty(token))
        {
            log.error("token无效");
        }
        String tenantId = JWTUtil.getUserTeantId(token);
        // Generate a new one
        if (StringUtils.isEmpty(tenantId))
        {
            tenantId = DEFAULT_TEANT_ID;
        }
        TenantIdContext.set(tenantId);
        return true;
    }

    @Override
    public void afterCompletion(final HttpServletRequest request, final HttpServletResponse response, final Object handler, final Exception ex)
    {
        // Cleanup, prepare for handling next request on this thread.
        TenantIdContext.remove();
    }
}
