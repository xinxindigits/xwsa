package cn.com.xinxin.sass.sal.model.wechatwork.externalprofile;

/**
 * @author: liuhangzhou
 * @created: 2020/4/20.
 * @updater:
 * @description: 企业微信成员扩展属性：文本类型的属性
 */
public class WeChatWorkExternalAttributeTextBO {
    /**
     * 文本内容
     */
    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "WeChatWorkExternalAttributeTextBO{" +
                "value='" + value + '\'' +
                '}';
    }
}
