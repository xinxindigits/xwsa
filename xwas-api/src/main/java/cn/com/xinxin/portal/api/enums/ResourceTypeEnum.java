package cn.com.xinxin.portal.api.enums;

/**
 * Created by dengyunhui on 2018/5/1
 **/
public enum  ResourceTypeEnum {

    MENU("MENU","菜单"),

    FUNCTION("FUNCTION","功能");

    private String code;

    private String name;

    ResourceTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    /**
     * 根据value获取对应的枚举。
     *
     * @param value 枚举值
     * @return <value>FilmServiceErrors</value>
     */
    static ResourceTypeEnum getEnumByCode(final String value) {

        // value为空，返回null
        if (value == null || value.isEmpty()) {
            return null;
        }

        // 遍历
        for (final ResourceTypeEnum errEnum : values()) {
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
