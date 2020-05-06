package cn.com.xinxin.sass.web.vo;

import cn.com.xinxin.sass.api.base.ToString;

import java.util.Date;

/**
 * @author: zhouyang
 * @created: 30/04/2020.
 * @updater:
 * @description:
 */
public class TenantInfoVO extends ToString{


    private static final long serialVersionUID = -799872667116123750L;


    private Long id;


    private String tenantId;


    private String tenantName;


    private String corpId;


    private String addressListSecret;


    private String chatRecordSecret;


    private String customerContactSecret;


    private String privateKey;


    private String extension;


    private Date gmtCreated;


    private String gmtCreator;


    private Date gmtUpdated;


    private String gmtUpdater;

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

    public Date getGmtCreated() {
        return gmtCreated;
    }

    public void setGmtCreated(Date gmtCreated) {
        this.gmtCreated = gmtCreated;
    }

    public String getGmtCreator() {
        return gmtCreator;
    }

    public void setGmtCreator(String gmtCreator) {
        this.gmtCreator = gmtCreator;
    }

    public Date getGmtUpdated() {
        return gmtUpdated;
    }

    public void setGmtUpdated(Date gmtUpdated) {
        this.gmtUpdated = gmtUpdated;
    }

    public String getGmtUpdater() {
        return gmtUpdater;
    }

    public void setGmtUpdater(String gmtUpdater) {
        this.gmtUpdater = gmtUpdater;
    }
}
