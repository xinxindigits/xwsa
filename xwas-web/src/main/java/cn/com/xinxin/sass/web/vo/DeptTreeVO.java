package cn.com.xinxin.sass.web.vo;

import cn.com.xinxin.sass.api.base.ToString;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author: zhouyang
 * @created: 27/04/2020.
 * @updater:
 * @description: 微信企业部门对应的树结构
 */
public class DeptTreeVO extends ToString {

    private static final long serialVersionUID = -3596788724602948695L;


    private String id;


    private String orgId;


    private String departmentId;


    private String departmentName;


    private String englishName;


    private String parentId;


    private Long departmentOrder;


    private String status;


    private String extension;


    private Date gmtCreated;


    private Date gmtUpdated;


    /**
     * 是否默认展开
     */
    private Boolean expanded = false;


    /**
     * 节点的子节点
     */
    private List<DeptTreeVO> children = new ArrayList<>();


    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public Long getDepartmentOrder() {
        return departmentOrder;
    }

    public void setDepartmentOrder(Long departmentOrder) {
        this.departmentOrder = departmentOrder;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public Date getGmtCreated() {
        return gmtCreated;
    }

    public void setGmtCreated(Date gmtCreated) {
        this.gmtCreated = gmtCreated;
    }

    public Date getGmtUpdated() {
        return gmtUpdated;
    }

    public void setGmtUpdated(Date gmtUpdated) {
        this.gmtUpdated = gmtUpdated;
    }

    public List<DeptTreeVO> getChildren() {
        return children;
    }

    public void setChildren(List<DeptTreeVO> children) {
        this.children = children;
    }

    public Boolean getExpanded() {
        return expanded;
    }

    public void setExpanded(Boolean expanded) {
        this.expanded = expanded;
    }
}
