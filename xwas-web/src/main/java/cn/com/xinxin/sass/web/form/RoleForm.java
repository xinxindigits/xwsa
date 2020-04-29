package cn.com.xinxin.sass.web.form;

import cn.com.xinxin.sass.api.base.ToString;


/**
 * @author: zhouyang
 * @created: 13/07/2018.
 * @updater:
 * @description: 角色表单接收类
 */
public class RoleForm extends ToString {

    private static final long serialVersionUID = -4560605194838588422L;

    /**
     * id
     */
    private String id;
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
     * 每页大小
     */
    private Integer pageSize;
    /**
     * 页码
     */
    private Integer pageNum;


    public String getId() {
        return id;
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

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

}
