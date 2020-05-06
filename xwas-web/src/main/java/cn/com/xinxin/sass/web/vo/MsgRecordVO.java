package cn.com.xinxin.sass.web.vo;

import cn.com.xinxin.sass.api.base.ToString;

/**
 * @author: liuhangzhou
 * @created: 2020/4/29.
 * @updater:
 * @description: 企业微信会话信息VO
 */
public class MsgRecordVO extends ToString {


    private static final long serialVersionUID = 4141052108991606074L;

    /**
     * 数据库主键
     */
    private Long id;
    /**
     * 机构id
     */
    private String orgId;

    /**
     * 消息序号
     */
    private Long seqId;

    /**
     * 消息id，唯一
     */
    private String msgId;

    /**
     * 消息动作 目前有send(发送消息)/recall(撤回消息)/switch(切换企业日志)三种类型
     */
    private String action;

    /**
     * 发送人
     */
    private String fromUserId;
    /**
     * 发送人姓名
     */
    private String fromUserName;
    /**
     * 群名
     */
    private String roomId;

    /**
     * 消息发送时间
     */
    private String msgTime;

    /**
     * 消息类型
     */
    private String msgType;

    /**
     * 接受人
     */
    private String toUserId;
    /**
     * 消息内容
     */
    private String content;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public Long getSeqId() {
        return seqId;
    }

    public void setSeqId(Long seqId) {
        this.seqId = seqId;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(String fromUserId) {
        this.fromUserId = fromUserId;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getMsgTime() {
        return msgTime;
    }

    public void setMsgTime(String msgTime) {
        this.msgTime = msgTime;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public String getToUserId() {
        return toUserId;
    }

    public void setToUserId(String toUserId) {
        this.toUserId = toUserId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFromUserName() {
        return fromUserName;
    }

    public void setFromUserName(String fromUserName) {
        this.fromUserName = fromUserName;
    }

    @Override
    public String toString() {
        return "MsgRecordVO{" +
                "orgId='" + orgId + '\'' +
                ", seqId=" + seqId +
                ", msgId='" + msgId + '\'' +
                ", action='" + action + '\'' +
                ", fromUserId='" + fromUserId + '\'' +
                ", fromUserName='" + fromUserName+ '\'' +
                ", roomId='" + roomId + '\'' +
                ", msgTime='" + msgTime + '\'' +
                ", msgType='" + msgType + '\'' +
                ", toUserId='" + toUserId + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
