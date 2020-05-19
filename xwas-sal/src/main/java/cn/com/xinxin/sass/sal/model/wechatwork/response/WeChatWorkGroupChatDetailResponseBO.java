package cn.com.xinxin.sass.sal.model.wechatwork.response;

import cn.com.xinxin.sass.sal.model.wechatwork.WeChatWorkGroupChatDetailBO;
import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author: liuhangzhou
 * @created: 2020/5/18.
 * @updater:
 * @description: 企业微信查询群聊详细信息返回值
 */
public class WeChatWorkGroupChatDetailResponseBO extends WeChatWorkResponseBaseBO {
    /**
     * 群聊详情
     */
    @JSONField(name = "group_chat")
    private WeChatWorkGroupChatDetailBO weChatWorkGroupChatDetailBO;

    public WeChatWorkGroupChatDetailBO getWeChatWorkGroupChatDetailBO() {
        return weChatWorkGroupChatDetailBO;
    }

    public void setWeChatWorkGroupChatDetailBO(WeChatWorkGroupChatDetailBO weChatWorkGroupChatDetailBO) {
        this.weChatWorkGroupChatDetailBO = weChatWorkGroupChatDetailBO;
    }
}
