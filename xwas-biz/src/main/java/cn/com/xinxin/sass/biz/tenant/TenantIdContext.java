package cn.com.xinxin.sass.biz.tenant;



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
