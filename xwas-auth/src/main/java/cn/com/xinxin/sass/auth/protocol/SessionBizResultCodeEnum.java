package cn.com.xinxin.sass.auth.protocol;


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

import com.xinxinfinance.commons.result.BizResultCode;

import javax.annotation.Nullable;

/**
 * Created by helloyy on 12/02/2018.
 */
public enum SessionBizResultCodeEnum implements BizResultCode {

    // 公用
    SUCCESS("SUCCESS", "成功", "成功"),

    FAIL("FAIL","处理失败","处理失败"),

    SYSTEM_ERROR("SYSTEM_ERROR", "系统异常", "系统繁忙，请稍后再试!"),


    INVALID_SESSION_ID("NTC00005", "session为空", "session为空，请登录操作!"),

    INVALID_TOKEN("INVALID_TOKEN", "无效登录口令", "无效登录口令!"),

    NO_PERMISSION("NO_PERMISSION", "无权限操作", "无权限操作!"),

    AUTHENTICATE_FAIL("AUTHENTICATE_FAIL", "登录授权失败", "登录授权失败,请登录操作!"),

    /* Enum end */;


    /**
     * 错误码
     */
    private final String code;

    /**
     * 错误描述
     */
    private final String debugMessage;

    /**
     * 前端提示文案
     */
    private final String alertMessage;


    /**
     *
     * @param debugMessage
     * @param alertMessage
     */
    SessionBizResultCodeEnum(String debugMessage, String alertMessage) {
        this.code = this.name();
        this.debugMessage = debugMessage;
        this.alertMessage = alertMessage;
    }

    /**
     * 构造
     *
     * @param alertMessage   前端提示文案
     */
    SessionBizResultCodeEnum(final String alertMessage) {
        this.code = this.name();
        this.debugMessage = alertMessage;
        this.alertMessage = alertMessage;
    }

    /**
     *
     * @param code
     * @param debugMessage
     * @param alertMessage
     */
    SessionBizResultCodeEnum(String code, String debugMessage, String alertMessage) {
        this.code = code;
        this.debugMessage = debugMessage;
        this.alertMessage = alertMessage;
    }

    /**
     * 根据value获取对应的枚举。
     *
     * @param value 枚举值
     * @return <value>FilmServiceErrors</value>
     */
    static SessionBizResultCodeEnum getEnumByCode(final String value) {

        // value为空，返回null
        if (value == null || value.isEmpty()) {
            return null;
        }

        // 遍历
        for (final SessionBizResultCodeEnum errEnum : values()) {
            if (value.equals(String.valueOf(errEnum.getCode()))) {
                return errEnum;
            }
        }
        // 找不到匹配的，返回null
        return null;
    }

    // region: adaptor


    // endregion: adaptor



    public String getMessage(){
        return this.alertMessage;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public @Nullable
    String getAlertMessage() {
        return this.alertMessage;
    }

    @Override
    public @Nullable String getDebugMessage() {
        return this.debugMessage;
    }
}
