package cn.com.xinxin.sass.sal.model.wechatwork;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author: liuhangzhou
 * @created: 2020/5/15.
 * @updater:
 * @description: 企业微信群聊信息
 */
public class WeChatWorkGroupChatBO {

    /**
     * 群id
     */
    @JSONField(name = "chat_id")
    private String chatId;

    /**
     * 客户群状态。
     * 0 - 正常
     * 1 - 跟进人离职
     * 2 - 离职继承中
     * 3 - 离职继承完成
     */
    private Integer status;

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
