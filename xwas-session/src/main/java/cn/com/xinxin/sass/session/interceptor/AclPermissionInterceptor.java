package cn.com.xinxin.sass.session.interceptor;


import cn.com.xinxin.sass.session.annotation.RequirePermission;
import cn.com.xinxin.sass.session.model.SassUserInfo;
import cn.com.xinxin.sass.session.repository.UserAclTokenRepository;
import com.xinxinfinance.commons.exception.BusinessException;
import com.xinxinfinance.commons.portal.view.result.PortalPageViewResultVO;
import com.xinxinfinance.commons.portal.view.result.PortalSingleViewResultVO;
//import com.xinxinfinance.commons.portal.xxjr.job.ReturnT;
import com.xinxinfinance.commons.result.CommonResultCode;
import com.xinxinfinance.commons.util.ApplicationContextHolder;
import com.xxl.job.core.biz.model.ReturnT;
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
                    return "common/unauthorized";
                }else if (returnType.getTypeName().equals("org.springframework.web.servlet.ModelAndView")){
                    ModelAndView mav = new ModelAndView("common/unauthorized");
                    return mav;
                }else if (returnType.getTypeName().equals("com.xinxinfinance.commons.portal.view.result.PortalSingleViewResultVO")){
                    /**
                     * 注意，controller的返回值一定要是SingleResponseVO，不能写Object
                     */
                    PortalSingleViewResultVO portalSingleViewResultVO = new PortalSingleViewResultVO();
                    portalSingleViewResultVO.setError(true);
                    portalSingleViewResultVO.setErrorMsg("您没有相应的权限");
                    return portalSingleViewResultVO;
                }else if (returnType.getTypeName().equals("com.xinxinfinance.commons.portal.view.result.PortalPageViewResultVO")){
                    /**
                     * 注意，controller的返回值一定要是PageResponseVO，不能写Object
                     */
                    PortalPageViewResultVO portalPageViewResultVO = new PortalPageViewResultVO();
                    portalPageViewResultVO.setError(true);
                    portalPageViewResultVO.setErrorMsg("您没有相应的权限");
                    return portalPageViewResultVO;
                }else if (returnType.getTypeName().equals("com.xinxinfinance.commons.portal.xxjr.job.ReturnT")){
                //}else if (returnType.getTypeName().equals("com.xxl.job.core.biz.model.ReturnT")){
                    ReturnT returnT = new ReturnT();
                    returnT.setCode(ReturnT.FAIL_CODE);
                    returnT.setMsg("您没有相应的权限");
                    return returnT;
                }
                throw new BusinessException(CommonResultCode.ILLEGAL_ARGUMENT,"无权限");
            }

        }

        return methodInvocation.proceed();
    }

    private boolean hasPermission(HttpServletRequest request, String[] values, UserAclTokenRepository userAclTokenRepository) {
        SassUserInfo sassUserInfo = userAclTokenRepository.getPortalUserBySessionId(request.getRequestedSessionId());
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
