package cn.com.xinxin.sass.job.executor;

import cn.com.xinxin.sass.biz.service.wechatwork.WeChatWorkSyncService;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import com.xxl.job.core.log.XxlJobLogger;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * @author: liuhangzhou
 * @created: 2020/5/7.
 * @updater:
 * @description: 企业微信聊天记录同步job
 */
@JobHandler(value = "weChatWorkChattingRecordJobHandler")
@Component
public class WeChatWorkChattingRecordJobHandler extends IJobHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(WeChatWorkChattingRecordJobHandler.class);

    private final WeChatWorkSyncService weChatWorkChatRecordSyncServiceImpl;

    public WeChatWorkChattingRecordJobHandler(final @Qualifier(value = "weChatWorkChatRecordSyncServiceImpl")
                                                      WeChatWorkSyncService weChatWorkChatRecordSyncServiceImpl) {
        this.weChatWorkChatRecordSyncServiceImpl = weChatWorkChatRecordSyncServiceImpl;
    }

    @Override
    public ReturnT<String> execute(String s) throws Exception {
        if (StringUtils.isBlank(s)) {
            LOGGER.error("企业微信会话记录同步job,参数不能为空");
            XxlJobLogger.log("企业微信会话记录同步job,参数不能为空");
            return ReturnT.FAIL;
        }

        LOGGER.info("租户[{}]同步会话记录任务开始", s);
        XxlJobLogger.log("租户同步会话记录开始");

        weChatWorkChatRecordSyncServiceImpl.sync(s);

        LOGGER.info("租户[{}]同步会话记录任务结束", s);
        XxlJobLogger.log("租户同步会话记录任务结束");

        return ReturnT.SUCCESS;
    }
}
