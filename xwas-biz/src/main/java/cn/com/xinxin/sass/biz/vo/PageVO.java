package cn.com.xinxin.sass.biz.vo;

import cn.com.xinxin.sass.api.base.ToString;

/**
 * @author: yanghaoxian
 * @created: 2020/5/11.
 * @updater:
 * @description:
 */
public class PageVO extends ToString {

    private Integer pageIndex;

    private Integer pageSize;

    private Integer offset;

    private Integer rowNum;

    public Integer getRowNum() {
        return rowNum;
    }

    public void setRowNum(Integer rowNum) {
        this.rowNum = rowNum;
    }

    public Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }
}
