package cn.com.xinxin.sass.auth.enums;

import org.apache.commons.lang3.EnumUtils;

import java.util.List;

/**
 * @author: zhouyang
 * @created: 15/05/2020.
 * @updater:
 * @description: 权限值的枚举类
 */
public enum AuthEnums {

    /**
     * 资源权限管理列表功能
     */
    SASS_RESOURCE_MENU_QUERY_LIST("SASS_RESOURCE_MENU_QUERY_LIST","菜单查询以及列表","菜单查询以及列表"),

    SASS_RESOURCE_MENU_FUNCTION_ADD("SASS_RESOURCE_MENU_FUNCTION_ADD","资源权限添加","资源权限添加"),

    SASS_RESOURCE_MENU_FUNCTION_REMOVE("SASS_RESOURCE_MENU_FUNCTION_REMOVE","资源权限删除","资源权限删除"),

    SASS_RESOURCE_MENU_FUNCTION_UPDATE("SASS_RESOURCE_MENU_FUNCTION_UPDATE","资源权限修改","资源权限修改"),

    SASS_RESOURCE_MENU_FUNCTION_MNG("SASS_RESOURCE_MENU_FUNCTION_MNG","资源权限管理","资源权限管理"),


    /**
     * 组织机构管理功能列表
     */
    SASS_ORG_QUERY_LIST("SASS_ORG_QUERY_LIST","组织机构查询以及列表功能","组织机构查询以及列表功能"),

    SASS_ORG_ADD("SASS_ORG_ADD","组织机构新增权限","组织机构新增权限"),

    SASS_ORG_UPDATE("SASS_ORG_UPDATE","组织机构修改权限","组织机构修改权限"),

    SASS_ORG_DELETE("SASS_ORG_DELETE","组织机构删除权限","组织机构删除权限"),

    SASS_ORG_MNG("SASS_ORG_MNG","组织机构管理","组织机构管理"),


    /**
     * 角色权限管理功能
     */
    SASS_ROLE_QUERY_LIST("SASS_ROLE_QUERY_LIST","角色查询列表权限","角色查询列表权限"),

    SASS_ROLE_ADD("SASS_ROLE_ADD","角色新增权限","角色权限新增"),

    SASS_ROLE_UPDATE("SASS_ROLE_UPDATE","角色修改权限","角色修改权限"),

    SASS_ROLE_DELETE("SASS_ROLE_DELETE","角色删除权限","角色删除权限"),

    SASS_ROLE_MNG("SASS_ROLE_MNG","角色管理权限","角色管理权限"),

    SASS_ROLE_GRANT_RESOURCE("SASS_ROLE_GRANT_RESOURCE","角色授权资源权限","角色授权资源权限"),

    SASS_ROLE_GRANT_USER("SASS_ROLE_GRANT_USER","角色授权用户权限","角色授权用户权限"),


    /**
     * 标签管理功能
     */
    SASS_TAG_QUERY_LIST("SASS_TAG_QUERY_LIST","标签查询列表权限","标签查询列表权限"),

    SASS_TAG_ADD("SASS_TAG_ADD","标签新增权限","标签新增权限"),

    SASS_TAG_UPDATE("SASS_TAG_UPDATE","标签修改权限","标签修改权限"),

    SASS_TAG_DELETE("SASS_TAG_DELETE","标签删除权限","标签删除权限"),

    SASS_TAG_MNG("SASS_TAG_MNG","标签管理功能","标签管理功能"),

    SASS_TAG_FIX_DATA("SASS_TAG_FIX_DATA","给数据上标签","给数据上标签"),


    /**
     * 租户管理功能
     */
    SASS_TENANT_QUERY_LIST("SASS_TENANT_QUERY_LIST","租户查询列表权限","租户查询列表权限"),

    SASS_TENANT_ADD("SASS_TENANT_ADD","租户新增权限","租户新增权限"),

    SASS_TENANT_UPDATE("SASS_TENANT_UPDATE","租户修改权限","租户修改权限"),

    SASS_TENANT_DELETE("SASS_TENANT_DELETE","租户删除权限","租户权限删除"),

    SASS_TENANT_MNG("SASS_TENANT_MNG","租户管理权限","租户管理"),

    SASS_TENANT_TASK_CONFIG_MNG("SASS_TENANT_TASK_CONFIG_MNG","租户定时任务配置管理","租户定时任务配置管理"),


    /**
     * 用户管理功能
     */
    SASS_USER_QUERY_LIST("SASS_USER_QUERY_LIST","用户查询列表权限","用户查询列表权限"),

    SASS_USER_ADD("SASS_USER_ADD","用户新增权限","用户新增权限"),

    SASS_USER_UPDATE("SASS_USER_UPDATE","用户修改权限","用户修改权限"),

    SASS_USER_DELETE("SASS_USER_DELETE","用户删除权限","用户删除权限"),

    SASS_USER_MNG("SASS_USER_MNG","用户管理功能","用户管理功能"),

    SASS_USER_ROLE_GRANT("SASS_USER_ROLE_GRANT","用户角色授权","用户角色授权"),

    SASS_USER_OPLOG_MNG("SASS_USER_OPLOG_MNG","用户操作日志记录管理","用户操作日志记录管理"),

    SASS_USER_OPLOG_QUERY("SASS_USER_OPLOG_QUERY","用户操作日志记录查询","用户操作日志记录查询"),

    SASS_USER_PASSWORD_REST("SASS_USER_PASSWORD_REST","重置用户密码","重置用户密码"),


    /**
     * 微信部门管理
     */
    SASS_WEXIN_DEPT_MNG("SASS_WEXIN_DEPT_MNG","企业微信部门管理权限","企业微信部门管理权限"),

    SASS_WEXIN_STAFF_MNG("SASS_WEXIN_STAFF_MNG","企业微信员工管理权限","企业微信员工管理权限"),

    SASS_WEXIN_CUSTOMER_MNG("SASS_WEXIN_CUSTOMER_MNG","企业微信客户管理权限","企业微信客户管理权限"),

    SASS_WEXIN_MSG_MNG("SASS_WEXIN_MSG_MNG","企业微信消息管理权限","企业微信消息管理权限"),
    ;

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

    /**
     *
     * 根据value获取对应的枚举。
     *
     * @param value 枚举值
     * @return <value>FilmServiceErrors</value>
     */
    static AuthEnums getEnumByCode(final String value) {

        // value为空，返回null
        if (value == null || value.isEmpty()) {
            return null;
        }

        // 遍历
        for (final AuthEnums errEnum : values()) {
            if (value.equals(String.valueOf(errEnum.getCode()))) {
                return errEnum;
            }
        }
        // 找不到匹配的，返回null
        return null;
    }


    public static List<AuthEnums> toLists(){
        // 转换为list
        return EnumUtils.getEnumList(AuthEnums.class);
    }



}
