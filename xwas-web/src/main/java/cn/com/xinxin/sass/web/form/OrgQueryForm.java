package cn.com.xinxin.sass.web.form;

import cn.com.xinxin.sass.api.base.ToString;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * @author: zhouyang
 * @created: 13/07/2018.
 * @updater:
 * @description: 组织创建数据转换对象
 */
public class OrgQueryForm extends ToString {

    private static final long serialVersionUID = -3821494142512774077L;


    private Integer pageSize;
    private Integer pageIndex;
    private String orgId;
    private String tenantId;
    private String orgType;
    private String orgName;


    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date startTime;
    /**
     *
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date endTime;

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    public String getOrgType() {
        return orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
