package cn.com.xinxin.sass.common;

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
