package cn.com.xinxin.sass.web.vo;

import cn.com.xinxin.sass.api.base.ToString;

/**
 * @author: yanghaoxian
 * @created: 2020/5/6.
 * @updater:
 * @description:
 */
public class TenantDetailVO extends ToString {

    private Long id;

    private String tenantId;

    private String tenantName;

    private String corpId;

    private String addressListSecret;

    private String customerContactSecret;

    private String chatRecordSecret;

    private String privateKey;

    private String remark;

    private String state;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getTenantName() {
        return tenantName;
    }

    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
    }

    public String getCorpId() {
        return corpId;
    }

    public void setCorpId(String corpId) {
        this.corpId = corpId;
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

    public String getChatRecordSecret() {
        return chatRecordSecret;
    }

    public void setChatRecordSecret(String chatRecordSecret) {
        this.chatRecordSecret = chatRecordSecret;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
