package cn.com.xinxin.sass.sal.wechatwork;

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

import cn.com.xinxin.sass.sal.model.wechatwork.WeChatWorkCustomerBO;

import java.util.List;

/**
 * @author: liuhangzhou
 * @created: 2020/4/20.
 * @updater:
 * @description: 企业微信客户信息管理Service
 */
public interface WeChatWorkCustomerClient {

    /**
     * 根据成员的userID查询客户的userID
     * @param token 企业微信token
     * @param userId 成员的userID
     * @return 客户的userID
     */
    List<String> queryCustomerUserId(String token, String userId);

    /**
     * 根据客户的userID查询客户的详细信息
     * @param token 企业微信token
     * @param customerUserId 客户的userID
     * @return 客户的详细信息
     */
    WeChatWorkCustomerBO queryCustomerDetail(String token, String customerUserId);

    /**
     * 根据成员的userID查询客户的userID
     * @param token 企业微信token
     * @param userIdS 成员的userID列表
     * @return 客户的userID
     */
    List<String> queryCustomerUserId(String token, List<String> userIdS);

    /**
     * 根据客户的userID查询客户的详细信息
     * @param token 企业微信token
     * @param customerUserIdS 客户的userID
     * @return 客户的详细信息
     */
    List<WeChatWorkCustomerBO> queryCustomerDetail(String token, List<String> customerUserIdS);
}
