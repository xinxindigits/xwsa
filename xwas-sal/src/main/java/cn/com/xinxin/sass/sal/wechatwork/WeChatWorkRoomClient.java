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

import cn.com.xinxin.sass.sal.model.wechatwork.WeChatWorkGroupChatBaseBO;
import cn.com.xinxin.sass.sal.model.wechatwork.WeChatWorkGroupChatDetailBO;
import cn.com.xinxin.sass.sal.model.wechatwork.request.WeChatWorkGroupChatRequestBO;

import java.util.List;

/**
 * @author: liuhangzhou
 * @created: 2020/5/14.
 * @updater:
 * @description: 企业微信群Client
 */
public interface WeChatWorkRoomClient {

    /**
     * 查询群列表
     * @param weChatWorkGroupChatRequestBO 参数
     * @param token token
     * @return 群列表信息
     */
    List<WeChatWorkGroupChatBaseBO> queryGroupChatList(WeChatWorkGroupChatRequestBO weChatWorkGroupChatRequestBO,
                                                       String token);

    /**
     * 查询群聊详情
     * @param token token
     * @param chatId 群id
     * @return 群聊详情
     */
    WeChatWorkGroupChatDetailBO queryGroupChatDetail(String token, String chatId);
}
