package cn.com.xinxin.sass.web.rest;

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

import cn.com.xinxin.sass.auth.model.SassUserInfo;
import cn.com.xinxin.sass.auth.web.AclController;
import cn.com.xinxin.sass.biz.log.SysLog;
import cn.com.xinxin.sass.biz.service.ResourceService;
import cn.com.xinxin.sass.biz.service.RoleResourceService;
import cn.com.xinxin.sass.biz.service.RoleService;
import cn.com.xinxin.sass.biz.service.UserRoleService;
import cn.com.xinxin.sass.biz.service.UserService;
import cn.com.xinxin.sass.common.enums.SassBizResultCodeEnum;
import cn.com.xinxin.sass.common.model.PageResultVO;
import cn.com.xinxin.sass.repository.model.ResourceDO;
import cn.com.xinxin.sass.repository.model.RoleDO;
import cn.com.xinxin.sass.repository.model.RoleResourceDO;
import cn.com.xinxin.sass.repository.model.UserDO;
import cn.com.xinxin.sass.repository.model.UserRoleDO;
import cn.com.xinxin.sass.web.convert.SassFormConvert;
import cn.com.xinxin.sass.web.form.*;
import cn.com.xinxin.sass.web.utils.RegexUtils;
import cn.com.xinxin.sass.web.utils.TreeResultUtil;
import cn.com.xinxin.sass.web.vo.MenuTreeVO;
import cn.com.xinxin.sass.web.vo.ResourceVO;
import cn.com.xinxin.sass.web.form.*;
import cn.com.xinxin.sass.web.vo.RoleVO;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.xinxinfinance.commons.exception.BusinessException;
import com.xinxinfinance.commons.result.BizResultCode;
import com.xinxinfinance.commons.util.BaseConvert;
import org.apache.ibatis.annotations.Param;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author: zhouyang
 * @created: 17/04/2020.
 * @updater:
 * @description: 权限相关的角色管理接口
 */
@RestController
@RequestMapping(value = "/role",produces = "application/json; charset=UTF-8")
public class SassRoleRestController extends AclController {

    private static final Logger logger = LoggerFactory.getLogger(SassRoleRestController.class);

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private RoleResourceService roleResourceService;

    @Autowired
    private ResourceService resourceService;

    @Autowired
    private UserService userService;
    /**
     * 创建角色接口
     * @param createRoleForm
     * @param request
     * @return
     */
    @RequestMapping(value = "/create",method = RequestMethod.POST)
    @RequiresPermissions(value = {"SASS_ROLE_MNG", "SASS_ROLE_ADD"},logical= Logical.OR)
    @SysLog("创建角色操作")
    public Object createRole(@RequestBody CreateRoleForm createRoleForm, HttpServletRequest request){

        if(createRoleForm == null){
            throw new BusinessException(SassBizResultCodeEnum.ILLEGAL_PARAMETER,"创建角色参数不能为空");
        }

        logger.info("--------SassRoleRestController.createRole.Request:{}--------",JSONObject.toJSONString(createRoleForm));
        String roleCode = createRoleForm.getCode();

        if(!RegexUtils.isDataCode(roleCode)){
            // 如果匹配不是useraccount格式
            throw new BusinessException(SassBizResultCodeEnum.ILLEGAL_PARAMETER,
                    "编码不能包含特殊字符或者长度超过16","编码不能包含特殊字符或者长度超过16");
        }

        RoleDO existedRole = roleService.findByRoleCode(roleCode);
        if(existedRole != null){
            throw new BusinessException(SassBizResultCodeEnum.DATA_ALREADY_EXIST,"角色信息已经存在","角色信息已经存在");
        }

        RoleDO roleDO = BaseConvert.convert(createRoleForm, RoleDO.class);
        SassUserInfo sassUserInfo = this.getSassUser(request);
        roleDO.setGmtCreator(sassUserInfo.getAccount());
        roleDO.setGmtUpdater(sassUserInfo.getAccount());
        roleDO.setTenantId(sassUserInfo.getTenantId());

        try {
            roleService.createRole(roleDO, createRoleForm.getResourceList());
            return SassBizResultCodeEnum.SUCCESS.getAlertMessage();
        }catch (DuplicateKeyException dex){
            throw new BusinessException(SassBizResultCodeEnum.ILLEGAL_PARAMETER, "编码不能重复","编码不能重复");
        }catch (Exception ex){
            throw new BusinessException(SassBizResultCodeEnum.FAIL, "处理异常，请稍后重试","处理异常，请稍后重试");

        }

    }

