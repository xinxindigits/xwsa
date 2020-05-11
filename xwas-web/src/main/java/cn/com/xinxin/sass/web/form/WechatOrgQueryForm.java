package cn.com.xinxin.sass.web.form;

import cn.com.xinxin.sass.api.base.ToString;

/**
 * @author: zhouyang
 * @created: 27/04/2020.
 * @updater:
 * @description:查询企业微信部门详情表单
 */
public class WechatOrgQueryForm extends ToString {

    private static final long serialVersionUID = -3924428072428332894L;
    /**
     * 企业微信微信部门id
     */
    private String departmentId;
    /**
     * 企业微信部门名称
     */
    private String departmentName;
    /**
     * 英文名
     */
    private String englishName;

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


}
