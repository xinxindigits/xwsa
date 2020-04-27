package cn.com.xinxin.sass.common.enums;

/**
 * @author: zhouyang
 * @created: 17/04/2020.
 * @updater:
 * @description: 性别类型枚举
 */
public enum  GenderTypeEnums {


    MALE("MALE","男性",1),

    FEMALE("FEMALE","女性",2),

    UNKNOWN("UNKNOWN","未知",0),

    ;


    private String code;

    private String desc;

    private Integer number;

    GenderTypeEnums(String code, String desc, Integer number) {
        this.code = code;
        this.desc = desc;
        this.number = number;
    }

    /**
     * 根据value获取对应的枚举。
     *
     * @param value 枚举值
     * @return <value>FilmServiceErrors</value>
     */
    public static GenderTypeEnums getEnumByCode(final String value) {


        // value为空，返回null
        if (value == null || value.isEmpty()) {
            return null;
        }

        // 遍历
        for (final GenderTypeEnums errEnum : values()) {
            if (value.equals(String.valueOf(errEnum.getCode()))) {
                return errEnum;
            }
        }
        // 找不到匹配的，返回null
        return null;
    }

    public static GenderTypeEnums getEnumByNum(final String value) {


        // value为空，返回null
        if (value == null || value.isEmpty()) {
            return null;
        }

        // 遍历
        for (final GenderTypeEnums errEnum : values()) {
            if (value.equals(String.valueOf(errEnum.getNumber()))) {
                return errEnum;
            }
        }
        // 找不到匹配的，返回null
        return null;
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }
}