    /**
     * 永久删除角色接口
     * @param deleteRoleForm
     * @param request
     * @return
     */
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    @RequiresPermissions(value = {"SASS_ROLE_MNG", "SASS_ROLE_DELETE"},logical= Logical.OR)
    @SysLog("删除角色操作")
    public Object deleteRole(@RequestBody DeleteRoleForm deleteRoleForm, HttpServletRequest request){

        if(deleteRoleForm == null || CollectionUtils.isEmpty(deleteRoleForm.getRoleCodes())){
            throw new BusinessException(SassBizResultCodeEnum.ILLEGAL_PARAMETER,"角色编码不能为空");
        }
        logger.info("--------SassRoleRestController.deleteRole.Request:{}--------",JSONObject.toJSONString(deleteRoleForm));

        roleService.deleteRoles(deleteRoleForm.getRoleCodes());

        return SassBizResultCodeEnum.SUCCESS.getAlertMessage();
    }


    /**
     * 更新角色接口
     * @param roleForm
     * @param request
     * @return
     */
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    @RequiresPermissions(value = {"SASS_ROLE_MNG", "SASS_ROLE_UPDATE"},logical= Logical.OR)
    @SysLog("更新角色操作")
    public Object updateRole(@RequestBody RoleForm roleForm, HttpServletRequest request){

        if(roleForm == null){
            throw new BusinessException(SassBizResultCodeEnum.ILLEGAL_PARAMETER,"更新角色参数不能为空");
        }
        logger.info("--------SassRoleRestController.updateRole.Request:{}--------",JSONObject.toJSONString(roleForm));

        RoleDO roleDO = SassFormConvert.convertRoleForm2RoleDO(roleForm);
        SassUserInfo sassUserInfo = this.getSassUser(request);
        roleDO.setGmtUpdater(sassUserInfo.getAccount());
        roleService.updateRole(roleDO);

        return SassBizResultCodeEnum.SUCCESS.getAlertMessage();
    }

    /**
     * 根据角色id查询接口
     * @param roleId
     * @param request
     * @return
     */
    @RequestMapping(value = "/query/{roleId}",method = RequestMethod.GET)
    @ResponseBody
    @RequiresPermissions(value = {"SASS_ROLE_MNG", "SASS_ROLE_QUERY_LIST"},logical= Logical.OR)
    public Object queryRoleById(@PathVariable Long roleId, HttpServletRequest request){

        if(roleId == null){
            throw new BusinessException(SassBizResultCodeEnum.ILLEGAL_PARAMETER,"角色id不能为空");
        }

        RoleDO roleDO = roleService.findOne(roleId);
        roleDO.setDeleted(false);
        RoleVO roleVo = BaseConvert.convert(roleDO, RoleVO.class);
        return roleVo;
    }

    /**
     * 分页查询角色列表接口
     * @param roleForm
     * @param request
     * @return
     */
    @RequestMapping(value = "/list",method = RequestMethod.POST)
    @RequiresPermissions(value = {"SASS_ROLE_MNG", "SASS_ROLE_QUERY_LIST"},logical= Logical.OR)
    public Object pageQueryRole(@RequestBody RoleForm roleForm, HttpServletRequest request){

        if(roleForm == null){
            throw new BusinessException(SassBizResultCodeEnum.ILLEGAL_PARAMETER,"更新角色参数不能为空");
        }
        logger.info("--------SassRoleRestController.pageQueryRole.Request:{}--------",JSONObject.toJSONString(roleForm));
        
        RoleDO roleDO = SassFormConvert.convertRoleForm2RoleDO(roleForm);
        PageResultVO page = new PageResultVO();
        page.setPageNumber((roleForm.getPageIndex() == null) ? PageResultVO.DEFAULT_PAGE_NUM : roleForm.getPageIndex());
        page.setPageSize((roleForm.getPageSize() == null) ? PageResultVO.DEFAULT_PAGE_SIZE : roleForm.getPageSize());
        PageResultVO<RoleDO> pageRole = roleService.findByConditionPage(page, roleDO);

        return pageRole;
    }



