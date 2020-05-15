package cn.com.xinxin.sass.sal.wechatwork;

import cn.com.xinxin.sass.sal.model.wechatwork.WeChatWorkGroupChatBO;

import java.util.List;

/**
 * @author: liuhangzhou
 * @created: 2020/5/14.
 * @updater:
 * @description: 企业微信群Client
 */
public interface WeChatWorkRoomClient {

    List<WeChatWorkGroupChatBO> queryGroupChatList(String token);
}
