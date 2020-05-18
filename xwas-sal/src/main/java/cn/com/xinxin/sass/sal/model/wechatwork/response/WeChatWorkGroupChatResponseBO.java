package cn.com.xinxin.sass.sal.model.wechatwork.response;

import cn.com.xinxin.sass.sal.model.wechatwork.WeChatWorkGroupChatBaseBO;
import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

/**
 * @author: liuhangzhou
 * @created: 2020/5/15.
 * @updater:
 * @description: 企业微信查询群聊信息返回值
 */
public class WeChatWorkGroupChatResponseBO extends WeChatWorkResponseBaseBO{
    /**
     * 群列表
     */
    @JSONField(name = "group_chat_list")
    private List<WeChatWorkGroupChatBaseBO> weChatWorkGroupChatBaseBOS;

    public List<WeChatWorkGroupChatBaseBO> getWeChatWorkGroupChatBaseBOS() {
        return weChatWorkGroupChatBaseBOS;
    }

    public void setWeChatWorkGroupChatBaseBOS(List<WeChatWorkGroupChatBaseBO> weChatWorkGroupChatBaseBOS) {
        this.weChatWorkGroupChatBaseBOS = weChatWorkGroupChatBaseBOS;
    }
}
