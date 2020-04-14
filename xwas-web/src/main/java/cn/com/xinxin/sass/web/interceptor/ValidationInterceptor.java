package cn.com.xinxin.sass.web.interceptor;

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
