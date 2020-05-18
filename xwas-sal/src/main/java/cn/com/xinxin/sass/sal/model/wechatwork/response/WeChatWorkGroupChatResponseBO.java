package cn.com.xinxin.sass.sal.model.wechatwork.response;

import cn.com.xinxin.sass.sal.model.wechatwork.WeChatWorkGroupChatBO;

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
    private List<WeChatWorkGroupChatBO> weChatWorkGroupChatBOS;

    public List<WeChatWorkGroupChatBO> getWeChatWorkGroupChatBOS() {
        return weChatWorkGroupChatBOS;
    }

    public void setWeChatWorkGroupChatBOS(List<WeChatWorkGroupChatBO> weChatWorkGroupChatBOS) {
        this.weChatWorkGroupChatBOS = weChatWorkGroupChatBOS;
    }
}
