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

    public final static Integer DEFAULT_PAGE_SIZE = 20;

    public final static Integer DEFAULT_PAGE_NUM = 1;

    private Integer pageSize;

    private Integer pageNumber;

    private Long total;

    private List<T> items;

    public Integer getPageSize() {
        if(pageSize != null) {
            return pageSize;
        }else {
            return DEFAULT_PAGE_SIZE;
        }
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageNumber() {
        if(pageNumber != null){
            return pageNumber;
        }else{
            return DEFAULT_PAGE_NUM;
        }
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
