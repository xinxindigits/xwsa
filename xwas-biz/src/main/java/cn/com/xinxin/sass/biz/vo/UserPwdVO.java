package cn.com.xinxin.sass.biz.vo;

/**
 * Created by dengyunhui on 2018/5/2
 **/
public class UserPwdVO {
    public UserPwdVO(String password, String salt) {
        this.password = password;
        this.salt = salt;
    }

    private String password;

    private String salt;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }
}
