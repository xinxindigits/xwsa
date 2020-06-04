package cn.com.xinxin.sass.web.interceptor;

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

import com.xinxinfinance.commons.api.result.support.GeneralResultCode;
import com.xinxinfinance.commons.api.result.support.Results;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.FieldError;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by zhouyang on 23/02/2018.
 */

class ValidationInterceptor implements MethodInterceptor{

    private static final Logger logger = LoggerFactory.getLogger(ValidationInterceptor.class);

    public Object invoke(MethodInvocation invocation) throws Throwable {
        if (invocation.getArguments() != null && invocation.getArguments().length != 0) {
            for (Object o : invocation.getArguments()) {
                if (o instanceof BeanPropertyBindingResult) {
                    BeanPropertyBindingResult bindingResult = (BeanPropertyBindingResult) o;
                    List<FieldError> errors = bindingResult.getFieldErrors();
                    if (errors != null && !errors.isEmpty()) {
                        final String alertMessage = errors.stream()
                                .map(x -> "[" + x.getField() + "]")
                                .collect(Collectors.joining(", ", "字段 ", " 不符合规范！"));
                        final String debugMessage = errors.stream()
                                .map(x -> '"' + x.toString() + '"')
                                .collect(Collectors.joining(", ", "[", "]"));
                        return Results.commonResult(GeneralResultCode.ILLEGAL_ARGUMENT, alertMessage, debugMessage);
                    }
                    break;
                }
            }
        }
        Object result = invocation.proceed();
        return result;
    }

}
