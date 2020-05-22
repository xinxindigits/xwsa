package com.tencent.wework;

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

import cn.com.xinxin.sass.common.constants.CommonConstants;
import cn.com.xinxin.sass.sal.model.wechatwork.WeChatWorkChattingRecordsBO;
import cn.com.xinxin.sass.sal.model.wechatwork.WeChatWorkChattingRecordsReqBO;
import cn.com.xinxin.sass.sal.model.wechatwork.response.WeChatWorkChattingRecordsResponseBO;
import com.alibaba.fastjson.JSON;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;

/**
 * @author: liuhangzhou
 * @created: 2020/4/26.
 * @updater:
 * @description: 企业微信聊天记录工具类
 */
public class ChattingRecordsUtils {

    /**
     * 获取聊天记录
     * @param reqBO 请求参数
     * @return 聊天记录
     */
    public static WeChatWorkChattingRecordsResponseBO fetchChattingRecords(WeChatWorkChattingRecordsReqBO reqBO) {
        long slice = Finance.NewSlice();
        Finance.GetChatData(reqBO.getSdk(), reqBO.getStartSeq(), reqBO.getLimit(), reqBO.getProxy(), reqBO.getPasswd(),
                reqBO.getTimeout(), slice);
        return JSON.parseObject(
                Finance.GetContentFromSlice(slice), WeChatWorkChattingRecordsResponseBO.class);
    }

    /**
     * 初始化sdk
     * @param corporationId 公司corporationId
     * @param secret 会话记录应用secret
     * @return sdk
     */
    public static long initSdk(String corporationId, String secret) {
        long sdk = Finance.NewSdk();
        Finance.Init(sdk, corporationId, secret);
        return sdk;
    }

    /**
     * 销毁sdk
     * @param sdk sdk
     */
    public static void destorySdk(long sdk) {
        Finance.DestroySdk(sdk);
    }

    /**
     * 解密消息
     * @param sdk sdk
     * @param privateKey 私钥
     * @param encryptRandomKey 被私钥加密的密钥
     * @param encryptChatMessage 加密消息
     * @return 解密后的消息
     * @throws NoSuchPaddingException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     * @throws UnsupportedEncodingException
     * @throws InvalidKeyException
     */
    public static WeChatWorkChattingRecordsBO decryptMessage(Long sdk, String privateKey, String encryptRandomKey,
                                                             String encryptChatMessage) throws
            NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeySpecException, BadPaddingException,
            IllegalBlockSizeException, UnsupportedEncodingException, InvalidKeyException {
        long slice = Finance.NewSlice();
        Cipher rsa = Cipher.getInstance(CommonConstants.RSA_PKCS1);
        rsa.init(Cipher.DECRYPT_MODE, getPrivateKey(privateKey, CommonConstants.RSA));
        byte[] data = rsa.doFinal(Base64.decodeBase64(encryptRandomKey));
        String result = new String(data, CommonConstants.ENCODEING_UTF8);
        Finance.DecryptData(sdk, result, encryptChatMessage, slice);
        return JSON.parseObject(Finance.GetContentFromSlice(slice),
                WeChatWorkChattingRecordsBO.class);
    }

    /**
     * 将字符串转化为私钥
     * @param key 私钥
     * @param algorithm 非对称算法
     * @return {@link PrivateKey} 私钥
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    private static PrivateKey getPrivateKey(String key, String algorithm)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] byteKey = java.util.Base64.getDecoder().decode(key);
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(byteKey);
        KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
        return keyFactory.generatePrivate(pkcs8EncodedKeySpec);
    }
}
