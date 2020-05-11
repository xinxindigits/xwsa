package cn.com.xinxin.sass.biz.log;

import cn.com.xinxin.sass.biz.service.OplogService;
import cn.com.xinxin.sass.auth.context.SassBaseContextHolder;
import cn.com.xinxin.sass.common.utils.CommonHttpRequestUtil;
import cn.com.xinxin.sass.repository.model.OplogDOWithBLOBs;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;


import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;

/**
 * @author: zhouyang
 * @created: 09/05/2020.
 * @updater:
 * @description:
 */
@Component
@Aspect
public class SysLogAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(SysLogAspect.class);

    @Autowired
    private OplogService oplogService;

    /**
     * 用于获取方法参数定义名字.
     */
    private DefaultParameterNameDiscoverer nameDiscoverer = new DefaultParameterNameDiscoverer();

    /**
     * 用于SpEL表达式解析.
     */
    private SpelExpressionParser spelExpressionParser = new SpelExpressionParser();

    /**
     *   定义切点 @Pointcut
     *   在注解的位置切入代码
     */
    @Pointcut("@annotation(cn.com.xinxin.sass.biz.log.SysLog)")
    public void logPoinCut() {
        LOGGER.info("SysLogAspect,syslog");
    }

    //切面 配置通知
    @AfterReturning("logPoinCut()")
    public void saveSysLog(JoinPoint joinPoint) {
        LOGGER.info("saveSysLog");
        //保存日志
        OplogDOWithBLOBs oplog = new OplogDOWithBLOBs();

        //从切面织入点处通过反射机制获取织入点处的方法
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        //获取切入点所在的方法
        Method method = signature.getMethod();

        //获取操作
        SysLog sysLog = method.getAnnotation(SysLog.class);
        if (sysLog != null) {
            String value = sysLog.value();
            //保存获取的操作
            oplog.setOperation(value);
        }

        String controllerDescription = "";
        String controllerMethodDescription = sysLog.value();

        if (StringUtils.isNotEmpty(controllerMethodDescription)) {
            //获取方法参数值
            Object[] args = joinPoint.getArgs();
            MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
            //controllerMethodDescription = getValBySpEL(controllerMethodDescription, methodSignature, args);
        }


        if (StringUtils.isEmpty(controllerDescription)) {
            oplog.setExtension(controllerMethodDescription);
        } else {
            oplog.setExtension(controllerDescription + "-" + controllerMethodDescription);
        }

        //获取请求的类名
        String className = joinPoint.getTarget().getClass().getName();
        //获取请求的方法名
        String methodName = method.getName();
        oplog.setMethod(className + "." + methodName);


        //请求的参数
        Object[] args = joinPoint.getArgs();
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        //将参数所在的数组转换成json
        String params = getArgs(sysLog,args,request);
        oplog.setParams(params);



        if (request != null) {
            //获取用户ip地址
            oplog.setIp(CommonHttpRequestUtil.getIpAddress(request));
            oplog.setUa(CommonHttpRequestUtil.getRequestDevice(request));
            oplog.setUri(CommonHttpRequestUtil.getPath(request.getRequestURI()));
            oplog.setHttpMethod(request.getMethod());
            oplog.setUa(StringUtils.substring(request.getHeader("user-agent"), 0, 256));
            oplog.setTenantId(SassBaseContextHolder.getTenantId());

        }
        //获取用户名
        oplog.setAccount(SassBaseContextHolder.getAccount());
        oplog.setGmtCreator(SassBaseContextHolder.getAccount());
        oplog.setGmtUpdater(SassBaseContextHolder.getAccount());

        //调用service保存SysLog实体类到数据库
        oplogService.createOplog(oplog);

    }



    private String getArgs(SysLog sysLogAnno, Object[] args, HttpServletRequest request) {
        String strArgs = "";
        try {
            if (!request.getContentType().contains("multipart/form-data")) {
                strArgs = JSONObject.toJSONString(args);
            }
        } catch (Exception e) {
            try {
                strArgs = Arrays.toString(args);
            } catch (Exception ex) {
                LOGGER.warn("解析参数异常", ex);
            }
        }
        return strArgs;
    }

    /**
     * 解析spEL表达式
     */
    private String getValBySpEL(String spEL, MethodSignature methodSignature, Object[] args) {
        try {
            //获取方法形参名数组
            String[] paramNames = nameDiscoverer.getParameterNames(methodSignature.getMethod());
            if (paramNames != null && paramNames.length > 0) {
                Expression expression = spelExpressionParser.parseExpression(spEL);
                // spring的表达式上下文对象
                EvaluationContext context = new StandardEvaluationContext();
                // 给上下文赋值
                for (int i = 0; i < args.length; i++) {
                    context.setVariable(paramNames[i], args[i]);
                    context.setVariable("p" + i, args[i]);
                }
                return expression.getValue(context).toString();
            }
        } catch (Exception e) {
            LOGGER.warn("解析操作日志的el表达式出错", e);
        }
        return spEL;
    }

}
