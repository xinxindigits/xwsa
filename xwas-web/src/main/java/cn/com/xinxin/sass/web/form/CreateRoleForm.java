package cn.com.xinxin.sass.web.form;

import cn.com.xinxin.sass.api.base.ToString;

import java.util.List;

/**
 * @author: yanghaoxian
 * @created: 2020/4/21.
 * @updater:
 * @description:增加角色表单
 */
public class CreateRoleForm extends ToString {

    /**
     * 角色名
     */
    private String name;
    /**
     * 角色类型
     */
    private String roleType;
    /**
     * 编码
     */
    private String code;
    /**
     * 描述
     */
    private String extension;
    /**
     * 资源列表
     */
    private List<String> resourceList;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRoleType() {
        return roleType;
    }

    public void setRoleType(String roleType) {
        this.roleType = roleType;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }


    public List<String> getResourceList() {
        return resourceList;
    }

    public void setResourceList(List<String> resourceList) {
        this.resourceList = resourceList;
    }
}
