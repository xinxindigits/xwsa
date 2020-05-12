package cn.com.xinxin.sass.biz.schedule.service;

/**
 * @author: liuhangzhou
 * @created: 2020/5/12.
 * @updater:
 * @description:
 */
public interface QuartzJobService {
    void startJob(String tenantId, String taskType, String cronExpression);
}
