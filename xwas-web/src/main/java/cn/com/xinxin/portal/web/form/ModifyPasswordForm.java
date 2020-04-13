package cn.com.xinxin.portal.web.form;

import cn.com.xinxin.sass.api.base.ToString;

/**
 * @author dengyunhui
 * @created 2018/11/12 11:12
 * @updated
 * @description
 **/
public class ModifyPasswordForm extends ToString {

    private Long id;
    private String originPasswd;
    private String newPasswd;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOriginPasswd() {
        return originPasswd;
    }

    public ModifyPasswordForm setOriginPasswd(String originPasswd) {
        this.originPasswd = originPasswd;
        return this;
    }

    public String getNewPasswd() {
        return newPasswd;
    }

    public void setNewPasswd(String newPasswd) {
        this.newPasswd = newPasswd;
    }
}
