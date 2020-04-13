package cn.com.xinxin.portal.web.shiro.vo;

/**
 * @author: zhouyang
 * @created: 13/04/2020.
 * @updater:
 * @description:
 */
public class LoginSysUserRedisVo {

    private static final long serialVersionUID = -3858850188055605806L;

    /**
     * 包装后的盐值
     */
    private String salt;

    /**
     * 登录ip
     */
    //private ClientInfo clientInfo;


    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }
}
