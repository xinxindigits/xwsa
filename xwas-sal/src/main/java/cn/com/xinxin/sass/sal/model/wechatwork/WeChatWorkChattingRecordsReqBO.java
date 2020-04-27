package cn.com.xinxin.sass.sal.model.wechatwork;

/**
 * @author: liuhangzhou
 * @created: 2020/4/26.
 * @updater:
 * @description: 企业微信聊天记录请求BO
 */
public class WeChatWorkChattingRecordsReqBO {
    /**
     * sdk
     */
    private Long sdk;

    /**
     * 获取消息的起始序列号
     */
    private Long startSeq;

    /**
     * 此次查询的条数
     */
    private Long limit;

    /**
     * 使用代理的请求，需要传入代理的链接
     */
    private String proxy;

    /**
     * 代理账号密码
     */
    private String passwd;

    /**
     * 超时时间
     */
    private Long timeout;

    public Long getSdk() {
        return sdk;
    }

    public void setSdk(Long sdk) {
        this.sdk = sdk;
    }

    public Long getStartSeq() {
        return startSeq;
    }

    public void setStartSeq(Long startSeq) {
        this.startSeq = startSeq;
    }

    public Long getLimit() {
        return limit;
    }

    public void setLimit(Long limit) {
        this.limit = limit;
    }

    public String getProxy() {
        return proxy;
    }

    public void setProxy(String proxy) {
        this.proxy = proxy;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public Long getTimeout() {
        return timeout;
    }

    public void setTimeout(Long timeout) {
        this.timeout = timeout;
    }

    @Override
    public String toString() {
        return "WeChatWorkChattingRecordsReqBO{" +
                "sdk=" + sdk +
                ", startSeq=" + startSeq +
                ", limit=" + limit +
                ", proxy='" + proxy + '\'' +
                ", passwd='" + passwd + '\'' +
                ", timeout=" + timeout +
                '}';
    }
}
