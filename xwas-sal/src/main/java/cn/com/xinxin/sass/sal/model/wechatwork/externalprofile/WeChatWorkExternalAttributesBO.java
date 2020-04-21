package cn.com.xinxin.sass.sal.model.wechatwork.externalprofile;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

/**
 * @author: liuhangzhou
 * @created: 2020/4/20.
 * @updater:
 * @description: 企业微信成员扩展属性列表
 */
public class WeChatWorkExternalAttributesBO {
    /**
     * 企业微信成员扩展属性集合
     */
    @JSONField(name = "attrs")
    private List<WeChatWorkExternalAttributeDetailBO> externalAttributeDetailBOS;

    public List<WeChatWorkExternalAttributeDetailBO> getExternalAttributeDetailBOS() {
        return externalAttributeDetailBOS;
    }

    public void setExternalAttributeDetailBOS(List<WeChatWorkExternalAttributeDetailBO> externalAttributeDetailBOS) {
        this.externalAttributeDetailBOS = externalAttributeDetailBOS;
    }

    @Override
    public String toString() {
        return "WeChatWorkExternalAttributesBO{" +
                "externalAttributeDetailBOS=" + externalAttributeDetailBOS +
                '}';
    }
}
