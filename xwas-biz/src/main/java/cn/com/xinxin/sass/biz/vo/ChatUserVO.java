package cn.com.xinxin.sass.biz.vo;

import cn.com.xinxin.sass.api.base.ToString;

/**
 * @author: yanghaoxian
 * @created: 2020/4/30.
 * @updater:
 * @description:
 */
public class ChatUserVO extends ToString {

    private String chatUserId;

    private String chatUserName;

    private String chatUserType;

    private String avatar;

    public String getChatUserId() {
        return chatUserId;
    }

    public void setChatUserId(String chatUserId) {
        this.chatUserId = chatUserId;
    }

    public String getChatUserName() {
        return chatUserName;
    }

    public void setChatUserName(String chatUserName) {
        this.chatUserName = chatUserName;
    }

    public String getChatUserType() {
        return chatUserType;
    }

    public void setChatUserType(String chatUserType) {
        this.chatUserType = chatUserType;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
