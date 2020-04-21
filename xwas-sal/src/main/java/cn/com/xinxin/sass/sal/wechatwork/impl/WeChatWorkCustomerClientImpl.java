package cn.com.xinxin.sass.sal.wechatwork.impl;

import cn.com.xinxin.sass.sal.model.wechatwork.WeChatWorkCustomerBO;
import cn.com.xinxin.sass.sal.model.wechatwork.response.WeChatWorkCustomerDetailQueryResponseBO;
import cn.com.xinxin.sass.sal.model.wechatwork.response.WeChatWorkCustomerUserIdQueryResponseBO;
import cn.com.xinxin.sass.sal.wechatwork.WeChatWorkCustomerClient;
import cn.com.xinxin.sass.sal.wechatwork.WeChatWorkInteractionClient;
import cn.com.xinxin.sass.common.enums.SassBizResultCodeEnum;
import cn.com.xinxin.sass.sal.utils.HttpClientUtils;
import com.alibaba.fastjson.JSON;
import com.xinxinfinance.commons.exception.BusinessException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: liuhangzhou
 * @created: 2020/4/20.
 * @updater:
 * @description: 企业微信客户信息管理Service
 */
@Service
public class WeChatWorkCustomerClientImpl implements WeChatWorkCustomerClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(WeChatWorkCustomerClientImpl.class);

    private final WeChatWorkInteractionClient weChatWorkInteractionClient;

    public WeChatWorkCustomerClientImpl(final WeChatWorkInteractionClient weChatWorkInteractionClient) {
        this.weChatWorkInteractionClient = weChatWorkInteractionClient;
    }

    /**
     * 根据成员的userID查询客户的userID
     * @param token 企业微信token
     * @param userId 成员的userID
     * @return 客户的userID
     */
    @Override
    public List<String> queryCustomerUserId(String token, String userId) {
        //参数检查
        if (StringUtils.isBlank(token)) {
            LOGGER.error("查询企业微信客户userId，token不能为空");
            throw new BusinessException(SassBizResultCodeEnum.ILLEGAL_PARAMETER, "查询企业微信客户userId，token不能为空");
        }
        if (null == userId) {
            LOGGER.error("查询企业微信客户userId，userId不能为空");
            throw new BusinessException(SassBizResultCodeEnum.ILLEGAL_PARAMETER, "查询企业微信客户userId，userId不能为空");
        }

        //查询客户userId列表
        String response = HttpClientUtils.sendGet(
                "https://qyapi.weixin.qq.com/cgi-bin/externalcontact/list?access_token=" + token + "&userid=" + userId);

        //将结果转化为BO
        WeChatWorkCustomerUserIdQueryResponseBO responseBO = JSON.parseObject(response,
                WeChatWorkCustomerUserIdQueryResponseBO.class);

        //检查查询是否成功
        weChatWorkInteractionClient.checkResponseStatus(responseBO.getErrorCode(), responseBO.getErrorMessage(),
                "向企业微信查询客户userId列表失败");

        return responseBO.getCustomerUserIdS();
    }

    /**
     * 根据客户的userID查询客户的详细信息
     * @param token 企业微信token
     * @param customerUserId 客户的userID
     * @return 客户的详细信息
     */
    @Override
    public WeChatWorkCustomerBO queryCustomerDetail(String token, String customerUserId) {
        //参数检查
        if (StringUtils.isBlank(token)) {
            LOGGER.error("查询企业微信客户详细信息，token不能为空");
            throw new BusinessException(SassBizResultCodeEnum.ILLEGAL_PARAMETER, "查询企业微信客户详细信息，token不能为空");
        }
        if (null == customerUserId) {
            LOGGER.error("查询企业微信客户详细信息，customerUserId不能为空");
            throw new BusinessException(SassBizResultCodeEnum.ILLEGAL_PARAMETER, "查询企业微信客户详细信息，customerUserId不能为空");
        }

        //查询客户的详细信息
        String response = HttpClientUtils.sendGet(
                "https://qyapi.weixin.qq.com/cgi-bin/externalcontact/get?access_token=" + token
                        + "&external_userid=" + customerUserId);

        //将结果转化为BO
        WeChatWorkCustomerDetailQueryResponseBO responseBO = JSON.parseObject(response,
                WeChatWorkCustomerDetailQueryResponseBO.class);

        //检查查询是否成功
        weChatWorkInteractionClient.checkResponseStatus(responseBO.getErrorCode(), responseBO.getErrorMessage(),
                "向企业微信查询客户详细信息失败");

        return responseBO.getWeChatWorkCustomerBO();
    }
}
