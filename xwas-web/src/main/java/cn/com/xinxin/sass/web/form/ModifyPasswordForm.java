package cn.com.xinxin.sass.web.form;

import cn.com.xinxin.sass.api.base.ToString;

/**
 * @author dengyunhui
 * @created 2018/11/12 11:12
 * @updated
 * @description 修改密码表单
 **/
public class ModifyPasswordForm extends ToString {

    /**
     * 用户id
     */
    private Long id;
    /**
     * 旧密码
     */
    private String originPasswd;
    /**
     * 新密码
     */
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
