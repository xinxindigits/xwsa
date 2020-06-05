package cn.com.xinxin.sass.common;

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

import com.google.common.collect.Lists;
import org.apache.commons.lang3.NotImplementedException;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * 静态工具类。
 *
 * @implNote 静态工具类必须有文档。
 *
 * @author MiNG
 * @version 1.0.0
 * @since 1.0.0 (2018-04-12)
 */
public final class CommonUtils
{
    /*
     * NOTE: 静态工具类必须有私有默认构造器，防止工具类被初始化。
     */
    private CommonUtils()
    {
        // No instance.
    }

    /**
     * 未实现的方法。
     *
     * @implNote 工具类方法必须有文档。
     *
     * @return 无返回。
     *
     * @throws NotImplementedException 未实现。
     */
    public static String foo()
    {
        throw new NotImplementedException("TODO");
    }

    /**
     * 将一个list分成多个指定大小的list
     * @param list 列表
     * @param size 大小
     * @param <T>
     * @return
     */
    public static <T> List<List<T>> split(Collection<T> list, int size) {
        List<List<T>> splitList = Lists.newArrayList();
        if (null == list || list.isEmpty()) {
            return splitList;
        }
        int iCount =0;
        List<T> eachList = Lists.newArrayList();
        for (T data : list) {
            eachList.add(data);
            ++ iCount;
            if (eachList.size() >= size
                    || iCount == list.size()) {
                splitList.add(eachList);
                eachList = Lists.newArrayList();
            }
        }
        return splitList;
    }

    public static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor) {
        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }
}
