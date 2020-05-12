package cn.com.xinxin.sass.biz.schedule.service.impl;

import cn.com.xinxin.sass.biz.SpringContextHolder;
import cn.com.xinxin.sass.biz.schedule.CommonJob;
import cn.com.xinxin.sass.biz.schedule.service.QuartzJobService;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author: liuhangzhou
 * @created: 2020/5/12.
 * @updater:
 * @description:
 */
@Service
public class QuartzJobServiceImpl implements QuartzJobService {

    private static final Logger LOGGER = LoggerFactory.getLogger(QuartzJobServiceImpl.class);

    @Override
    public void startJob(String tenantId, String taskType, String cronExpression) {
        SchedulerFactoryBean schedulerFactory = (SchedulerFactoryBean) SpringContextHolder.getBean(
                SchedulerFactoryBean.class);

        if (null == schedulerFactory) {
            LOGGER.error("无法找到Spring容器中的SchedulerFactoryBean实例");
            return;
        }
        Scheduler scheduler = schedulerFactory.getScheduler();
        if (null == scheduler) {
            LOGGER.error("无法找到SchedulerFactoryBean实例中的调度器对象");
            return;
        }

        String jobName = tenantId + "_" + taskType;

        TriggerKey triggerKey = TriggerKey.triggerKey(jobName, "1");
        Trigger originTrigger;
        try {
            originTrigger = scheduler.getTrigger(triggerKey);
        } catch (SchedulerException e) {
            LOGGER.info("Quartz job initialization -- failed to start scheduler, jobname[{}]", jobName);
            return;
        }

        long time = System.currentTimeMillis();
        Date startTime = new Date(time);

        Trigger trigger = TriggerBuilder.newTrigger()
                .withDescription(jobName)
                .withIdentity(jobName, "1")
                .startAt(startTime)
                .withSchedule(CronScheduleBuilder.cronSchedule(cronExpression))
                .build();
        try {
            if (null == originTrigger) {
                JobDetail job = JobBuilder.newJob(CommonJob.class)
                        .withDescription(jobName)
                        .withIdentity(jobName, "1")
                        .build();
                scheduler.scheduleJob(job, trigger);

            } else {
                scheduler.rescheduleJob(triggerKey, trigger);
            }

            if(!scheduler.isStarted()){
                scheduler.start();
            }
        } catch (SchedulerException e) {
            LOGGER.error("Quartz job initialization -- failed to start scheduler", e);
        }
    }
}
