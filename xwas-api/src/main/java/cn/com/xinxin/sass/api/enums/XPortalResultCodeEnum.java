package cn.com.xinxin.sass.api.enums;

import com.xinxinfinance.commons.api.result.basic.ResultCode;
import org.jetbrains.annotations.Nullable;

/**
 * Created by dengyunhui on 2018/5/21
 * @author dengyunhui
 **/
public enum XPortalResultCodeEnum implements ResultCode {
    SUCCESS("SUCCESS","操作成功","操作成功"),

    SYSTEM_ERROR("SYSTEM_ERROR","系统错误","系统错误"),

    ;

    private  String code;

    private  String message;

    private  String debugMessage;

    XPortalResultCodeEnum(String message) {
        this.message = message;
    }

    XPortalResultCodeEnum(String message, String debugMessage) {
        this.message = message;
        this.debugMessage = debugMessage;
    }

    XPortalResultCodeEnum(String code, String message, String debugMessage) {
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
