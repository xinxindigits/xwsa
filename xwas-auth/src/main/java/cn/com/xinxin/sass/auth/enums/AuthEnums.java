package cn.com.xinxin.sass.auth.enums;


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
    SASS_RESOURCE_MENU_QUERY_LIST("SASS_RESOURCE_MENU_QUERY_LIST","菜单查询以及列表","菜单查询以及列表","/resource/tree"),

    SASS_RESOURCE_MENU_FUNCTION_ADD("SASS_RESOURCE_MENU_FUNCTION_ADD","资源权限添加","资源权限添加","/resource/create"),

    SASS_RESOURCE_MENU_FUNCTION_REMOVE("SASS_RESOURCE_MENU_FUNCTION_REMOVE","资源权限删除","资源权限删除","/resource/delete"),

    SASS_RESOURCE_MENU_FUNCTION_UPDATE("SASS_RESOURCE_MENU_FUNCTION_UPDATE","资源权限修改","资源权限修改","/resource/update"),

    SASS_RESOURCE_MENU_FUNCTION_MNG("SASS_RESOURCE_MENU_FUNCTION_MNG","资源权限管理","资源权限管理","/resource/list"),


    /**
     * 组织机构管理功能列表
     */
    SASS_ORG_QUERY_LIST("SASS_ORG_QUERY_LIST","组织机构查询以及列表功能","组织机构查询以及列表功能","/organization/list"),

    SASS_ORG_ADD("SASS_ORG_ADD","组织机构新增权限","组织机构新增权限","/organization/create"),

    SASS_ORG_UPDATE("SASS_ORG_UPDATE","组织机构修改权限","组织机构修改权限","/organization/update"),

    SASS_ORG_DELETE("SASS_ORG_DELETE","组织机构删除权限","组织机构删除权限","/organization/delete"),

    SASS_ORG_MNG("SASS_ORG_MNG","组织机构管理","组织机构管理","/organization/list"),


    /**
     * 角色权限管理功能
     */
    SASS_ROLE_QUERY_LIST("SASS_ROLE_QUERY_LIST","角色查询列表权限","角色查询列表权限","/role/query"),

    SASS_ROLE_ADD("SASS_ROLE_ADD","角色新增权限","角色权限新增","/role/create"),

    SASS_ROLE_UPDATE("SASS_ROLE_UPDATE","角色修改权限","角色修改权限","/role/update"),

    SASS_ROLE_DELETE("SASS_ROLE_DELETE","角色删除权限","角色删除权限","/role/delete"),

    SASS_ROLE_MNG("SASS_ROLE_MNG","角色管理权限","角色管理权限","/role/list"),

    SASS_ROLE_GRANT_RESOURCE("SASS_ROLE_GRANT_RESOURCE","角色授权资源权限","角色授权资源权限","/role/resource/grant"),

    SASS_ROLE_GRANT_USER("SASS_ROLE_GRANT_USER","角色授权用户权限","角色授权用户权限","/role/user/grant"),


    /**
     * 标签管理功能
     */
    SASS_TAG_QUERY_LIST("SASS_TAG_QUERY_LIST","标签查询列表权限","标签查询列表权限","/tags/query"),

    SASS_TAG_ADD("SASS_TAG_ADD","标签新增权限","标签新增权限","/tags/create"),

    SASS_TAG_UPDATE("SASS_TAG_UPDATE","标签修改权限","标签修改权限","/tags/update"),

    SASS_TAG_DELETE("SASS_TAG_DELETE","标签删除权限","标签删除权限","/tags/delete"),

    SASS_TAG_MNG("SASS_TAG_MNG","标签管理功能","标签管理功能","/tags/list"),

    SASS_TAG_FIX_DATA("SASS_TAG_FIX_DATA","给数据上标签","给数据上标签","/tags/fixdata"),


    /**
     * 租户管理功能
     */
    SASS_TENANT_QUERY_LIST("SASS_TENANT_QUERY_LIST","租户查询列表权限","租户查询列表权限","/tenant/query"),

    SASS_TENANT_ADD("SASS_TENANT_ADD","租户新增权限","租户新增权限","/tenant/create"),

    SASS_TENANT_UPDATE("SASS_TENANT_UPDATE","租户修改权限","租户修改权限","/tenant/update"),

    SASS_TENANT_DELETE("SASS_TENANT_DELETE","租户删除权限","租户权限删除","/tenant/delete"),

    SASS_TENANT_MNG("SASS_TENANT_MNG","租户管理权限","租户管理","/tenant/list"),

    SASS_TENANT_TASK_CONFIG_MNG("SASS_TENANT_TASK_CONFIG_MNG","租户定时任务配置管理","租户定时任务配置管理","/tenant/queryConfig"),


    /**
     * 用户管理功能
     */
    SASS_USER_QUERY_LIST("SASS_USER_QUERY_LIST","用户查询列表权限","用户查询列表权限","/user/query"),

    SASS_USER_ADD("SASS_USER_ADD","用户新增权限","用户新增权限","/user/create"),

    SASS_USER_UPDATE("SASS_USER_UPDATE","用户修改权限","用户修改权限","/user/update"),

    SASS_USER_DELETE("SASS_USER_DELETE","用户删除权限","用户删除权限","/user/delete"),

    SASS_USER_MNG("SASS_USER_MNG","用户管理功能","用户管理功能","/user/list"),

    SASS_USER_ROLE_GRANT("SASS_USER_ROLE_GRANT","用户角色授权","用户角色授权","/user/grant"),

    SASS_USER_OPLOG_MNG("SASS_USER_OPLOG_MNG","用户操作日志记录管理","用户操作日志记录管理","/ops/log/list"),

    SASS_USER_OPLOG_QUERY("SASS_USER_OPLOG_QUERY","用户操作日志记录查询","用户操作日志记录查询","/ops/log/query"),

    SASS_USER_PASSWORD_REST("SASS_USER_PASSWORD_REST","重置用户密码","重置用户密码","/user/restpwd"),


    /**
     * 微信部门管理
     */
    SASS_WEXIN_DEPT_MNG("SASS_WEXIN_DEPT_MNG","企业微信部门管理权限","企业微信部门管理权限","/wechat/dept/list"),

    SASS_WEXIN_STAFF_MNG("SASS_WEXIN_STAFF_MNG","企业微信员工管理权限","企业微信员工管理权限","/wechat/staff/list"),

    SASS_WEXIN_CUSTOMER_MNG("SASS_WEXIN_CUSTOMER_MNG","企业微信客户管理权限","企业微信客户管理权限","/wechat/customer/list"),

    SASS_WEXIN_MSG_MNG("SASS_WEXIN_MSG_MNG","企业微信消息管理权限","企业微信消息管理权限","/wechat/message/list"),
    ;

    private String code;

    private String name;

    private String desc;

    private String url;

    AuthEnums(String code,
              String name,
              String desc,
              String url) {
        this.code = code;
        this.name = name;
        this.desc = desc;
        this.url = url;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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
