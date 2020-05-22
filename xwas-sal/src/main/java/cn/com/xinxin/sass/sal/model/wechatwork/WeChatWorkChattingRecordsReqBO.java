package cn.com.xinxin.sass.sal.model.wechatwork;

/*
 *
 * Copyright 2020 www.xinxindigits.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software
 * and associated documentation files (the "Software"),to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice
 * shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 * Redistribution and selling copies of the software are prohibited, only if the authorization from xinxin digits
 * was obtained.Neither the name of the xinxin digits; nor the names of its contributors may be used to
 * endorse or promote products derived from this software without specific prior written permission.
 *
 */

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
