package cn.com.xinxin.sass.web.form;

import cn.com.xinxin.sass.api.base.ToString;

import java.util.List;

/**
 * @author: yanghaoxian
 * @created: 2020/4/21.
 * @updater:
 * @description:角色分配表单
 */
public class RoleResourceGrantForm extends ToString {

    private static final long serialVersionUID = -1728805790896158L;


    private String roleCode;

    private String roleName;

    private List<RoleResourceForm> resources;

    private String extension;

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

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


    public List<RoleResourceForm> getResources() {
        return resources;
    }

    public void setResources(List<RoleResourceForm> resources) {
        this.resources = resources;
    }
}
