package cn.com.xinxin.sass.biz.log;

import java.lang.annotation.*;

/**
 * @author: zhouyang
 * @created: 09/05/2020.
 * @updater:
 * @description: 操作日志注解
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SysLog {

    String value() default "";

    /**
     * 记录执行参数
     *
     * @return
     */
    boolean request() default true;

    /**
     * 记录返回参数
     *
     * @return
     */
    boolean response() default true;

}