package cn.com.xinxin.sass.web.vo;

import cn.com.xinxin.sass.api.base.ToString;

import java.util.Date;

/**
 * @author: zhouyang
 * @created: 21/04/2020.
 * @updater:
 * @description:
 */
public class RoleVO extends ToString {

    private static final long serialVersionUID = 9038508462834612482L;

    /**
     * 角色Id
     */
    private Long id;
    /**
     * 角色名称
     */
    private String name;
    /**
     * 角色类型
     */
    private String roleType;
    /**
     * 角色编码
     */
    private String code;
    /**
     * 创建时间
     */
    private Date gmtCreated;

    /**
     * 扩展
     */
    private String extension;

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

    public Date getGmtCreated() {
        return gmtCreated;
    }

    public void setGmtCreated(Date gmtCreated) {
        this.gmtCreated = gmtCreated;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }
}
