package cn.com.xinxin.sass.sal.wechatwork.impl;

import cn.com.xinxin.sass.common.enums.SassBizResultCodeEnum;
import cn.com.xinxin.sass.sal.model.wechatwork.WeChatWorkGroupChatBaseBO;
import cn.com.xinxin.sass.sal.model.wechatwork.WeChatWorkGroupChatDetailBO;
import cn.com.xinxin.sass.sal.model.wechatwork.request.WeChatWorkGroupChatRequestBO;
import cn.com.xinxin.sass.sal.model.wechatwork.response.WeChatWorkGroupChatDetailResponseBO;
import cn.com.xinxin.sass.sal.model.wechatwork.response.WeChatWorkGroupChatResponseBO;
import cn.com.xinxin.sass.sal.utils.HttpClientUtils;
import cn.com.xinxin.sass.sal.wechatwork.WeChatWorkInteractionClient;
import cn.com.xinxin.sass.sal.wechatwork.WeChatWorkRoomClient;
import com.alibaba.fastjson.JSONObject;
import com.xinxinfinance.commons.exception.BusinessException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 * @author: liuhangzhou
 * @created: 2020/5/18.
 * @updater:
 * @description: 企业微信群Client
 */
@Service
public class WeChatWorkRoomClientImpl implements WeChatWorkRoomClient {

    private final static Logger LOGGER = LoggerFactory.getLogger(WeChatWorkRoomClientImpl.class);

    private final WeChatWorkInteractionClient weChatWorkInteractionClient;

    public WeChatWorkRoomClientImpl(final WeChatWorkInteractionClient weChatWorkInteractionClient) {
        this.weChatWorkInteractionClient = weChatWorkInteractionClient;
    }

    /**
     * 查询群列表
     * @param weChatWorkGroupChatRequestBO 参数
     * @param token token
     * @return 群列表信息
     */
    @Override
    public List<WeChatWorkGroupChatBaseBO> queryGroupChatList(WeChatWorkGroupChatRequestBO weChatWorkGroupChatRequestBO,
                                                              String token) {

        if (null == weChatWorkGroupChatRequestBO) {
            LOGGER.error("查询群列表, weChatWorkGroupChatRequestBO不能为空");
            throw new BusinessException(SassBizResultCodeEnum.ILLEGAL_PARAMETER,
                    "查询群列表, weChatWorkGroupChatRequestBO不能为空");
        }
        if (StringUtils.isBlank(token)) {
            LOGGER.error("查询群列表, token不能为空");
            throw new BusinessException(SassBizResultCodeEnum.ILLEGAL_PARAMETER,
                    "查询群列表, token不能为空");
        }

        String url = "https://qyapi.weixin.qq.com/cgi-bin/externalcontact/groupchat/list?access_token=" + token;

        String response = null;
        try {
            response = HttpClientUtils.sendPost(url, JSONObject.toJSONString(weChatWorkGroupChatRequestBO));
        } catch (IOException e) {
            LOGGER.error("查询企业微信群列表io异常", e);
            throw new BusinessException(SassBizResultCodeEnum.FAIL, "查询企业微信群列表io异常");
        }

        WeChatWorkGroupChatResponseBO responseBO = JSONObject.parseObject(response, WeChatWorkGroupChatResponseBO.class);

        weChatWorkInteractionClient.checkResponseStatus(responseBO.getErrorCode(), responseBO.getErrorMessage(),
                "查询企业微信群列表异常");

        return responseBO.getWeChatWorkGroupChatBaseBOS();
    }

    /**
     * 查询群聊详情
     * @param token token
     * @param chatId 群id
     * @return 群聊详情
     */
    @Override
    public WeChatWorkGroupChatDetailBO queryGroupChatDetail(String token, String chatId) {

        if (StringUtils.isBlank(token)) {
            LOGGER.error("查询群聊详情, token不能为空");
            throw new BusinessException(SassBizResultCodeEnum.ILLEGAL_PARAMETER,
                    "查询群聊详情, token不能为空");
        }
        if (StringUtils.isBlank(chatId)) {
            LOGGER.error("查询群聊详情, chatId不能为空");
            throw new BusinessException(SassBizResultCodeEnum.ILLEGAL_PARAMETER,
                    "查询群聊详情, chatId不能为空");
        }

        String url = "https://qyapi.weixin.qq.com/cgi-bin/externalcontact/groupchat/get?access_token=" + token;

        String param = "{\"chat_id\":\"" + chatId + "\"}";

        String response = null;
        try {
            response = HttpClientUtils.sendPost(url, param);
        } catch (IOException e) {
            LOGGER.error("查询企业微信群聊详情io异常", e);
            throw new BusinessException(SassBizResultCodeEnum.FAIL, "查询企业微信群聊详情io异常");
        }

        WeChatWorkGroupChatDetailResponseBO responseBO = JSONObject.parseObject(response,
                WeChatWorkGroupChatDetailResponseBO.class);

        weChatWorkInteractionClient.checkResponseStatus(responseBO.getErrorCode(), responseBO.getErrorMessage(),
                "查询企业微信群详情异常");

        return responseBO.getWeChatWorkGroupChatDetailBO();
    }
}
