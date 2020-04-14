package cn.com.xinxin.sass.web.vo;

/**
 * Created by dengyunhui on 2018/5/2
 **/
public class UserVO {

    private String username;

    private String password;

    private String rememberMe;

    public String getRememberMe() {
        return rememberMe;
    }

    public void setRememberMe(String rememberMe) {
        this.rememberMe = rememberMe;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
