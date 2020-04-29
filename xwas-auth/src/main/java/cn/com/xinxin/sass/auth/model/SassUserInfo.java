package cn.com.xinxin.sass.auth.model;

import org.apache.shiro.authz.Permission;

import java.io.Serializable;
import java.util.Set;

/**
 * @author: zhouyang
 * @created: 16/07/2018.
 * @updater:
 * @description:
 */
public class SassUserInfo implements Serializable {

    private static final long serialVersionUID = 2073926830281465880L;

    private Long id;

    private String name;

    private String account;

    private String salt;

    private String password;

    private String ip;

    private String device;

    private String orgCode;

    protected Set<String> roles;

    protected Set<String> stringPermissions;

    protected Set<Permission> objectPermissions;

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    public Set<String> getStringPermissions() {
        return stringPermissions;
    }

    public void setStringPermissions(Set<String> stringPermissions) {
        this.stringPermissions = stringPermissions;
    }

    public Set<Permission> getObjectPermissions() {
        return objectPermissions;
    }

    public void setObjectPermissions(Set<Permission> objectPermissions) {
        this.objectPermissions = objectPermissions;
    }

    @Override
    public String toString() {
        return "SassUserInfo, name = (" + this.getName()+");"
            +"account = (" + this.getAccount() + ");";
    }
}
