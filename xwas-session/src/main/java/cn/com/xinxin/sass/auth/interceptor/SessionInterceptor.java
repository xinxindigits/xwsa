package cn.com.xinxin.sass.auth.interceptor;


import cn.com.xinxin.sass.auth.repository.UserAclTokenRepository;
import com.xinxinfinance.commons.exception.BusinessException;
import com.xinxinfinance.commons.result.CommonResultCode;
import com.xinxinfinance.commons.util.ApplicationContextHolder;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

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
                    if (request.getRequestedSessionId() != null){
                        UserAclTokenRepository userAclTokenRepository = (UserAclTokenRepository) ApplicationContextHolder.getBean("userAclTokenRepository");
                        boolean exist = userAclTokenRepository.exist(request.getRequestedSessionId());
                        if (exist){
                            //auth 存在，更新session超时时间
                            userAclTokenRepository.updatePortalUserSession(request.getRequestedSessionId());
                            return methodInvocation.proceed();
                        }else {
                            //auth 超时
                            /*Type returnType = method.getGenericReturnType();
                            if (returnType.getTypeName().equals("java.lang.String")){
                                return "login";
                            }else if (returnType.getTypeName().equals("org.springframework.web.servlet.ModelAndView")) {
                                ModelAndView mav = new ModelAndView("login");
                                return mav;
                            }*/
                            throw new BusinessException(CommonResultCode.ILLEGAL_ARGUMENT,"无效session");
                        }
                    }
                }
            }
        }

        return methodInvocation.proceed();
    }
}
