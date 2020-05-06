package cn.com.xinxin.sass.common.enums;

/**
 * @author: yanghaoxian
 * @created: 2020/4/30.
 * @updater:
 * @description:
 */
public enum ChatUserEnum {

    CUSTOMER("CUSTOMER","客户"),
    MEMBER("MEMBER","成员"),
    OTHER("OTHER","其他"),
    ;

    private String code;

    private String desc;

    ChatUserEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
