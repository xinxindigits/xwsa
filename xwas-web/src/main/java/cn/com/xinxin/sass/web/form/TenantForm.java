package cn.com.xinxin.sass.web.form;

import cn.com.xinxin.sass.api.base.ToString;

/**
 * @author: yanghaoxian
 * @created: 2020/5/6.
 * @updater:
 * @description:租户管理表单
 */
public class TenantForm extends ToString {

    private Integer pageSize;

    private Integer pageIndex;

    private String code;

    private String state;

    private String remark;

    private String name;

    private String corpId;

    private String privateKey;

    private String addressListSecret;

    private String chatRecordSecret;

    private String customerContactSecret;

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCorpId() {
        return corpId;
    }

    public void setCorpId(String corpId) {
        this.corpId = corpId;
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

    public String getChatRecordSecret() {
        return chatRecordSecret;
    }

    public void setChatRecordSecret(String chatRecordSecret) {
        this.chatRecordSecret = chatRecordSecret;
    }

    public String getCustomerContactSecret() {
        return customerContactSecret;
    }

    public void setCustomerContactSecret(String customerContactSecret) {
        this.customerContactSecret = customerContactSecret;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
