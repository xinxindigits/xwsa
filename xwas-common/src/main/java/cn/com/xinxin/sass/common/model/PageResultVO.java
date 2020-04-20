package cn.com.xinxin.sass.common.model;

import cn.com.xinxin.sass.api.base.ToString;

import java.util.List;

/**
 * @author: zhouyang
 * @created: 20/04/2020.
 * @updater:
 * @description:
 */
public class PageResultVO<T> extends ToString{


    private Integer pageSize;

    private Integer pageNumber;

    private Long total;

    private List<T> items;

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }
}
