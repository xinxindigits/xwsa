package cn.com.xinxin.sass.sal.model.wechatwork.response;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author: liuhangzhou
 * @created: 2020/4/20.
 * @updater:
 * @description: 企业微信响应基础BO
 */
public class WeChatWorkResponseBaseBO {
    /**
     * 返回码
     */
    @JSONField(name = "errcode")
    private Integer errorCode;

    /**
     * 对返回码的文本描述内容
     */
    @JSONField(name = "errmsg")
    private String errorMessage;

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public String toString() {
        return "WeChatWorkResponseBaseBO{" +
                "errorCode='" + errorCode + '\'' +
                ", errorMessage='" + errorMessage + '\'' +
                '}';
    }
}
