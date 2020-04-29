package cn.com.xinxin.sass.web.form;

import cn.com.xinxin.sass.api.base.ToString;

import java.util.List;

/**
 * @author: liuhangzhou
 * @created: 2020/4/28.
 * @updater:
 * @description: 企业微信客户查询表单
 */
public class WeChatCustomerQueryForm extends ToString {


    private static final long serialVersionUID = 8064363806982333584L;
    /**
     * 机构id
     */
    private String orgId;

    /**
     * 成员userId
     */
    private List<String> memberUserIds;

    /**
     * 客户的创建时间应大于startTime
     */
    private String startTime;

    /**
     * 客户的创建时间应小于endTime
     */
    private String endTime;

    /**
     * 页码
     */
    private Integer pageIndex;

    /**
     * 页大小
     */
    private Integer pageSize;

    /**
     * 客户名称
     */
    private String customerName;


    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public List<String> getMemberUserIds() {
        return memberUserIds;
    }

    public void setMemberUserIds(List<String> memberUserIds) {
        this.memberUserIds = memberUserIds;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
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

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    @Override
    public String toString() {
        return "WeChatCustomerQueryForm{" +
                "orgId='" + orgId + '\'' +
                ", memberUserIds=" + memberUserIds +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", pageNum=" + pageIndex +
                ", pageSize=" + pageSize +
                ", customerName='" + customerName + '\'' +
                '}';
    }
}
