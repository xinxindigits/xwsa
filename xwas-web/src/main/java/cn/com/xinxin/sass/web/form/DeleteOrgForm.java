package cn.com.xinxin.sass.web.form;

import cn.com.xinxin.sass.api.base.ToString;

import java.util.List;

/**
 * @author: yanghaoxian
 * @created: 2020/4/27.
 * @updater:
 * @description:删除租户/组织表单
 */
public class DeleteOrgForm extends ToString {

    /**
     * 租户编码列表
     */
    private List<String> codes;

    /**
     * 组织机构id列表
     */
    private List<Long> ids;

    public List<String> getCodes() {
        return codes;
    }

    public void setCodes(List<String> codes) {
        this.codes = codes;
    }

    public List<Long> getIds() {
        return ids;
    }

    public void setIds(List<Long> ids) {
        this.ids = ids;
    }
}
