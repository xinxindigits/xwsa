package cn.com.xinxin.sass.web.form;

import cn.com.xinxin.sass.api.base.ToString;

import java.util.List;

/**
 * @author: zhouyang
 * @created: 13/07/2018.
 * @updater:
 * @description:用户-角色授权表单
 */
public class UserRoleGrantForm extends ToString {

    private static final long serialVersionUID = -679598670792406818L;
    /**
     *
     */
    private String userAccount;
    /**
     *
     */
    private String userName;

    private String extension;
    /**
     *
     */
    private List<UserRoleForm> userRoles;

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public List<UserRoleForm> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(List<UserRoleForm> userRoles) {
        this.userRoles = userRoles;
    }
}
