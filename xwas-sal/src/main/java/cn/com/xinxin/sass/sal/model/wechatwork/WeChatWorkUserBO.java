package cn.com.xinxin.sass.sal.model.wechatwork;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

/**
 * @author: liuhangzhou
 * @created: 2020/4/20.
 * @updater:
 * @description: 企业微信成员BO
 */
public class WeChatWorkUserBO {
    /**
     * 成员UserID。对应管理端的帐号
     */
    @JSONField(name = "userid")
    private String userId;

    /**
     * 成员名称
     */
    @JSONField(name = "name")
    private String userName;


    /**
     * 成员所属部门id列表，仅返回该应用有查看权限的部门id
     */
    @JSONField(name = "department")
    private List<Integer> departmentIds;

    /**
     * 部门内的排序值，32位整数，默认为0。数量必须和department一致，数值越大排序越前面。
     */
    @JSONField(name = "order")
    private List<Integer> departmentOrders;

    /**
     * 职务信息；
     */
    @JSONField(name = "position")
    private String position;

    /**
     * 手机号码
     */
    @JSONField(name = "mobile")
    private String mobile;

    /**
     * 性别。0表示未定义，1表示男性，2表示女性
     */
    @JSONField(name = "gender")
    private Integer gender;

    /**
     * 邮箱
     */
    @JSONField(name = "email")
    private String email;

    /**
     * 表示在所在的部门内是否为上级；
     */
    @JSONField(name = "is_leader_in_dept")
    private List<Integer> leadingDepartments;

    /**
     * 头像url
     */
    @JSONField(name = "avatar")
    private String avatar;

    /**
     * 头像缩略图url
     */
    @JSONField(name = "thumb_avatar")
    private String thumbAvatar;

    /**
     * 座机
     */
    @JSONField(name = "telephone")
    private String telephone;

    /**
     * 别名
     */
    @JSONField(name = "alias")
    private String alias;

    /**
     * 激活状态: 1=已激活，2=已禁用，4=未激活，5=退出企业。
     */
    @JSONField(name = "status")
    private Integer status;

    /**
     * 地址
     */
    @JSONField(name = "address")
    private String address;

    /**
     * 是否隐藏手机号
     */
    @JSONField(name = "hide_mobile")
    private Integer hideMobile;

    /**
     * 英文名
     */
    @JSONField(name = "english_name")
    private String englishName;

    /**
     * 全局唯一。对于同一个服务商，不同应用获取到企业内同一个成员的open_userid是相同的，最多64个字节
     */
    @JSONField(name = "open_userid")
    private String openUserId;

    /**
     * 主部门
     */
    @JSONField(name = "main_department")
    private Long mainDepartment;

    /**
     * 扩展属性
     */
    @JSONField(name = "extattr")
    private String externalAttributes;

    /**
     * 员工个人二维码，扫描可添加为外部联系人
     */
    @JSONField(name = "qr_code")
    private String QRCode;

    /**
     * 对外职务
     */
    @JSONField(name = "external_position")
    private String externalPosition;

    /**
     * 成员对外属性
     */
    @JSONField(name = "external_profile")
    private String externalProfile;

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

    public List<Integer> getDepartmentIds() {
        return departmentIds;
    }

    public void setDepartmentIds(List<Integer> departmentIds) {
        this.departmentIds = departmentIds;
    }

    public List<Integer> getDepartmentOrders() {
        return departmentOrders;
    }

    public void setDepartmentOrders(List<Integer> departmentOrders) {
        this.departmentOrders = departmentOrders;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Integer> getLeadingDepartments() {
        return leadingDepartments;
    }

    public void setLeadingDepartments(List<Integer> leadingDepartments) {
        this.leadingDepartments = leadingDepartments;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getThumbAvatar() {
        return thumbAvatar;
    }

    public void setThumbAvatar(String thumbAvatar) {
        this.thumbAvatar = thumbAvatar;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getHideMobile() {
        return hideMobile;
    }

    public void setHideMobile(Integer hideMobile) {
        this.hideMobile = hideMobile;
    }

    public String getEnglishName() {
        return englishName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    public String getOpenUserId() {
        return openUserId;
    }

    public void setOpenUserId(String openUserId) {
        this.openUserId = openUserId;
    }

    public Long getMainDepartment() {
        return mainDepartment;
    }

    public void setMainDepartment(Long mainDepartment) {
        this.mainDepartment = mainDepartment;
    }

    public String getExternalAttributes() {
        return externalAttributes;
    }

    public void setExternalAttributes(String externalAttributes) {
        this.externalAttributes = externalAttributes;
    }

    public String getQRCode() {
        return QRCode;
    }

    public void setQRCode(String QRCode) {
        this.QRCode = QRCode;
    }

    public String getExternalPosition() {
        return externalPosition;
    }

    public void setExternalPosition(String externalPosition) {
        this.externalPosition = externalPosition;
    }

    public String getExternalProfile() {
        return externalProfile;
    }

    public void setExternalProfile(String externalProfile) {
        this.externalProfile = externalProfile;
    }

    @Override
    public String toString() {
        return "WeChatWorkUserBO{" +
                "userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", departmentIds=" + departmentIds +
                ", departmentOrders=" + departmentOrders +
                ", position='" + position + '\'' +
                ", mobile='" + mobile + '\'' +
                ", gender='" + gender + '\'' +
                ", email='" + email + '\'' +
                ", leadingDepartments=" + leadingDepartments +
                ", avatar='" + avatar + '\'' +
                ", thumbAvatar='" + thumbAvatar + '\'' +
                ", telephone='" + telephone + '\'' +
                ", alias='" + alias + '\'' +
                ", status='" + status + '\'' +
                ", address='" + address + '\'' +
                ", hideMobile=" + hideMobile +
                ", englishName='" + englishName + '\'' +
                ", openUserId='" + openUserId + '\'' +
                ", mainDepartment=" + mainDepartment +
                ", externalAttributes='" + externalAttributes + '\'' +
                ", QRCode='" + QRCode + '\'' +
                ", externalPosition='" + externalPosition + '\'' +
                ", externalProfile='" + externalProfile + '\'' +
                '}';
    }
}
