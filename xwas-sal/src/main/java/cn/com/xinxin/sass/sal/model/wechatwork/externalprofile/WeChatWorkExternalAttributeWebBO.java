package cn.com.xinxin.sass.sal.model.wechatwork.externalprofile;

/**
 * @author: liuhangzhou
 * @created: 2020/4/20.
 * @updater:
 * @description: 企业微信成员扩展属性：网页类型的属性
 */
public class WeChatWorkExternalAttributeWebBO {
    /**
     * 网页的url,必须包含http或者https头
     */
    private String url;

    /**
     * 网页的展示标题,长度限制12个UTF8字符
     */
    private String title;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "WeChatWorkExternalAttributeWebBO{" +
                "url='" + url + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
