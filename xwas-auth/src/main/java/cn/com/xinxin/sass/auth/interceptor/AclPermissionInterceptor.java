package cn.com.xinxin.sass.auth.interceptor;


import cn.com.xinxin.sass.auth.annotation.RequirePermission;
import cn.com.xinxin.sass.auth.model.SassUserInfo;
import cn.com.xinxin.sass.auth.repository.UserAclTokenRepository;
import cn.com.xinxin.sass.auth.utils.HttpRequestUtil;
import cn.com.xinxin.sass.auth.utils.JWTUtil;
import com.xinxinfinance.commons.exception.BusinessException;
import com.xinxinfinance.commons.result.CommonResultCode;
import com.xinxinfinance.commons.util.ApplicationContextHolder;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Set;

/**
 * @author: zhouyang
 * @created: 16/07/2018.
 * @updater:
 * @description:
 */
public class AclPermissionInterceptor implements MethodInterceptor {

    private static final Logger log = LoggerFactory.getLogger(AclPermissionInterceptor.class);

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        Method method = methodInvocation.getMethod();
        Annotation permissionAnnotation = method.getAnnotation(RequirePermission.class);

        log.info("AclPermissionInterceptor.invoke,method:{}",method.getName());

        if (permissionAnnotation != null
                && ((RequirePermission) permissionAnnotation).value() != null
                && methodInvocation.getArguments() != null
                && methodInvocation.getArguments().length != 0){
            boolean hasPermission = false;

            for (Object o : methodInvocation.getArguments()) {
                if (o instanceof HttpServletRequest){
                    HttpServletRequest request = (HttpServletRequest) o;
                    String[] values = ((RequirePermission) permissionAnnotation).value();

                    UserAclTokenRepository userAclTokenRepository = (UserAclTokenRepository) ApplicationContextHolder.getBean("userAclTokenRepository");

                    hasPermission = hasPermission(request, values, userAclTokenRepository);
                }
            }

            if (hasPermission){
                return methodInvocation.proceed();
            }else {
                Type returnType = method.getGenericReturnType();
                if (returnType.getTypeName().equals("java.lang.String")){
                    return "unauthorized";
                }else if (returnType.getTypeName().equals("org.springframework.web.servlet.ModelAndView")){
                    ModelAndView mav = new ModelAndView("unauthorized");
                    return mav;
                }
                throw new BusinessException(CommonResultCode.ILLEGAL_ARGUMENT,"无权限");
            }

        }

        return methodInvocation.proceed();
    }

    private boolean hasPermission(HttpServletRequest request,
                                  String[] values,
                                  UserAclTokenRepository userAclTokenRepository) {


        String token = HttpRequestUtil.getLoginToken(request);
        String account = JWTUtil.getUserAccount(token);
        SassUserInfo sassUserInfo = userAclTokenRepository.getSassUserByUserAccount(account);
        if (sassUserInfo == null){
            return false;
        }

        Set<String> permissions = sassUserInfo.getStringPermissions();
        if (CollectionUtils.isEmpty(permissions)){
            return false;
        }
        boolean hasPermission = false;
        for (String value : values) {
             if (!permissions.contains(value)){
                 hasPermission = false;
                 break;
             }else {
                 hasPermission = true;
             }
        }

        return hasPermission;
    }
}
