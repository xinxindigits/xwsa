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
import cn.com.xinxin.sass.biz.service.wechatwork.WeChatWorkSyncService;
import cn.com.xinxin.sass.biz.service.wechatwork.impl.WeChatWorkAddressListSyncServiceImpl;
import cn.com.xinxin.sass.biz.service.wechatwork.impl.WeChatWorkChatRecordSyncServiceImpl;
import cn.com.xinxin.sass.common.enums.TaskTypeEnum;
import cn.com.xinxin.sass.tenant.TenantIdContext;
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

        String tenantId = jobName.split("_")[0];

        TenantIdContext.set(tenantId);

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
            TenantIdContext.remove();
        } catch (Exception e) {
            TenantIdContext.remove();
            LOGGER.error("任务执行失败", e);
        }
    }
}
