package cn.com.xinxin.sass.sal.model.wechatwork;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author: liuhangzhou
 * @created: 2020/4/20.
 * @updater:
 * @description: 企业微信客户BO
 */
public class WeChatWorkCustomerBO {

    /**
     * 外部联系人的userid
     */
    @JSONField(name = "external_userid")
    private String externalUserId;

    /**
     * 外部联系人的名称
     */
    private String name;

    /**
     * 外部联系人的职位，如果外部企业或用户选择隐藏职位
     */
    private String position;

    /**
     * 外部联系人头像
     */
    private String avatar;

    /**
     * 外部联系人所在企业的简称
     */
    @JSONField(name = "corp_name")
    private String corporationName;

    /**
     * 外部联系人所在企业的主体名称
     */
    @JSONField(name = "corp_full_name")
    private String corporationFullName;

    /**
     * 外部联系人的类型，1表示该外部联系人是微信用户，2表示该外部联系人是企业微信用户
     */
    private Integer type;

    /**
     * 外部联系人性别 0-未知 1-男性 2-女性
     */
    private Integer gender;

    /**
     * 外部联系人在微信开放平台的唯一身份标识（微信unionid）
     */
    @JSONField(name = "unionid")
    private String unionId;

    /**
     * 外部联系人的自定义展示信息，可以有多个字段和多种类型，包括文本，网页和小程序
     */
    @JSONField(name = "external_profile")
    private String externalProfile;

    /**
     * 添加了此外部联系人的企业成员
     */
    @JSONField(name = "follow_user")
    private String followUser;

    public String getExternalUserId() {
        return externalUserId;
    }

    public void setExternalUserId(String externalUserId) {
        this.externalUserId = externalUserId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getCorporationName() {
        return corporationName;
    }

    public void setCorporationName(String corporationName) {
        this.corporationName = corporationName;
    }

    public String getCorporationFullName() {
        return corporationFullName;
    }

    public void setCorporationFullName(String corporationFullName) {
        this.corporationFullName = corporationFullName;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getUnionId() {
        return unionId;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId;
    }

    public String getExternalProfile() {
        return externalProfile;
    }

    public void setExternalProfile(String externalProfile) {
        this.externalProfile = externalProfile;
    }

    public String getFollowUser() {
        return followUser;
    }

    public void setFollowUser(String followUser) {
        this.followUser = followUser;
    }

    @Override
    public String toString() {
        return "WeChatWorkCustomerBO{" +
                "externalUserId='" + externalUserId + '\'' +
                ", name='" + name + '\'' +
                ", position='" + position + '\'' +
                ", avatar='" + avatar + '\'' +
                ", corporationName='" + corporationName + '\'' +
                ", corporationFullName='" + corporationFullName + '\'' +
                ", type=" + type +
                ", gender=" + gender +
                ", unionId='" + unionId + '\'' +
                ", externalProfile='" + externalProfile + '\'' +
                ", followUser='" + followUser + '\'' +
                '}';
    }
}
