package cn.com.xinxin.sass.common.enums;

/**
 * @author: liuhangzhou
 * @created: 2020/4/27.
 * @updater:
 * @description: 任务错误码
 */
public enum  TaskErrorEnum {
    RECEIVE_EXCEPTION("RECEIVE_EXCEPTION", "获取异常"),
    IMPORTING_EXCEPTION("IMPORTING_EXCEPTION", "导入异常"),
    ;

    private String errorCode;
    private String errorDesc;

    TaskErrorEnum(String errorCode, String errorDesc) {
        this.errorCode = errorCode;
        this.errorDesc = errorDesc;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorDesc() {
        return errorDesc;
    }
}
