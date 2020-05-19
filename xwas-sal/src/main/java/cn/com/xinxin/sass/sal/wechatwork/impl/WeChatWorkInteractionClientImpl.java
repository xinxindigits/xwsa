package cn.com.xinxin.sass.sal.wechatwork.impl;

import cn.com.xinxin.sass.sal.model.wechatwork.response.WeChatWorkTokenResponseBO;
import cn.com.xinxin.sass.sal.wechatwork.WeChatWorkInteractionClient;
import cn.com.xinxin.sass.common.enums.SassBizResultCodeEnum;
import cn.com.xinxin.sass.sal.utils.HttpClientUtils;
import com.alibaba.fastjson.JSON;
import com.xinxinfinance.commons.exception.BusinessException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author: liuhangzhou
 * @created: 2020/4/20.
 * @updater:
 * @description: 企业微信交互Service
 */
@Service
public class WeChatWorkInteractionClientImpl implements WeChatWorkInteractionClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(WeChatWorkInteractionClientImpl.class);

    /**
     * 获取企业微信token
     * @param corporationId 企业id
     * @param secret 应用secret
     * @return token
     */
    @Override
    public String fetchToken(String corporationId, String secret) {
        String url = "https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=" + corporationId + "&corpsecret=" + secret;

        WeChatWorkTokenResponseBO weChatWorkTokenResponseBO = JSON.parseObject(HttpClientUtils.sendGet(url),
                WeChatWorkTokenResponseBO.class);

        checkResponseStatus(weChatWorkTokenResponseBO.getErrorCode(), weChatWorkTokenResponseBO.getErrorMessage(),
                "向企业微信获取token失败");

        if (StringUtils.isBlank(weChatWorkTokenResponseBO.getToken())) {
            LOGGER.error("向企业微信获取token失败，token为空");
            throw new BusinessException(SassBizResultCodeEnum.FAIL, "向企业微信获取token失败，token为空");
        }

        return weChatWorkTokenResponseBO.getToken();
    }

    /**
     * 检查响应状态
     * @param errorCode 企业微信返回的返回码
     * @param errorMessage 对返回码的文本描述内容
     * @param errorDescription 如果响应不成功应该输出的日志信息
     */
    @Override
    public void checkResponseStatus(Integer errorCode, String errorMessage, String errorDescription) {
        if (null == errorCode || 0L != errorCode) {
            LOGGER.error("向企业微信发起请求，错误码[{}]", errorMessage);
            throw new BusinessException(SassBizResultCodeEnum.FAIL, errorDescription);
        }
    }
}
