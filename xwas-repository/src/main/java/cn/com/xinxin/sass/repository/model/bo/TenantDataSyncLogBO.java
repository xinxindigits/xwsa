package cn.com.xinxin.sass.repository.model.bo;

/**
 * @author: liuhangzhou
 * @created: 2020/5/13.
 * @updater:
 * @description: 租户数据同步任务日志BO
 */
public class TenantDataSyncLogBO {
    /**
     * 租户id
     */
    private String tenantId;

    /**
     * 任务类型
     */
    private String taskType;

    /**
     * 任务日期
     */
    private String taskDate;

    /**
     * 任务状态
     */
    private String taskStatus;

    /**
     * 任务起始时间
     */
    private String startTime;

    /**
     * 任务结束时间
     */
    private String endTime;

    /**
     * 分页起始
     */
    private Long index;

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

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public String getTaskDate() {
        return taskDate;
    }

    public void setTaskDate(String taskDate) {
        this.taskDate = taskDate;
    }

    public String getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
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

    public Long getIndex() {
        return index;
    }

    public void setIndex(Long index) {
        this.index = index;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
