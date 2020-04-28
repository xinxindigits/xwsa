package cn.com.xinxin.sass.web.vo;

import cn.com.xinxin.sass.api.base.ToString;

/**
 * @author: yanghaoxian
 * @created: 2020/4/24.
 * @updater:
 * @description:组织架构VO
 */
public class OrganizationVO extends ToString {

    private String code;

    private String name;

    private String corpId;

    private String addressListSecret;

    private String customerContactSecret;

    private String chatRecordSecret;

    private String privateKey;

    private String extension;

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

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getChatRecordSecret() {
        return chatRecordSecret;
    }

    public void setChatRecordSecret(String chatRecordSecret) {
        this.chatRecordSecret = chatRecordSecret;
    }
}
