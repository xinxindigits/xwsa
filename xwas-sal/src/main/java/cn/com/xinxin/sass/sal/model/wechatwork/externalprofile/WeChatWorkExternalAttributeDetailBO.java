package cn.com.xinxin.sass.sal.model.wechatwork.externalprofile;

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

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author: liuhangzhou
 * @created: 2020/4/20.
 * @updater:
 * @description: 企业微信成员扩展属性
 */
public class WeChatWorkExternalAttributeDetailBO {

    /**
     * 属性类型: 0-文本 1-网页 2-小程序
     */
    private Integer type;

    /**
     * 属性名称： 需要先确保在管理端有创建该属性，否则会忽略
     */
    private String name;

    /**
     * 文本类型的属性
     */
    @JSONField(name = "text")
    private WeChatWorkExternalAttributeTextBO textBO;

    /**
     * 网页类型的属性，url和title字段要么同时为空表示清除该属性，要么同时不为空
     */
    @JSONField(name = "web")
    private WeChatWorkExternalAttributeWebBO webBO;

    /**
     * 小程序类型的属性，appid和title字段要么同时为空表示清除改属性，要么同时不为空
     */
    @JSONField(name = "miniprogram")
    private WeChatWorkExternalAttributeMiniProgramBO miniProgramBO;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public WeChatWorkExternalAttributeTextBO getTextBO() {
        return textBO;
    }

    public void setTextBO(WeChatWorkExternalAttributeTextBO textBO) {
        this.textBO = textBO;
    }

    public WeChatWorkExternalAttributeWebBO getWebBO() {
        return webBO;
    }

    public void setWebBO(WeChatWorkExternalAttributeWebBO webBO) {
        this.webBO = webBO;
    }

    public WeChatWorkExternalAttributeMiniProgramBO getMiniProgramBO() {
        return miniProgramBO;
    }

    public void setMiniProgramBO(WeChatWorkExternalAttributeMiniProgramBO miniProgramBO) {
        this.miniProgramBO = miniProgramBO;
    }

    @Override
    public String toString() {
        return "WeChatWorkExternalAttributeDetailBO{" +
                "type=" + type +
                ", name='" + name + '\'' +
                ", textBO=" + textBO +
                ", webBO=" + webBO +
                ", miniProgramBO=" + miniProgramBO +
                '}';
    }
}
