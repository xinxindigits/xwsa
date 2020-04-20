package cn.com.xinxin.sass.sal.wechatwork.impl;

import cn.com.xinxin.sass.sal.model.wechatwork.WeChatWorkDepartmentBO;
import cn.com.xinxin.sass.sal.model.wechatwork.response.WeChatWorkDepartmentQueryResponseBO;
import cn.com.xinxin.sass.sal.wechatwork.WeChatWorkDepartmentClient;
import cn.com.xinxin.sass.sal.wechatwork.WeChatWorkInteractionClient;
import cn.com.xinxin.sass.common.enums.SassBizResultCodeEnum;
import cn.com.xinxin.sass.sal.utils.HttpClientUtils;
import com.alibaba.fastjson.JSON;
import com.xinxinfinance.commons.exception.BusinessException;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @author: liuhangzhou
 * @created: 2020/4/16.
 * @updater:
 * @description: 企业微信部门信息管理Service
 */
@Service
public class WeChatWorkDepartmentClientImpl implements WeChatWorkDepartmentClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(WeChatWorkDepartmentClientImpl.class);

    private final WeChatWorkInteractionClient weChatWorkInteractionClient;

    public WeChatWorkDepartmentClientImpl(final WeChatWorkInteractionClient weChatWorkInteractionClient) {
        this.weChatWorkInteractionClient = weChatWorkInteractionClient;
    }

    /**
     * 向企业微信查询部门列表
     * @param token 企业微信token
     * @return 部门列表
     */
    @Override
    public List<WeChatWorkDepartmentBO> queryDepartmentList(String token) {
        //参数检查
        if (StringUtils.isBlank(token)) {
            LOGGER.error("查询企业微信部门信息，token不能为空");
            throw new BusinessException(SassBizResultCodeEnum.ILLEGAL_PARAMETER, "查询企业微信部门信息，token不能为空");
        }

        //查询部门信息列表
        String response = HttpClientUtils.sendGet(
                "https://qyapi.weixin.qq.com/cgi-bin/department/list?access_token=" + token);

        //将结果转化为BO
        WeChatWorkDepartmentQueryResponseBO responseBO = JSON.parseObject(response, WeChatWorkDepartmentQueryResponseBO.class);

        //检查查询是否成功
        weChatWorkInteractionClient.checkResponseStatus(responseBO.getErrorCode(), responseBO.getErrorMessage(),
                "向企业微信查询部门信息列表失败");

        if (CollectionUtils.isEmpty(responseBO.getWeChatWorkDepartmentBOS())) {
            LOGGER.error("向企业微信查询部门信息列表失败, 部门列表为空");
            throw new BusinessException(SassBizResultCodeEnum.FAIL, "向企业微信查询部门信息列表失败, 部门列表为空");
        }

        return responseBO.getWeChatWorkDepartmentBOS();
    }
}
