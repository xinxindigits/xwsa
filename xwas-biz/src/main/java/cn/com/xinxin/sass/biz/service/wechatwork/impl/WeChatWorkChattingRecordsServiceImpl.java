package cn.com.xinxin.sass.biz.service.wechatwork.impl;

import cn.com.xinxin.sass.biz.service.wechatwork.WeChatWorkChattingRecordsService;
import cn.com.xinxin.sass.sal.model.wechatwork.WeChatWorkChatDataBO;
import cn.com.xinxin.sass.sal.model.wechatwork.WeChatWorkChattingRecordsReqBO;
import cn.com.xinxin.sass.sal.model.wechatwork.response.WeChatWorkChattingRecordsResponseBO;
import cn.com.xinxin.sass.sal.wechatwork.WeChatWorkInteractionClient;
import com.tencent.wework.ChattingRecordsUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * @author: liuhangzhou
 * @created: 2020/4/26.
 * @updater:
 * @description: 企业微信聊天记录服务
 */
@Service
public class WeChatWorkChattingRecordsServiceImpl implements WeChatWorkChattingRecordsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(WeChatWorkChattingRecordsServiceImpl.class);

    private WeChatWorkInteractionClient weChatWorkInteractionClient;

    public WeChatWorkChattingRecordsServiceImpl(final WeChatWorkInteractionClient weChatWorkInteractionClient) {
        this.weChatWorkInteractionClient = weChatWorkInteractionClient;
    }

    /**
     * 获取聊天记录
     * @param orgId 机构id
     */
    @Override
    public void fetchChattingRecords(String orgId) {
        //TODO 查配置表

        String corporationId = "";
        String secret = "";
        Long startSeq = 0L;

        //初始化sdk
        long sdk = ChattingRecordsUtils.initSdk(corporationId, secret);

        //拉取数据
        WeChatWorkChattingRecordsReqBO reqBO = new WeChatWorkChattingRecordsReqBO();
        reqBO.setLimit(1000L);
        reqBO.setSdk(sdk);
        reqBO.setTimeout(3L);
        reqBO.setStartSeq(startSeq);

        List<WeChatWorkChatDataBO> weChatWorkChatDataBOS;

        do {
            WeChatWorkChattingRecordsResponseBO responseBO = ChattingRecordsUtils.fetchChattingRecords(reqBO);
            weChatWorkInteractionClient.checkResponseStatus(responseBO.getErrorCode(), responseBO.getErrorMessage(),
                    "拉取会话数据失败");
            weChatWorkChatDataBOS = responseBO.getWeChatWorkChatDataBOS();

            if (CollectionUtils.isEmpty(weChatWorkChatDataBOS)) {
                LOGGER.warn("拉取会话数据，记录为空");
                return;
            }

            weChatWorkChatDataBOS.forEach(w -> {

            });



            weChatWorkChatDataBOS.stream().max(Comparator.comparing(WeChatWorkChatDataBO::getSequenceNo))
                    .ifPresent(l -> reqBO.setStartSeq(l.getSequenceNo()));

        } while (1000L == weChatWorkChatDataBOS.size());


    }
}
