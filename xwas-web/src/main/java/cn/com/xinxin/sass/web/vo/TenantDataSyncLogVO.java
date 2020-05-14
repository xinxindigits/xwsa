package cn.com.xinxin.sass.web.vo;

/**
 * @author: liuhangzhou
 * @created: 2020/5/13.
 * @updater:
 * @description:
 */
public class TenantDataSyncLogVO {
    /**
     * 数据库主键
     */
    private Long id;

    /**
     * 任务流水号
     */
    private String taskId;

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
     * 任务时间
     */
    private String taskTime;

    /**
     * 此次同步消息数量
     */
    private Integer messageCount;

    /**
     * 此次同步部门数量
     */
    private Integer departmentCount;

    /**
     * 此次同步成员数量
     */
    private Integer memberCount;

    /**
     * 客户数量
     */
    private Integer customerCount;

    /**
     * 任务状态
     */
    private String taskStatus;

    /**
     * 错误码
     */
    private String errorCode;

    /**
     * 错误原因
     */
    private String errorDesc;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

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

    public String getTaskTime() {
        return taskTime;
    }

    public void setTaskTime(String taskTime) {
        this.taskTime = taskTime;
    }

    public Integer getMessageCount() {
        return messageCount;
    }

    public void setMessageCount(Integer messageCount) {
        this.messageCount = messageCount;
    }

    public Integer getDepartmentCount() {
        return departmentCount;
    }

    public void setDepartmentCount(Integer departmentCount) {
        this.departmentCount = departmentCount;
    }

    public Integer getMemberCount() {
        return memberCount;
    }

    public void setMemberCount(Integer memberCount) {
        this.memberCount = memberCount;
    }

    public Integer getCustomerCount() {
        return customerCount;
    }

    public void setCustomerCount(Integer customerCount) {
        this.customerCount = customerCount;
    }

    public String getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorDesc() {
        return errorDesc;
    }

    public void setErrorDesc(String errorDesc) {
        this.errorDesc = errorDesc;
    }
}
