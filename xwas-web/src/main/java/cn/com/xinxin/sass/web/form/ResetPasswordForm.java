package cn.com.xinxin.sass.web.form;

import cn.com.xinxin.sass.api.base.ToString;

/**
 * @author dengyunhui
 * @created 2018/11/12 11:12
 * @updated
 * @description 重置密码表单
 **/
public class ResetPasswordForm extends ToString {

    /**
     * 用户id
     */
    private Long id;
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

    public String getNewPasswd() {
        return newPasswd;
    }

    public void setNewPasswd(String newPasswd) {
        this.newPasswd = newPasswd;
    }
}
