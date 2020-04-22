package cn.com.xinxin.sass.web.form;

import cn.com.xinxin.sass.api.base.ToString;

import java.util.List;

/**
 * @author: yanghaoxian
 * @created: 2020/4/21.
 * @updater:
 * @description:角色分配表单
 */
public class RoleAuthorityForm extends ToString {

    private String roleCode;

    private String roleName;

    private List<UserForm> userList;

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public List<UserForm> getUserList() {
        return userList;
    }

    public void setUserList(List<UserForm> userList) {
        this.userList = userList;
    }
}
