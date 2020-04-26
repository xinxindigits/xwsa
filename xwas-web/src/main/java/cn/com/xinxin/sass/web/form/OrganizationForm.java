package cn.com.xinxin.sass.web.form;

import cn.com.xinxin.sass.api.base.ToString;

/**
 * @author: zhouyang
 * @created: 13/07/2018.
 * @updater:
 * @description: 组织创建数据转换对象
 */
public class OrganizationForm extends ToString {

    private static final long serialVersionUID = 4596813473833470938L;

    private Long id;

    private String code;

    private String name;

    private Long parentId;

    private String orgType;

    private boolean isLeaf;

    private String state;

    private String remark;

    private String extension;

    private String corpId;

    private String privateKey;

    private String addressListSecret;

    private String customerContactSecret;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getOrgType() {
        return orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

    public Boolean getLeaf() {
        return isLeaf;
    }

    public void setLeaf(Boolean leaf) {
        isLeaf = leaf;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public String getAddressListSecret() {
        return addressListSecret;
    }

    public void setAddressListSecret(String addressListSecret) {
        this.addressListSecret = addressListSecret;
    }

    public String getCustomerContactSecret() {
        return customerContactSecret;
    }

    public void setCustomerContactSecret(String customerContactSecret) {
        this.customerContactSecret = customerContactSecret;
    }

    public String getCorpId() {
        return corpId;
    }

    public void setCorpId(String corpId) {
        this.corpId = corpId;
    }
}
