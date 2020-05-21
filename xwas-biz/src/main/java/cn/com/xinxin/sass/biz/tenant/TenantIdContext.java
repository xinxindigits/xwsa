package cn.com.xinxin.sass.biz.tenant;

import com.xinxinfinance.commons.trace.TraceIdContext;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.Callable;

/**
 * @author: zhouyang
 * @created: 21/05/2020.
 * @updater:
 * @description: 租户相关的上下文信息
 */
public class TenantIdContext {

    private static final ThreadLocal<String> TENANT_ID_HOLDER = new ThreadLocal<>();

    /**
     * 获取当前线程的TraceId。
     *
     * @return 当前线程的TradeId，如果没有则返回{@code null}。
     */
    @Nullable
    public static String get()
    {
        return TENANT_ID_HOLDER.get();
    }

    /**
     * 设置当前线程的TraceId。
     *
     * @param traceId TraceId，如果为{@code null}则和{@link #remove()}等价。
     */
    public static void set(final @Nullable String traceId)
    {
        TENANT_ID_HOLDER.set(traceId);
    }

    /**
     * 移除当前线程的TraceId。
     */
    public static void remove()
    {
        TENANT_ID_HOLDER.remove();
    }

    /**
     * 将当前线程的TraceId传递到执行{@code action}的线程内。
     *
     * @param action 无返回值的闭包。
     *
     * @return 持有当前线程TraceId的action。
     */
    public static Runnable transfer(final Runnable action)
    {
        // 捕获当前线程的TraceId。
        final String traceId = TraceIdContext.get();
        return () ->
        {
            // 设置执行线程的TraceId。
            TraceIdContext.set(traceId);
            try
            {
                action.run();
            } finally
            {
                // 清空执行线程的TraceId。
                TraceIdContext.remove();
            }
        };
    }

    /**
     * 将当前线程的TraceId传递到执行{@code funct}的线程内。
     *
     * @param func 有返回值的闭包。
     *
     * @return 持有当前线程TraceId的func。
     */
    public static <T> Callable<T> transfer(final Callable<T> func)
    {
        // 捕获当前线程的TraceId。
        final String traceId = TraceIdContext.get();
        return () ->
        {
            // 设置执行线程的TraceId。
            TraceIdContext.set(traceId);
            try
            {
                return func.call();
            } finally
            {
                // 清空执行线程的TraceId。
                TraceIdContext.remove();
            }
        };
    }

}
