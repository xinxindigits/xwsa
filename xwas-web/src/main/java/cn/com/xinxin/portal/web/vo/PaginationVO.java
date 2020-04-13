package cn.com.xinxin.portal.web.vo;

/**
 * Created by dengyunhui on 2018/5/8
 **/
public class PaginationVO {
    private Integer start;
    private Integer limit;
    private Integer pageIndex;

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }
}
