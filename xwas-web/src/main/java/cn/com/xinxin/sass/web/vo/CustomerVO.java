package cn.com.xinxin.sass.web.vo;

import cn.com.xinxin.sass.api.base.ToString;

import java.util.List;

/**
 * @author: liuhangzhou
 * @created: 2020/4/28.
 * @updater:
 * @description: 客户VO
 */
public class CustomerVO extends ToString {

    private static final long serialVersionUID = -5131016140971315036L;

    /**
     * 数据库主键
     */
    private Long id;

    /**
     * 成员userId
     */
    private String memberUserId;

    /**
     * 客户userId
     */
    private String userId;

    /**
     * 客户姓名
     */
    private String customerName;

    /**
     * 客户类型
     */
    private Integer customerType;

    /**
     * 性别
     */
    private String gender;

    /**
     * unionId
     */
    private String unionId;

    /**
     * 客户职位
     */
    private String customerPosition;

    /**
     * 公司名
     */
    private String corpName;

    /**
     * 公司全称
     */
    private String corpFullName;

    /**
     * 扩展信息
     */
    private String externalProfile;

    /**
     * 跟进成员
     */
    private String followUser;

    /**
     * 状态
     */
    private String status;

    /**
     * 头像地址
     */
    private String avatar;

    private List<TagsVO> tags;

    /**
     * 创建时间
     */
    private String createdTime;


    public List<TagsVO> getTags() {
        return tags;
    }

    public void setTags(List<TagsVO> tags) {
        this.tags = tags;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMemberUserId() {
        return memberUserId;
    }

    public void setMemberUserId(String memberUserId) {
        this.memberUserId = memberUserId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Integer getCustomerType() {
        return customerType;
    }

    public void setCustomerType(Integer customerType) {
        this.customerType = customerType;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getUnionId() {
        return unionId;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId;
    }

    public String getCustomerPosition() {
        return customerPosition;
    }

    public void setCustomerPosition(String customerPosition) {
        this.customerPosition = customerPosition;
    }

    public String getCorpName() {
        return corpName;
    }

    public void setCorpName(String corpName) {
        this.corpName = corpName;
    }

    public String getCorpFullName() {
        return corpFullName;
    }

    public void setCorpFullName(String corpFullName) {
        this.corpFullName = corpFullName;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }
}
