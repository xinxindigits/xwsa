package cn.com.xinxin.sass.job.executor;

import cn.com.xinxin.sass.biz.service.wechatwork.WeChatWorkSyncService;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author: liuhangzhou
 * @created: 2020/5/7.
 * @updater:
 * @description: 企业微信通讯录同步job
 */
@JobHandler(value = "weChatWorkAddressListJobHandler")
@Component
public class WeChatWorkAddressListJobHandler extends IJobHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(WeChatWorkAddressListJobHandler.class);

    private final WeChatWorkSyncService weChatWorkAddressListSyncServiceImpl;

    public WeChatWorkAddressListJobHandler(final WeChatWorkSyncService weChatWorkAddressListSyncServiceImpl) {
        this.weChatWorkAddressListSyncServiceImpl = weChatWorkAddressListSyncServiceImpl;
    }

    @Override
    public ReturnT<String> execute(String s) throws Exception {
        if (StringUtils.isBlank(s)) {
            LOGGER.error("企业微信通讯录同步job,参数不能为空");
            return ReturnT.FAIL;
        }

        LOGGER.info("租户[{}]同步通讯录任务开始", s);

        weChatWorkAddressListSyncServiceImpl.sync(s);

        LOGGER.info("租户[{}]同步通讯录任务结束", s);

        return ReturnT.SUCCESS;
    }
}
