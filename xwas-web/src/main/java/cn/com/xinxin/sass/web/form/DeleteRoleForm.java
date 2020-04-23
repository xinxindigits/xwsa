package cn.com.xinxin.sass.web.form;

import cn.com.xinxin.sass.api.base.ToString;

import java.util.List;
import java.util.Set;

/**
 * @author: yanghaoxian
 * @created: 2020/4/23.
 * @updater:
 * @description:删除角色表单
 */
public class DeleteRoleForm extends ToString {

    /**
     * 角色列表
     */
    private List<String> roleCodes;

    public List<String> getRoleCodes() {
        return roleCodes;
    }

    public void setRoleCodes(List<String> roleCodes) {
        this.roleCodes = roleCodes;
    }
}
