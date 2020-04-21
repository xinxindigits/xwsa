package cn.com.xinxin.sass.sal.model.wechatwork.externalprofile;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

/**
 * @author: liuhangzhou
 * @created: 2020/4/20.
 * @updater:
 * @description: 成员对外信息
 */
public class WeChatWorkExternalProfileBO {
    /**
     * 企业简称
     */
    @JSONField(name = "external_corp_name")
    private String externalCorpName;

    /**
     * 属性列表，目前支持文本、网页、小程序三种类型
     */
    @JSONField(name = "external_attr")
    private List<WeChatWorkExternalAttributeDetailBO> externalAttributeBOList;

    public String getExternalCorpName() {
        return externalCorpName;
    }

    public void setExternalCorpName(String externalCorpName) {
        this.externalCorpName = externalCorpName;
    }

    public List<WeChatWorkExternalAttributeDetailBO> getExternalAttributeBOList() {
        return externalAttributeBOList;
    }

    public void setExternalAttributeBOList(List<WeChatWorkExternalAttributeDetailBO> externalAttributeBOList) {
        this.externalAttributeBOList = externalAttributeBOList;
    }

    @Override
    public String toString() {
        return "WeChatWorkExternalProfileBO{" +
                "externalCorpName='" + externalCorpName + '\'' +
                ", externalAttributeBOList=" + externalAttributeBOList +
                '}';
    }
}
