package cn.com.xinxin.sass.web.vo;

import cn.com.xinxin.sass.api.base.ToString;

/**
 * Created by dengyunhui on 2018/5/10
 **/
public class RoleResourceVO extends ToString{

    private static final long serialVersionUID = 3823061084955873045L;


    private Long id;
    private String name;
    private String roleType;
    private String code;
    private String roleResources;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getRoleResources() {
        return roleResources;
    }

    public void setRoleResources(String roleResources) {
        this.roleResources = roleResources;
    }
}