    /**
     * 分页查询角色列表接口
     * @param request
     * @return
     */
    @RequestMapping(value = "/routes",method = RequestMethod.GET)
    @RequiresPermissions(value = {"SASS_ROLE_MNG", "SASS_ROLE_QUERY_LIST"},logical= Logical.OR)
    public Object routesAllRole(HttpServletRequest request){

        logger.info("--------SassRoleRestController.routesAllRole.Request:{}--------");

        SassUserInfo sassUserInfo = this.getSassUser(request);

        List<RoleDO> roleDOList = roleService.queryAllRolesByTenantId(sassUserInfo.getTenantId());

        List<RoleVO> roleVOS = BaseConvert.convertList(roleDOList, RoleVO.class);

        return roleVOS;

    }


    /**
     * 查询某个角色下面的权限值
     * @param request
     * @return
     */
    @RequestMapping(value = "/resource/tree",method = RequestMethod.GET)
    @ResponseBody
    @RequiresPermissions(value = {"SASS_ROLE_MNG", "SASS_ROLE_QUERY_LIST"},logical= Logical.OR)
    public Object treeRoleResource(HttpServletRequest request, @Param("roleCode") String roleCode){

        logger.info("ResourceController.treeRoleResource,roleCode={}",roleCode);

        if(StringUtils.isEmpty(roleCode)){
            throw new BusinessException(SassBizResultCodeEnum.DATA_NOT_EXIST,"角色不能为空,无法查询权限值列表");
        }

        // 如果roleCode不为空，则表示需要查询roleCode的权限值
        List<ResourceDO> roleResourceDOS = this.roleResourceService.findResourcesByRoleCode(roleCode);

        List<ResourceVO> resourceVOList = SassFormConvert.convertResourceDO2VO(roleResourceDOS);

        // 组装必要的参数
        List<MenuTreeVO> resourceTreeVOList = Lists.newArrayList();
        resourceVOList.stream().forEach(
                resourceVO -> {
                    MenuTreeVO menuTreeVO = new MenuTreeVO();
                    menuTreeVO.setText(resourceVO.getName());
                    menuTreeVO.setParentId(String.valueOf(resourceVO.getParentId()));
                    menuTreeVO.setId(String.valueOf(resourceVO.getId()));
                    menuTreeVO.setCode(resourceVO.getCode());
                    menuTreeVO.setUrl(resourceVO.getUrl());
                    menuTreeVO.setAuthority(resourceVO.getAuthority());
                    menuTreeVO.setOrder(0);
                    menuTreeVO.setChecked(false);
                    resourceTreeVOList.add(menuTreeVO);
                }
        );

        List<MenuTreeVO> results = TreeResultUtil.build(resourceTreeVOList);
        // 返回权限树
        return results;

    }

