package cn.com.xinxin.sass.auth.enums;

/**
 * @author: zhouyang
 * @created: 15/05/2020.
 * @updater:
 * @description: 权限值的枚举类
 */
public enum AuthEnums {

    SASS_RESOURCE_MENU_QUERY_LIST("SASS_RESOURCE_MENU_QUERY_LIST","菜单查询以及列表","菜单查询以及列表"),

    SASS_RESOURCE_MENU_FUNCTION_ADD("SASS_RESOURCE_MENU_FUNCTION_ADD","资源权限添加","资源权限添加"),

    SASS_RESOURCE_MENU_FUNCTION_REMOVE("SASS_RESOURCE_MENU_FUNCTION_REMOVE","资源权限删除","资源权限删除"),

    SASS_RESOURCE_MENU_FUNCTION_UPDATE("SASS_RESOURCE_MENU_FUNCTION_UPDATE","资源权限修改","资源权限修改");

    private String code;

    private String name;

    private String desc;

    AuthEnums(String code, String name, String desc) {
        this.code = code;
        this.name = name;
        this.desc = desc;
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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
