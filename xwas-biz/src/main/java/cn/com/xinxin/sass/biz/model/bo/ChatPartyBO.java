package cn.com.xinxin.sass.biz.model.bo;

/**
 * @author: liuhangzhou
 * @created: 2020/5/6.
 * @updater:
 * @description: 聊天方BO
 */
public class ChatPartyBO {
    /**
     * 聊天方类型，0-人，1-群
     */
    private Integer type;
    /**
     * 群id
     */
    private String roomId;
    /**
     * 群名
     */
    private String roomName;
    /**
     * 用户id
     */
    private String userId;
    /**
     * 用户名
     */
    private String userName;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
