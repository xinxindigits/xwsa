package cn.com.xinxin.sass.sal.model.wechatwork.externalprofile;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author: liuhangzhou
 * @created: 2020/4/20.
 * @updater:
 * @description: 企业微信成员扩展属性：小程序类型的属性
 */
public class WeChatWorkExternalAttributeMiniProgramBO {
    /**
     * 小程序appid，必须是有在本企业安装授权的小程序，否则会被忽略
     */
    @JSONField(name = "appid")
    private String appId;

    /**
     * 小程序的页面路径
     */
    @JSONField(name = "pagepath")
    private String pagePath;

    /**
     * 小程序的展示标题,长度限制12个UTF8字符
     */
    private String title;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getPagePath() {
        return pagePath;
    }

    public void setPagePath(String pagePath) {
        this.pagePath = pagePath;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "WeChatWorkExternalAttributeMiniProgramBO{" +
                "appId='" + appId + '\'' +
                ", pagePath='" + pagePath + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
