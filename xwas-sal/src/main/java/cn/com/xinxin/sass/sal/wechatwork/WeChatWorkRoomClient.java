package cn.com.xinxin.sass.sal.wechatwork;

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