    /**
     * 角色授权接口
     * @param roleAuthorityForm
     * @param request
     * @return
     */
    @RequestMapping(value = "/user/grant",method = RequestMethod.POST)
    @RequiresPermissions(value = {"SASS_ROLE_MNG", "SASS_ROLE_GRANT_RESOURCE"},logical= Logical.OR)
    @Transactional(rollbackFor = Exception.class)
    @SysLog("角色授予操作")
    public Object userGrant(@RequestBody RoleAuthorityForm roleAuthorityForm, HttpServletRequest request){

        if(roleAuthorityForm == null){
            throw new BusinessException(SassBizResultCodeEnum.ILLEGAL_PARAMETER,"更新角色参数不能为空");
        }
        logger.info("--------SassRoleRestController.grant.Request:{}--------",JSONObject.toJSONString(roleAuthorityForm));

        RoleDO roleDO = roleService.findByRoleCode(roleAuthorityForm.getRoleCode());
        if(roleDO == null){
            throw new BusinessException(SassBizResultCodeEnum.DATA_NOT_EXIST,"不存在该角色");
        }
        List<UserRoleDO> oldUserRoleList = userRoleService.findByRoleCode(roleAuthorityForm.getRoleCode());
        Set<String> oldUserRoles = new HashSet();
        Set<String> newUserRoles = new HashSet();
        if(!CollectionUtils.isEmpty(roleAuthorityForm.getUserList())){
            newUserRoles.addAll(roleAuthorityForm.getUserList());
        }
        if(!CollectionUtils.isEmpty(oldUserRoleList)){
            oldUserRoles = oldUserRoleList.stream().map(UserRoleDO::getUserAccount).collect(Collectors.toSet());;
        }
        //需要删除角色的用户
        Sets.SetView<String> deleteAccounts = Sets.difference(oldUserRoles, newUserRoles);
        //需要添加角色的用户
        Sets.SetView<String> createAccounts = Sets.difference(newUserRoles, oldUserRoles);
        if(!CollectionUtils.isEmpty(deleteAccounts)) {
            userRoleService.deleteByAccountsAndRoleCode(roleAuthorityForm.getRoleCode(), new ArrayList<>(deleteAccounts));
            logger.info("delete");
            deleteAccounts.forEach(account->{userService.refreshSassUserInfo(account);});
        }
        if(!CollectionUtils.isEmpty(createAccounts)){
            List<UserDO> userDOList = userService.findUserByAccounts(new ArrayList<>(createAccounts));
            SassUserInfo sassUserInfo = this.getSassUser(request);
            List<UserRoleDO> userRoleDOList = userDOList.stream().map(user -> {
                UserRoleDO userRoleDO = new UserRoleDO();
                userRoleDO.setUserAccount(user.getAccount());
                userRoleDO.setUserName(user.getName());
                userRoleDO.setRoleName(roleDO.getName());
                userRoleDO.setRoleCode(roleDO.getCode());
                userRoleDO.setGmtCreator(sassUserInfo.getAccount());
                userRoleDO.setGmtUpdater(sassUserInfo.getAccount());
                return userRoleDO;
            }).collect(Collectors.toList());
            userRoleService.createUserRoles(userRoleDOList);
            logger.info("create");
            createAccounts.forEach(account->{userService.refreshSassUserInfo(account);});
        }

        return SassBizResultCodeEnum.SUCCESS.getAlertMessage();
    }


    /**
     * 角色授权接口
     * @param grantForm
     * @param request
     * @return
     */
    @RequestMapping(value = "/resource/grant",method = RequestMethod.POST)
    @RequiresPermissions(value = {"SASS_ROLE_MNG", "SASS_ROLE_GRANT_RESOURCE"},logical= Logical.OR)
    @Transactional(rollbackFor = Exception.class)
    @SysLog("资源授予操作")
    public Object resourceGrant(@RequestBody RoleResourceGrantForm grantForm, HttpServletRequest request){

        if(grantForm == null){
            throw new BusinessException(SassBizResultCodeEnum.ILLEGAL_PARAMETER, "角色资源授权参数不能为空");
        }
        logger.info("--------SassRoleRestController.grant.Request:{}--------",JSONObject.toJSONString(grantForm));

        SassUserInfo sassUserInfo = this.getSassUser(request);

        String roleCode = grantForm.getRoleCode();
        String roleName = grantForm.getRoleName();
        List<RoleResourceForm> grantResourceList = grantForm.getResources();

        List<RoleResourceDO> roleResourceDOS = Lists.newArrayList();
        for(RoleResourceForm roleResourceForm : grantResourceList){

            RoleResourceDO roleResourceDO = new RoleResourceDO();
            roleResourceDO.setTenantId(sassUserInfo.getTenantId());
            roleResourceDO.setResourceCode(roleResourceForm.getResourceCode());
            roleResourceDO.setResourceName(roleResourceForm.getResourceName());
            roleResourceDO.setRoleCode(roleCode);
            roleResourceDO.setRoleName(roleName);
            roleResourceDO.setGmtCreator(sassUserInfo.getAccount());
            roleResourceDO.setGmtUpdater(sassUserInfo.getAccount());

            roleResourceDOS.add(roleResourceDO);
        }

        // 1.首先根据角色删除角色下面的权限
        // 2.重新插入角色权限
        this.roleResourceService.deleteByRoleCodes(Lists.newArrayList(roleCode));
        this.roleResourceService.createRoleResources(roleResourceDOS);
        // 更新对应的角色的用户的缓存权限信息
        List<UserRoleDO> userRoleList = userRoleService.findByRoleCode(grantForm.getRoleCode());
        if(!CollectionUtils.isEmpty(userRoleList)) {
            userRoleList.stream().forEach(userRoleDO -> userService.refreshSassUserInfo(userRoleDO.getUserAccount()));
        }
        return SassBizResultCodeEnum.SUCCESS.getAlertMessage();
    }
}
