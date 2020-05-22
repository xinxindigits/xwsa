package cn.com.xinxin.sass.api.enums;

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

import com.xinxinfinance.commons.api.result.basic.ResultCode;
import org.jetbrains.annotations.Nullable;

/**
 * Created by dengyunhui on 2018/5/21
 * @author dengyunhui
 **/
public enum SassResultCodeEnum implements ResultCode {
    SUCCESS("SUCCESS","操作成功","操作成功"),

    SYSTEM_ERROR("SYSTEM_ERROR","系统错误","系统错误"),

    ;

    private  String code;

    private  String message;

    private  String debugMessage;

    SassResultCodeEnum(String message) {
        this.message = message;
    }

    SassResultCodeEnum(String message, String debugMessage) {
        this.message = message;
        this.debugMessage = debugMessage;
    }

    SassResultCodeEnum(String code, String message, String debugMessage) {
        this.code = code;
        this.message = message;
        this.debugMessage = debugMessage;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public @Nullable String getMessage() {
        return this.message;
    }

    @Override
    public @Nullable String getDebugMessage() {
        return this.debugMessage;
    }
}
