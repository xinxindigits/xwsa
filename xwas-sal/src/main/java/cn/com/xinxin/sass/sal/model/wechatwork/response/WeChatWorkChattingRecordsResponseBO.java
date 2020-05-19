package cn.com.xinxin.sass.sal.model.wechatwork.response;

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
