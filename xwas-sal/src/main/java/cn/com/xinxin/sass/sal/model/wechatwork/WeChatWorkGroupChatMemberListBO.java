package cn.com.xinxin.sass.sal.model.wechatwork;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author: liuhangzhou
 * @created: 2020/5/18.
 * @updater:
 * @description: 企业微信群成员信息
 */
public class WeChatWorkGroupChatMemberListBO {
    /**
     * 群成员id
     */
    @JSONField(name = "userid")
    private String userId;

    /**
     * 成员类型。
     * 1 - 企业成员
     * 2 - 外部联系人
     */
    private Integer type;

    /**
     * 入群时间
     */
    @JSONField(name = "join_time")
    private String joinTime;

    /**
     * 入群方式。
     * 1 - 由成员邀请入群（直接邀请入群）
     * 2 - 由成员邀请入群（通过邀请链接入群）
     * 3 - 通过扫描群二维码入群
     */
    @JSONField(name = "join_scene")
    private Integer joinScene;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getJoinTime() {
        return joinTime;
    }

    public void setJoinTime(String joinTime) {
        this.joinTime = joinTime;
    }

    public Integer getJoinScene() {
        return joinScene;
    }

    public void setJoinScene(Integer joinScene) {
        this.joinScene = joinScene;
    }
}
