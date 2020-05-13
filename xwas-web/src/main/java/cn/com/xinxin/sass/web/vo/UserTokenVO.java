package cn.com.xinxin.sass.web.vo;

import cn.com.xinxin.sass.api.base.ToString;

/**
 * @author: zhouyang
 * @created: 14/04/2020.
 * @updater:
 * @description:
 */
public class UserTokenVO extends ToString {

    private String account;

    private String userName;

    private String tenantId;

    private String token;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }
}
