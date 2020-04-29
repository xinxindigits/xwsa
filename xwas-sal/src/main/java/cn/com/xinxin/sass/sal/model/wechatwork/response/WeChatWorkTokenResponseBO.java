package cn.com.xinxin.sass.sal.model.wechatwork.response;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author: liuhangzhou
 * @created: 2020/4/20.
 * @updater:
 * @description: 企业微信tokenBO
 */
public class WeChatWorkTokenResponseBO extends WeChatWorkResponseBaseBO {

    /**
     * 获取到的凭证，最长为512字节
     */
    @JSONField(name = "access_token")
    private String token;

    /**
     * 凭证的有效时间（秒）
     */
    @JSONField(name = "expires_in")
    private Integer validTime;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getValidTime() {
        return validTime;
    }

    public void setValidTime(Integer validTime) {
        this.validTime = validTime;
    }

    @Override
    public String toString() {
        return "WeChatWorkTokenResponseBO{" +
                "token='" + token + '\'' +
                ", validTime=" + validTime +
                '}';
    }
}
