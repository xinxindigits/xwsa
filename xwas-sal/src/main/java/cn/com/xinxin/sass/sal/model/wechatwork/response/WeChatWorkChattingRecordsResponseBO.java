package cn.com.xinxin.sass.sal.model.wechatwork.response;

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

import cn.com.xinxin.sass.sal.model.wechatwork.WeChatWorkChatDataBO;
import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

/**
 * @author: liuhangzhou
 * @created: 2020/4/26.
 * @updater:
 * @description: 企业微信聊天记录响应值
 */
public class WeChatWorkChattingRecordsResponseBO extends WeChatWorkResponseBaseBO {
    /**
     * 企业微信聊天记录内容
     */
    @JSONField(name = "chatdata")
    private List<WeChatWorkChatDataBO> weChatWorkChatDataBOS;

    public List<WeChatWorkChatDataBO> getWeChatWorkChatDataBOS() {
        return weChatWorkChatDataBOS;
    }

    public void setWeChatWorkChatDataBOS(List<WeChatWorkChatDataBO> weChatWorkChatDataBOS) {
        this.weChatWorkChatDataBOS = weChatWorkChatDataBOS;
    }

    @Override
    public String toString() {
        return "WeChatWorkChattingRecordsResponseBO{" +
                "weChatWorkChatDataBOS=" + weChatWorkChatDataBOS +
                '}';
    }
}
