package cn.com.xinxin.sass.biz.schedule.service.impl;

/*
 *
 * Copyright 2020 www.xinxindigits.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software
 * and associated documentation files (the "Software"),to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice
 * shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 * Redistribution and selling copies of the software are prohibited, only if the authorization from xinxin digits
 * was obtained.Neither the name of the xinxin digits; nor the names of its contributors may be used to
 * endorse or promote products derived from this software without specific prior written permission.
 *
 */

import cn.com.xinxin.sass.biz.SpringContextHolder;
import cn.com.xinxin.sass.biz.schedule.CommonJob;
import cn.com.xinxin.sass.biz.schedule.service.QuartzJobService;
import cn.com.xinxin.sass.biz.tenant.TenantIdContext;
import cn.com.xinxin.sass.common.enums.SassBizResultCodeEnum;
import com.xinxinfinance.commons.exception.BusinessException;
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
 * @description: 定时任务服务
 */
@Service
public class QuartzJobServiceImpl implements QuartzJobService {

    private static final Logger LOGGER = LoggerFactory.getLogger(QuartzJobServiceImpl.class);

    @Override
    public void startJob(String tenantId, String taskType, String cronExpression) {
        SchedulerFactoryBean schedulerFactory = (SchedulerFactoryBean) SpringContextHolder.getBean(
                SchedulerFactoryBean.class);

        TenantIdContext.set(tenantId);

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
            TenantIdContext.remove();
        } catch (SchedulerException e) {
            TenantIdContext.remove();
            LOGGER.error("Quartz job initialization -- failed to start scheduler", e);
            throw new BusinessException(SassBizResultCodeEnum.FAIL, "任务[{}]初始化失败", jobName);
        }
    }

    @Override
    public void stopJob(String tenantId, String taskType) {
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

        JobKey jobKey = JobKey.jobKey(jobName, "1");

        try {
            if (null != jobKey) {
                scheduler.deleteJob(jobKey);
            }
        } catch (SchedulerException e) {
            LOGGER.info("删除任务失败, jobname[{}]", jobName);
            throw new BusinessException(SassBizResultCodeEnum.FAIL, "删除任务失败");
        }
    }
}
