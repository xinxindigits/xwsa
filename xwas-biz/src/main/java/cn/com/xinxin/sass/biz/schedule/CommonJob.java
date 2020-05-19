package cn.com.xinxin.sass.biz.schedule;

import cn.com.xinxin.sass.biz.SpringContextHolder;
import cn.com.xinxin.sass.biz.service.wechatwork.WeChatWorkSyncService;
import cn.com.xinxin.sass.biz.service.wechatwork.impl.WeChatWorkAddressListSyncServiceImpl;
import cn.com.xinxin.sass.biz.service.wechatwork.impl.WeChatWorkChatRecordSyncServiceImpl;
import cn.com.xinxin.sass.common.enums.TaskTypeEnum;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author: liuhangzhou
 * @created: 2020/5/12.
 * @updater:
 * @description: 定时job类
 */
public class CommonJob implements Job {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommonJob.class);

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        LOGGER.info("Project被触发");
        String jobName = context.getJobDetail().getKey().getName();

        LOGGER.info("Project {} 被触发", jobName);

        try {
            if (jobName.contains(TaskTypeEnum.CONTACT_SYNC.getType()))  {
                WeChatWorkSyncService weChatWorkSyncService = SpringContextHolder.getBean(
                        WeChatWorkAddressListSyncServiceImpl.class);
                weChatWorkSyncService.sync(jobName.replace("_" + TaskTypeEnum.CONTACT_SYNC.getType(), ""));
            } else if (jobName.contains(TaskTypeEnum.MESSAGE_SYNC.getType())) {
                WeChatWorkSyncService weChatWorkSyncService = SpringContextHolder.getBean(
                        WeChatWorkChatRecordSyncServiceImpl.class);
                weChatWorkSyncService.sync(jobName.replace("_" + TaskTypeEnum.MESSAGE_SYNC.getType(), ""));
            }
        } catch (Exception e) {
            LOGGER.error("任务执行失败", e);
        }
    }
}
