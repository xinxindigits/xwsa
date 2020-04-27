package cn.com.xinxin.sass.web.form;

import cn.com.xinxin.sass.api.base.ToString;

import java.util.List;

/**
 * @author: yanghaoxian
 * @created: 2020/4/27.
 * @updater:
 * @description:
 */
public class DeleteUserForm extends ToString {

    private List<String> accounts;

    public List<String> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<String> accounts) {
        this.accounts = accounts;
    }
}
