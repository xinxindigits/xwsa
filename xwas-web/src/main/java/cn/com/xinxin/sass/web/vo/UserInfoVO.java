package cn.com.xinxin.sass.web.vo;

import cn.com.xinxin.sass.api.base.ToString;

import java.util.List;

/**
 * Created by dengyunhui on 2018/5/2
 **/
public class UserInfoVO extends ToString{

    private static final long serialVersionUID = 61839903145221657L;


    private Long id;

    private String account;

    private String name;

    private Integer gender;

    private String password;

    private String extension;

    /**
     * 用户对应的角色
     */
    private List<RoleVO> roles;

    /**
     * 用户对应的权限值
     */
    private List<ResourceVO> resources;



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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public List<RoleVO> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleVO> roles) {
        this.roles = roles;
    }

    public List<ResourceVO> getResources() {
        return resources;
    }

    public void setResources(List<ResourceVO> resources) {
        this.resources = resources;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
