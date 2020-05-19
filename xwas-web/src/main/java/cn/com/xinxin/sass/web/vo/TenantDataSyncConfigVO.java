package cn.com.xinxin.sass.web.vo;

import cn.com.xinxin.sass.api.base.ToString;

import java.util.Date;

/**
 * @author: yanghaoxian
 * @created: 2020/5/12.
 * @updater:
 * @description:
 */
public class TenantDataSyncConfigVO extends ToString {

    /**
     * id
     */
    private Long id;
    /**
     * 租户id
     */
    private String tenantId;
    /**
     * cron表达式
     */
    private String cronExpression;
    /**
     * 任务类型
     */
    private String taskType;
    /**
     * 对应企业微信消息序号seq
     */
    private Long fetchedSeqNo;
    /**
     * 会话每次提取上限
     */
    private Integer countCeiling;
    /**
     * 会话每次提取间隔(秒)
     */
    private Integer timeInterval;
    /**
     * 更新时间
     */
    private Date gmtUpdated;
    /**
     * 状态
     */
    private Byte deleted;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public Integer getCountCeiling() {
        return countCeiling;
    }

    public void setCountCeiling(Integer countCeiling) {
        this.countCeiling = countCeiling;
    }

    public Integer getTimeInterval() {
        return timeInterval;
    }

    public void setTimeInterval(Integer timeInterval) {
        this.timeInterval = timeInterval;
    }

    public Long getFetchedSeqNo() {
        return fetchedSeqNo;
    }

    public void setFetchedSeqNo(Long fetchedSeqNo) {
        this.fetchedSeqNo = fetchedSeqNo;
    }

    public Date getGmtUpdated() {
        return gmtUpdated;
    }

    public void setGmtUpdated(Date gmtUpdated) {
        this.gmtUpdated = gmtUpdated;
    }

    public Byte getDeleted() {
        return deleted;
    }

    public void setDeleted(Byte deleted) {
        this.deleted = deleted;
    }
}
