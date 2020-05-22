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

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author: liuhangzhou
 * @created: 2020/4/26.
 * @updater:
 * @description: 企业微信聊天记录内容
 */
public class WeChatWorkChatDataBO {

    /**
     * 消息的seq值，标识消息的序号
     */
    @JSONField(name = "seq")
    private Long sequenceNo;

    /**
     * 消息id，消息的唯一标识，企业可以使用此字段进行消息去重。String类型
     */
    @JSONField(name = "msgid")
    private String messageId;

    /**
     * 加密此条消息使用的公钥版本号。
     */
    @JSONField(name = "publickey_ver")
    private Integer publicKeyVersion;

    /**
     * 使用publickey_ver指定版本的公钥进行非对称加密后base64加密的内容，
     * 需要业务方先base64 decode处理后，再使用指定版本的私钥进行解密，得出内容。String类型
     */
    @JSONField(name = "encrypt_random_key")
    private String encryptRandomKey;

    /**
     * 消息密文。需要业务方使用将encrypt_random_key解密得到的内容，
     * 与encrypt_chat_msg，传入sdk接口DecryptData,得到消息明文。String类型
     */
    @JSONField(name = "encrypt_chat_msg")
    private String encryptChatMessage;

    public Long getSequenceNo() {
        return sequenceNo;
    }

    public void setSequenceNo(Long sequenceNo) {
        this.sequenceNo = sequenceNo;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public Integer getPublicKeyVersion() {
        return publicKeyVersion;
    }

    public void setPublicKeyVersion(Integer publicKeyVersion) {
        this.publicKeyVersion = publicKeyVersion;
    }

    public String getEncryptRandomKey() {
        return encryptRandomKey;
    }

    public void setEncryptRandomKey(String encryptRandomKey) {
        this.encryptRandomKey = encryptRandomKey;
    }

    public String getEncryptChatMessage() {
        return encryptChatMessage;
    }

    public void setEncryptChatMessage(String encryptChatMessage) {
        this.encryptChatMessage = encryptChatMessage;
    }

    @Override
    public String toString() {
        return "WeChatWorkChatDataBO{" +
                "sequenceNo=" + sequenceNo +
                ", messageId='" + messageId + '\'' +
                ", publicKeyVersion=" + publicKeyVersion +
                ", encryptRandomKey='" + encryptRandomKey + '\'' +
                ", encryptChatMessage='" + encryptChatMessage + '\'' +
                '}';
    }
}
