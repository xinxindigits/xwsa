package cn.com.xinxin.sass.sal.model.wechatwork;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author: liuhangzhou
 * @created: 2020/4/20.
 * @updater:
 * @description: 企业微信部门BO
 */
public class WeChatWorkDepartmentBO {
    /**
     * 创建的部门id
     */
    @JSONField(name = "id")
    private Long departmentId;

    /**
     * 部门名称
     */
    @JSONField(name = "name")
    private String departmentName;

    /**
     * 英文名称
     */
    @JSONField(name = "name_en")
    private String departmentEnglishName;

    /**
     * 父部门id。根部门为1
     */
    @JSONField(name = "parentid")
    private Long departmentParentId;

    /**
     * 在父部门中的次序值。order值大的排序靠前。值范围是[0, 2^32)
     */
    @JSONField(name = "order")
    private Long departmentOrder;

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getDepartmentEnglishName() {
        return departmentEnglishName;
    }

    public void setDepartmentEnglishName(String departmentEnglishName) {
        this.departmentEnglishName = departmentEnglishName;
    }

    public Long getDepartmentParentId() {
        return departmentParentId;
    }

    public void setDepartmentParentId(Long departmentParentId) {
        this.departmentParentId = departmentParentId;
    }

    public Long getDepartmentOrder() {
        return departmentOrder;
    }

    public void setDepartmentOrder(Long departmentOrder) {
        this.departmentOrder = departmentOrder;
    }

    @Override
    public String toString() {
        return "WeChatWorkDepartmentBO{" +
                "departmentId=" + departmentId +
                ", departmentName='" + departmentName + '\'' +
                ", departmentEnglishName='" + departmentEnglishName + '\'' +
                ", departmentParentId=" + departmentParentId +
                ", departmentOrder=" + departmentOrder +
                '}';
    }
}
