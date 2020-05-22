package cn.com.xinxin.sass.sal.wechatwork.impl;

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

import cn.com.xinxin.sass.sal.model.wechatwork.WeChatWorkUserBO;
import cn.com.xinxin.sass.sal.model.wechatwork.response.WeChatWorkUserQueryResponseBO;
import cn.com.xinxin.sass.sal.wechatwork.WeChatWorkInteractionClient;
import cn.com.xinxin.sass.sal.wechatwork.WeChatWorkUserClient;
import cn.com.xinxin.sass.common.enums.SassBizResultCodeEnum;
import cn.com.xinxin.sass.sal.utils.HttpClientUtils;
import com.alibaba.fastjson.JSON;
import com.xinxinfinance.commons.exception.BusinessException;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: liuhangzhou
 * @created: 2020/4/20.
 * @updater:
 * @description: 企业微信成员信息管理Service
 */
@Service
public class WeChatWorkUserClientImpl implements WeChatWorkUserClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(WeChatWorkUserClientImpl.class);

    private final WeChatWorkInteractionClient weChatWorkInteractionClient;

    public WeChatWorkUserClientImpl(final WeChatWorkInteractionClient weChatWorkInteractionClient) {
        this.weChatWorkInteractionClient = weChatWorkInteractionClient;
    }

    /**
     * 查询用户信息列表
     * @param token 企业微信token
     * @param departmentId 部门id
     * @return 用户信息列表
     */
    @Override
    public List<WeChatWorkUserBO> queryUserList(String token, Long departmentId) {
        //参数检查
        if (StringUtils.isBlank(token)) {
            LOGGER.error("查询企业微信成员信息，token不能为空.");
            throw new BusinessException(SassBizResultCodeEnum.ILLEGAL_PARAMETER, "查询企业微信成员信息，token不能为空.");
        }
        if (null == departmentId) {
            LOGGER.error("查询企业微信成员信息，departmentId不能为空");
            throw new BusinessException(SassBizResultCodeEnum.ILLEGAL_PARAMETER, "查询企业微信成员信息，departmentId不能为空");
        }

        //查询成员信息列表
        String response = HttpClientUtils.sendGet(
                "https://qyapi.weixin.qq.com/cgi-bin/user/list?access_token=" + token + "&department_id=" + departmentId);

        //将结果转化为BO
        WeChatWorkUserQueryResponseBO responseBO = JSON.parseObject(response, WeChatWorkUserQueryResponseBO.class);

        //检查查询是否成功
        weChatWorkInteractionClient.checkResponseStatus(responseBO.getErrorCode(), responseBO.getErrorMessage(),
                "向企业微信查询成员信息列表失败");

        return responseBO.getWeChatWorkUserBOS();
    }

    /**
     * 查询用户信息列表
     * @param token 企业微信token
     * @param departmentIdS 部门id列表
     * @return 用户信息列表
     */
    @Override
    public List<WeChatWorkUserBO> queryUserList(String token, List<Long> departmentIdS) {
        //参数检查
        if (StringUtils.isBlank(token)) {
            LOGGER.error("查询企业微信成员信息，token不能为空");
            throw new BusinessException(SassBizResultCodeEnum.ILLEGAL_PARAMETER, "查询企业微信成员信息，token不能为空");
        }
        if (CollectionUtils.isEmpty(departmentIdS)) {
            LOGGER.error("查询企业微信成员信息，departmentIdS不能为空");
            throw new BusinessException(SassBizResultCodeEnum.ILLEGAL_PARAMETER, "查询企业微信成员信息，departmentIdS不能为空");
        }

        //查询成员信息列表
        List<String> urls = new ArrayList<>();
        departmentIdS.forEach(d -> urls.add("https://qyapi.weixin.qq.com/cgi-bin/user/list?access_token=" + token +
                "&department_id=" + d));
        List<String> responseList = HttpClientUtils.sendGetList(urls);


        List<WeChatWorkUserBO> weChatWorkUserBOS = new ArrayList<>();
        responseList.forEach(r -> {
            //将结果转化为BO
            WeChatWorkUserQueryResponseBO responseBO = JSON.parseObject(r, WeChatWorkUserQueryResponseBO.class);
            //检查查询是否成功
            weChatWorkInteractionClient.checkResponseStatus(responseBO.getErrorCode(), responseBO.getErrorMessage(),
                    "向企业微信查询成员信息列表失败");
            weChatWorkUserBOS.addAll(responseBO.getWeChatWorkUserBOS());
        });

        return weChatWorkUserBOS;
    }
}
