package cn.com.xinxin.sass.sal.model.wechatwork;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

/**
 * @author: liuhangzhou
 * @created: 2020/4/27.
 * @updater:
 * @description: 企业微信聊天记录BO
 */
public class WeChatWorkChattingRecordsBO {
    /**
     * 消息id，消息的唯一标识，企业可以使用此字段进行消息去重
     */
    @JSONField(name = "msgid")
    private String msgId;

    /**
     * 消息动作，目前有send(发送消息)/recall(撤回消息)/switch(切换企业日志)三种类型。
     */
    private String action;

    /**
     * 消息发送方id。同一企业内容为userid，非相同企业为external_userid。消息如果是机器人发出，也为external_userid。
     */
    private String from;

    /**
     *消息接收方列表，可能是多个，同一个企业内容为userid，非相同企业为external_userid
     */
    @JSONField(name = "tolist")
    private List<String> toList;

    /**
     * 群聊消息的群id。如果是单聊则为空。
     */
    @JSONField(name = "roomid")
    private String roomId;

    /**
     * 消息发送时间戳，utc时间
     */
    @JSONField(name = "msgtime")
    private String msgTime;

    /**
     * 文本消息类型
     */
    @JSONField(name = "msgtype")
    private String msgType;

    /**
     * 文本
     */
    private String text;
    /**
     * 图片
     */
    private String image;
    /**
     * 撤回消息
     */
    private String revoke;
    /**
     * 同意会话聊天内容
     */
    private String agree;
    /**
     * 不同意会话聊天内容
     */
    private String disagree;
    /**
     * 语音
     */
    private String voice;
    /**
     * 视频
     */
    private String video;
    /**
     * 名片
     */
    private String card;
    /**
     * 位置
     */
    private String location;
    /**
     * 表情
     */
    private String emotion;
    /**
     * 文件
     */
    private String file;
    /**
     * 链接
     */
    private String link;
    /**
     * 小程序
     */
    private String weapp;
    /**
     * 会话记录消息
     */
    private String chatrecord;
    /**
     * 待办消息
     */
    private String todo;
    /**
     * 投票消息
     */
    private String vote;
    /**
     * 填表消息
     */
    private String collect;
    /**
     * 红包消息
     */
    private String redpacket;
    /**
     * 会议邀请消息
     */
    private String meeting;
    /**
     * 在线文档消息
     */
    private String docmsg;
    /**
     * MarkDown格式消息
     */
    private String markdown;
    /**
     * 图文消息
     */
    private String news;
    /**
     * 日程消息
     */
    private String calendar;
    /**
     * 混合消息
     */
    private String mixed;

    /**
     * 切换企业的时间戳
     */
    private String time;

    /**
     * 切换企业的用户
     */
    private String user;

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

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public List<String> getToList() {
        return toList;
    }

    public void setToList(List<String> toList) {
        this.toList = toList;
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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getRevoke() {
        return revoke;
    }

    public void setRevoke(String revoke) {
        this.revoke = revoke;
    }

    public String getAgree() {
        return agree;
    }

    public void setAgree(String agree) {
        this.agree = agree;
    }

    public String getDisagree() {
        return disagree;
    }

    public void setDisagree(String disagree) {
        this.disagree = disagree;
    }

    public String getVoice() {
        return voice;
    }

    public void setVoice(String voice) {
        this.voice = voice;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getEmotion() {
        return emotion;
    }

    public void setEmotion(String emotion) {
        this.emotion = emotion;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getWeapp() {
        return weapp;
    }

    public void setWeapp(String weapp) {
        this.weapp = weapp;
    }

    public String getChatrecord() {
        return chatrecord;
    }

    public void setChatrecord(String chatrecord) {
        this.chatrecord = chatrecord;
    }

    public String getTodo() {
        return todo;
    }

    public void setTodo(String todo) {
        this.todo = todo;
    }

    public String getVote() {
        return vote;
    }

    public void setVote(String vote) {
        this.vote = vote;
    }

    public String getCollect() {
        return collect;
    }

    public void setCollect(String collect) {
        this.collect = collect;
    }

    public String getRedpacket() {
        return redpacket;
    }

    public void setRedpacket(String redpacket) {
        this.redpacket = redpacket;
    }

    public String getMeeting() {
        return meeting;
    }

    public void setMeeting(String meeting) {
        this.meeting = meeting;
    }

    public String getDocmsg() {
        return docmsg;
    }

    public void setDocmsg(String docmsg) {
        this.docmsg = docmsg;
    }

    public String getMarkdown() {
        return markdown;
    }

    public void setMarkdown(String markdown) {
        this.markdown = markdown;
    }

    public String getNews() {
        return news;
    }

    public void setNews(String news) {
        this.news = news;
    }

    public String getCalendar() {
        return calendar;
    }

    public void setCalendar(String calendar) {
        this.calendar = calendar;
    }

    public String getMixed() {
        return mixed;
    }

    public void setMixed(String mixed) {
        this.mixed = mixed;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "WeChatWorkChattingRecordsBO{" +
                "msgId='" + msgId + '\'' +
                ", action='" + action + '\'' +
                ", from='" + from + '\'' +
                ", toList=" + toList +
                ", roomId='" + roomId + '\'' +
                ", msgTime='" + msgTime + '\'' +
                ", msgType='" + msgType + '\'' +
                ", text='" + text + '\'' +
                ", image='" + image + '\'' +
                ", revoke='" + revoke + '\'' +
                ", agree='" + agree + '\'' +
                ", disagree='" + disagree + '\'' +
                ", voice='" + voice + '\'' +
                ", video='" + video + '\'' +
                ", card='" + card + '\'' +
                ", location='" + location + '\'' +
                ", emotion='" + emotion + '\'' +
                ", file='" + file + '\'' +
                ", link='" + link + '\'' +
                ", weapp='" + weapp + '\'' +
                ", chatrecord='" + chatrecord + '\'' +
                ", todo='" + todo + '\'' +
                ", vote='" + vote + '\'' +
                ", collect='" + collect + '\'' +
                ", redpacket='" + redpacket + '\'' +
                ", meeting='" + meeting + '\'' +
                ", docmsg='" + docmsg + '\'' +
                ", markdown='" + markdown + '\'' +
                ", news='" + news + '\'' +
                ", calendar='" + calendar + '\'' +
                ", mixed='" + mixed + '\'' +
                ", time='" + time + '\'' +
                ", user='" + user + '\'' +
                '}';
    }
}
