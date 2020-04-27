package cn.com.xinxin.sass.web.form;

import cn.com.xinxin.sass.api.base.ToString;

/**
 * @author: zhouyang
 * @created: 27/04/2020.
 * @updater:
 * @description:
 */
public class WechatOrgQueryForm extends ToString {

    private static final long serialVersionUID = -3924428072428332894L;

    private long id;

    private String orgId;

    private String departmentId;

    private String departmentName;

    private String englishName;

    private String parentId;

    private int departmentOrder;

    private String status;

    private String extension;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getEnglishName() {
        return englishName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public int getDepartmentOrder() {
        return departmentOrder;
    }

    public void setDepartmentOrder(int departmentOrder) {
        this.departmentOrder = departmentOrder;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
