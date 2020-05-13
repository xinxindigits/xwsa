package cn.com.xinxin.sass.web.form;

import cn.com.xinxin.sass.api.base.ToString;

/**
 * @author: liuhangzhou
 * @created: 2020/5/12.
 * @updater:
 * @description: 租户配置表单
 */
public class TenantConfigForm extends ToString {

    private static final long serialVersionUID = -8091845240027712989L;

    /**
     * 数据库主键
     */
    private Long id;

    /**
     * 任务类型
     */
    private String taskType;

    /**
     * 任务执行时间
     */
    private String cronExpression;

    /**
     * 每次获取会话记录条数
     */
    private Integer countCeiling;

    /**
     * 会话每次提取间隔(秒)
     */
    private Integer timeInterval;

    /**
     * 任务状态
     */
    private Integer status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
