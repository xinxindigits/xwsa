package cn.com.xinxin.sass.web.form;

import cn.com.xinxin.sass.api.base.ToString;

/**
 * @author: liuhangzhou
 * @created: 2020/4/29.
 * @updater:
 * @description: 企业微信会话消息查询参数
 */
public class WeChatMessageQueryForm extends ToString {

    private static final long serialVersionUID = -5377565182974958749L;
    /**
     * 机构id
     */
    private String tenantId;

    /**
     * 成员userid或客户userid
     */
    private String userId;
    /**
     * 成员userid或客户userid
     */
    private String userIdTwo;
    /**
     * 会话id
     */
    private String roomId;

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

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public String getUserIdTwo() {
        return userIdTwo;
    }

    public void setUserIdTwo(String userIdTwo) {
        this.userIdTwo = userIdTwo;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }
}
