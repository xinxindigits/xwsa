package cn.com.xinxin.portal.web.convert;

import cn.com.xinxin.portal.repository.model.*;
import cn.com.xinxin.portal.web.form.*;
import com.google.common.primitives.Bytes;
import com.xinxinfinance.commons.util.BaseConvert;

import java.lang.reflect.Field;
import java.util.List;

/**
 * @author: zhouyang
 * @created: 13/07/2018.
 * @updater:
 * @description: 组织转换类
 */
public class PortalFormConvert {

    /**
     * 组织类型转换方法
     * @param organizationForm
     * @return
     */
    public static OrganizationDO convertOrgForm2OrganizationDO(final OrganizationForm organizationForm){

        OrganizationDO  organizationDO = BaseConvert.convert(organizationForm, OrganizationDO.class);

        return organizationDO;

    }

    /**
     * 表单转换方法
     * @param resourceForm
     * @return
     */
    public static ResourceDO convertResourceForm2ResourceDO(final ResourceForm resourceForm){

        ResourceDO resourceDO = BaseConvert.convert(resourceForm, ResourceDO.class);

        return resourceDO;
    }


    /**
     *
     * @param userForm
     * @return
     */
    public static UserDO convertUserForm2UserDO(final UserForm userForm){
        UserDO userDO = BaseConvert.convert(userForm, UserDO.class);
        userDO.setGender(userForm.getGender());
        return userDO;
    }

    /**
     *
     * @param roleResourceForm
     * @return
     */
    public static RoleResourceDO convertRRForm2RoleResourceDO(final RoleResourceForm roleResourceForm){

        RoleResourceDO  roleResourceDO = BaseConvert.convert(roleResourceForm, RoleResourceDO.class);

        return  roleResourceDO;

    }

    /**
     *
     * @param resourceFormList
     * @return
     */
    public static List<RoleResourceDO> convertRRForm2RoleResourceDOList(final List<RoleResourceForm> resourceFormList){

        List<RoleResourceDO> roleResourceDOList = BaseConvert.convertList(resourceFormList, RoleResourceDO.class);
        return roleResourceDOList;
    }


    /**
     *
     * @param userRoleForm
     * @return
     */
    public static UserRoleDO convertUserRoleForm2UserRoleDO(final UserRoleForm userRoleForm){

        UserRoleDO userRoleDO = BaseConvert.convert(userRoleForm, UserRoleDO.class);

        return userRoleDO;
    }

}
