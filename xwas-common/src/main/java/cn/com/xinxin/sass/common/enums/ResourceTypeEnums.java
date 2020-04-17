package cn.com.xinxin.sass.common.enums;

import org.apache.commons.lang3.EnumUtils;

/**
 * @author: zhouyang
 * @created: 17/04/2020.
 * @updater:
 * @description: 对应的资源类型枚举
 */
public enum  ResourceTypeEnums {



    FUNCTION_TYPE("function","功能类资源类型"),

    MENU_TYPE("menu","菜单类资源类型")


    ;


    private String code;

    private String desc;


    ResourceTypeEnums(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }


    /**
     * 根据value获取对应的枚举。
     *
     * @param value 枚举值
     * @return <value>FilmServiceErrors</value>
     */
    static ResourceTypeEnums getEnumByCode(final String value) {


        // value为空，返回null
        if (value == null || value.isEmpty()) {
            return null;
        }

        // 遍历
        for (final ResourceTypeEnums errEnum : values()) {
            if (value.equals(String.valueOf(errEnum.getCode()))) {
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



}
