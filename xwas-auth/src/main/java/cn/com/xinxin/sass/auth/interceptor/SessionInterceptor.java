package cn.com.xinxin.sass.auth.interceptor;


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
