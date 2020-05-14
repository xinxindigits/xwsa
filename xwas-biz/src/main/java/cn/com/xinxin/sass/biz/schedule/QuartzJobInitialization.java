package cn.com.xinxin.sass.biz.schedule;

import cn.com.xinxin.sass.biz.SpringContextHolder;
import cn.com.xinxin.sass.biz.schedule.service.QuartzJobService;
import cn.com.xinxin.sass.biz.service.TenantDataSyncConfigService;
import cn.com.xinxin.sass.repository.model.TenantDataSyncConfigDO;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.quartz.*;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.List;

/**
 * @author: liuhangzhou
 * @created: 2020/5/12.
 * @updater:
 * @description: 定时任务初始化类
 */
@Service
public class QuartzJobInitialization {
    private static final Logger LOGGER = LoggerFactory.getLogger(QuartzJobInitialization.class);

    private final TenantDataSyncConfigService tenantDataSyncConfigService;
    private final QuartzJobService quartzJobService;

    public QuartzJobInitialization(final TenantDataSyncConfigService tenantDataSyncConfigService,
                                   final QuartzJobService quartzJobService) {
        this.tenantDataSyncConfigService = tenantDataSyncConfigService;
        this.quartzJobService = quartzJobService;
    }

    @PostConstruct
    public void quartzJobInitialization() throws ClassNotFoundException {
        LOGGER.info("*****************************QuartzJobInitialization Started************************");

        List<TenantDataSyncConfigDO> tenantDataSyncConfigDOS = tenantDataSyncConfigService.queryValidRecord();

        if (CollectionUtils.isNotEmpty(tenantDataSyncConfigDOS)) {
            for (TenantDataSyncConfigDO tenantDataSyncConfigDO : tenantDataSyncConfigDOS) {
                try {
                    quartzJobService.startJob(tenantDataSyncConfigDO.getTenantId(), tenantDataSyncConfigDO.getTaskType(),
                            tenantDataSyncConfigDO.getCronExpression());
                } catch (Exception e) {
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
