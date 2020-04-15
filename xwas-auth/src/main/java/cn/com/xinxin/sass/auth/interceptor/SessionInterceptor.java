package cn.com.xinxin.sass.auth.interceptor;


import cn.com.xinxin.sass.auth.model.SassUserInfo;
import cn.com.xinxin.sass.auth.repository.UserAclTokenRepository;
import cn.com.xinxin.sass.auth.utils.HttpRequestUtil;
import cn.com.xinxin.sass.auth.utils.JWTUtil;
import com.xinxinfinance.commons.exception.BusinessException;
import com.xinxinfinance.commons.result.CommonResultCode;
import com.xinxinfinance.commons.util.ApplicationContextHolder;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * @author dengyunhui
 * @created 2018/12/26 11:51
 * @updated
 * @description
 **/
public class SessionInterceptor implements MethodInterceptor {
    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        Method method = methodInvocation.getMethod();
        if (Modifier.isPublic(method.getModifiers()) && methodInvocation.getArguments() != null && methodInvocation.getArguments().length > 0){
            for (Object argument : methodInvocation.getArguments()) {
                if (argument instanceof HttpServletRequest){
                    HttpServletRequest request = (HttpServletRequest) argument;
                    // 获取用户token并且拿到用户信息
                    String token = HttpRequestUtil.getLoginToken(request);
                    String account = JWTUtil.getUserAccount(token);
                    if (request.getRequestedSessionId() != null){
                        UserAclTokenRepository userAclTokenRepository = (UserAclTokenRepository) ApplicationContextHolder.getBean("userAclTokenRepository");
                        String cacheToken = userAclTokenRepository.getSassUserCacheToken(account);
                        if (StringUtils.isNotEmpty(cacheToken)){
                            //auth 存在，更新token超时时间
                            userAclTokenRepository.setSassUserTokenCache(account,token);
                            return methodInvocation.proceed();
                        }else {
                            throw new BusinessException(CommonResultCode.ILLEGAL_ARGUMENT,"无效token");
                        }
                    }
                }
            }
        }

        return methodInvocation.proceed();
    }
}
