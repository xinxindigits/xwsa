package cn.com.xinxin.sass.biz.schedule;

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

import cn.com.xinxin.sass.biz.schedule.service.impl.QuartzJobServiceImpl;

import cn.com.xinxin.sass.biz.service.impl.TenantDataSyncConfigServiceImpl;
import cn.com.xinxin.sass.biz.tenant.TenantIdContext;
import cn.com.xinxin.sass.repository.model.TenantDataSyncConfigDO;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.quartz.*;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;


import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.List;

/**
 * @author: liuhangzhou
 * @created: 2020/5/12.
 * @updater:
 * @description: 定时任务初始化类
 */
public class QuartzJobInitialization {
    private static final Logger LOGGER = LoggerFactory.getLogger(QuartzJobInitialization.class);

    @PostConstruct
    public void quartzJobInitialization() throws ClassNotFoundException {
        LOGGER.info("*****************************QuartzJobInitialization Started************************");

        List<TenantDataSyncConfigDO> tenantDataSyncConfigDOS = SpringContextHolder.getBean(
                TenantDataSyncConfigServiceImpl.class).queryValidRecord();

        if (CollectionUtils.isNotEmpty(tenantDataSyncConfigDOS)) {
            for (TenantDataSyncConfigDO tenantDataSyncConfigDO : tenantDataSyncConfigDOS) {
                try {
                    // 启动任务之前设置租户ID
                    TenantIdContext.set(tenantDataSyncConfigDO.getTenantId());
                    SpringContextHolder.getBean(QuartzJobServiceImpl.class)
                            .startJob(tenantDataSyncConfigDO.getTenantId(), tenantDataSyncConfigDO.getTaskType(),
                            tenantDataSyncConfigDO.getCronExpression());
                    // 完成任务移除租户ID
                    TenantIdContext.remove();
                } catch (Exception e) {
                    // 异常任务移除租户ID
                    TenantIdContext.remove();
                    LOGGER.info("Quartz job initialization -- failed to start scheduler, tenantId[{}], taskType[{}]",
                            tenantDataSyncConfigDO.getTenantId(), tenantDataSyncConfigDO.getTaskType());
                }

            }
        }
    }

    @PreDestroy
    public void destroyQuartzJobInitialization(){

        LOGGER.info("*****************************QuartzJobInitialization Stoped************************");

        SchedulerFactoryBean schedulerFactory = (SchedulerFactoryBean) SpringContextHolder.getBean(SchedulerFactoryBean.class);
        if (null == schedulerFactory) {
            LOGGER.error("无法找到Spring容器中的SchedulerFactoryBean实例");
            return;
        }

        try {
            Scheduler scheduler = schedulerFactory.getScheduler();
            if (!scheduler.isShutdown()) {
                scheduler.shutdown();
            }
        } catch (Exception e) {
            LOGGER.error("Failed to shutdown Quartz scheduler", e);
        }
    }
}
