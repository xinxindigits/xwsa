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
import cn.com.xinxin.sass.auth.repository.UserAclTokenRepository;
import cn.com.xinxin.sass.biz.log.SysLog;
import cn.com.xinxin.sass.biz.service.OrganizationService;
import cn.com.xinxin.sass.biz.service.RoleService;
import cn.com.xinxin.sass.biz.service.UserRoleService;
import cn.com.xinxin.sass.biz.service.UserService;
import cn.com.xinxin.sass.biz.vo.QueryUserConditionVO;
import cn.com.xinxin.sass.common.enums.SassBizResultCodeEnum;
import cn.com.xinxin.sass.common.model.PageResultVO;
import cn.com.xinxin.sass.repository.model.*;
import cn.com.xinxin.sass.auth.web.AclController;
import cn.com.xinxin.sass.tenant.TenantIdContext;
import cn.com.xinxin.sass.web.form.*;
import cn.com.xinxin.sass.web.utils.RandomPasswordUtils;
import cn.com.xinxin.sass.web.utils.RegexUtils;
import cn.com.xinxin.sass.web.vo.*;
import cn.com.xinxin.sass.web.convert.SassFormConvert;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.xinxinfinance.commons.api.result.support.Results;
import com.xinxinfinance.commons.exception.BusinessException;
import com.xinxinfinance.commons.result.CommonResultCode;
import com.xinxinfinance.commons.security.SecureUtils;
import com.xinxinfinance.commons.util.BaseConvert;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

/**
 * @author: zhouyang
 * @created: 14/04/2020.
 * @updater:
 * @description: 用户信息接口，注意在更新用户权限以及相关的菜单操作的时候记得要刷新对应的用户权限缓存信息
 */

@RestController
@RequestMapping(value = "/user",produces = "application/json; charset=UTF-8")
public class SassUserRestController extends AclController {

    private static final Logger log = LoggerFactory.getLogger(SassUserRestController.class);


    @Autowired
    private UserService userService;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private UserAclTokenRepository userAclTokenRepository;

    @Autowired
    private RoleService roleService;

    @Autowired
    private OrganizationService organizationService;


    @RequestMapping(value = "/list",method = RequestMethod.POST)
    @ResponseBody
    @RequiresRoles(value = {"sass_admin","admin","sass_mng"},logical= Logical.OR)
    public Object pageQueryUser(@RequestBody UserForm userForm, HttpServletRequest request){

        if(userForm == null){
            throw new BusinessException(SassBizResultCodeEnum.ILLEGAL_PARAMETER, "参数不能为空");
        }
        log.info("--------SassUserRestController.pageQueryUser.Request:{}--------",JSONObject.toJSONString(userForm));

        SassUserInfo sassUserInfo = this.getSassUser(request);

        String opsTenantId = this.getOpsTenantId(request);

        if(StringUtils.isBlank(opsTenantId)){
            throw new BusinessException(SassBizResultCodeEnum.ILLEGAL_PARAMETER, "需要运营的租户不能为空");
        }

        PageResultVO page = new PageResultVO();
        page.setPageNumber((userForm.getPageIndex() == null) ? PageResultVO.DEFAULT_PAGE_NUM : userForm.getPageIndex());
        page.setPageSize((userForm.getPageSize() == null) ? PageResultVO.DEFAULT_PAGE_SIZE : userForm.getPageSize());
        QueryUserConditionVO queryUserConditionVO = BaseConvert.convert(userForm, QueryUserConditionVO.class);

        PageResultVO<UserDO> pageUser = userService.findByConditionPageAndTenantId(page,
                queryUserConditionVO, opsTenantId);

        PageResultVO<UserInfoVO> resultVO = BaseConvert.convert(pageUser, PageResultVO.class);

        List<UserInfoVO> userInfoVOS = Lists.newArrayList();

        if(!CollectionUtils.isEmpty(pageUser.getItems())){
            List<String> accounts = pageUser.getItems().stream()
                    .map(x->x.getAccount()).collect(toList());
            Map<String,  List<UserOrgDO>> userOrgsMaps = this.userService.queryUserOrgsMapsByAccounts(accounts);
            userInfoVOS = pageUser.getItems().stream().map(userDO -> {
                UserInfoVO userInfoVO = BaseConvert.convert(userDO, UserInfoVO.class);
                userInfoVO.setGender(userDO.getGender() == null ? null : userDO.getGender().intValue());
                userInfoVO.setStatus(userDO.getStatus() == null ? null : userDO.getStatus().intValue());
                // 设置用户组织关系
                List<UserOrgDO> userOrgDOList = userOrgsMaps.get(userDO.getAccount());
                List<OrgSimpleVO> userOrgVOList = SassFormConvert.convertOrgDO2VOList(userOrgDOList);
                userInfoVO.setOrgs(userOrgVOList);

                return userInfoVO;
            }).collect(Collectors.toList());
        }
        resultVO.setItems(userInfoVOS);
        return resultVO;
    }

    @RequestMapping(value = "/query/{account}",method = RequestMethod.GET)
    @ResponseBody
    @RequiresRoles(value = {"sass_admin","admin","sass_mng"},logical= Logical.OR)
    public Object queryUserByAccount(HttpServletRequest request,@PathVariable String account){

        log.info("queryUserByAccount, account = {}",account);

        SassUserInfo sassUserInfo = this.getSassUser(request);

        String opsTenantId = this.getOpsTenantId(request);

        if(StringUtils.isBlank(opsTenantId)){
            throw new BusinessException(SassBizResultCodeEnum.ILLEGAL_PARAMETER, "需要运营的租户不能为空");
        }


        UserDO userDO = this.userService.findByUserAccountAndTenantId(account,opsTenantId);

        UserInfoVO userInfoVO = BaseConvert.convert(userDO, UserInfoVO.class);
        userInfoVO.setGender(userDO.getGender() == null ? null : userDO.getGender().intValue());
        userInfoVO.setStatus(userDO.getStatus() == null ? null : userDO.getStatus().intValue());
        /**
         * 用户对应的角色值
         */
        List<RoleDO> userRolesLists = this.userService.findRolesByAccount(account);
        log.info("queryUserByAccount, userRolesLists = {}",userRolesLists);

        List<RoleVO> userRolesVOLists = SassFormConvert.convertRoleDO2VOs(userRolesLists);
        userInfoVO.setRoles(userRolesVOLists);

        /**
         * 用户对应的资源权限值
         */
        List<ResourceDO> userResourceDOList = this.userService.findResourcesByAccount(account);
        List<ResourceVO> userResourceVOList  = SassFormConvert.convertResourceDO2VO(userResourceDOList);
        log.info("queryUserByAccount, userRolesLists = {}",userRolesLists);

        userInfoVO.setResources(userResourceVOList);

        // 查询对应的组织关系
        List<UserOrgDO> userOrgDOList = this.userService.queryUserOrgsByAccount(account);
        List<OrgSimpleVO> userOrgVOList = SassFormConvert.convertOrgDO2VOList(userOrgDOList);
        userInfoVO.setOrgs(userOrgVOList);

        return userInfoVO;

    }

    @RequestMapping(value = "/me",method = RequestMethod.GET)
    @ResponseBody
    public Object queryLoginUserByMe(HttpServletRequest request){


        /**
         * 用户查询自身信息，需要将租户ID设置为自己的
         */
        SassUserInfo sassUserInfo = this.getSassUser(request);

        log.info("queryUserByAccount, account = {}",sassUserInfo.getAccount());

        //设置租户ID
        TenantIdContext.set(sassUserInfo.getTenantId());

        UserDO userDO = this.userService.findByUserAccountAndTenantId(sassUserInfo.getAccount(),
                sassUserInfo.getTenantId());

        UserInfoVO userInfoVO = BaseConvert.convert(userDO, UserInfoVO.class);
        userInfoVO.setGender(userDO.getGender() == null ? null : userDO.getGender().intValue());
        userInfoVO.setStatus(userDO.getStatus() == null ? null : userDO.getStatus().intValue());
        /**
         * 用户对应的角色值
         */
        List<RoleDO> userRolesLists = this.userService.findRolesByAccount(sassUserInfo.getAccount());
        log.info("queryUserByAccount, userRolesLists = {}",userRolesLists);

        List<RoleVO> userRolesVOLists = SassFormConvert.convertRoleDO2VOs(userRolesLists);
        userInfoVO.setRoles(userRolesVOLists);

        /**
         * 用户对应的资源权限值
         */
        List<ResourceDO> userResourceDOList = this.userService.findResourcesByAccount(sassUserInfo.getAccount());
        List<ResourceVO> userResourceVOList  = SassFormConvert.convertResourceDO2VO(userResourceDOList);
        log.info("queryUserByAccount, userRolesLists = {}",userRolesLists);

        userInfoVO.setResources(userResourceVOList);

        // 查询对应的组织关系
        List<UserOrgDO> userOrgDOList = this.userService.queryUserOrgsByAccount(sassUserInfo.getAccount());
        List<OrgSimpleVO> userOrgVOList = SassFormConvert.convertOrgDO2VOList(userOrgDOList);
        userInfoVO.setOrgs(userOrgVOList);

        // 移除租户操作ID
        TenantIdContext.remove();
        return userInfoVO;

    }

    @SysLog("重置用户密码")
    @RequestMapping(value = "/restpwd",method = RequestMethod.POST)
    @ResponseBody
    @RequiresRoles(value = {"sass_admin","admin","sass_mng"},logical= Logical.OR)
    public Object restUserPassword(HttpServletRequest request, @RequestBody UserPwdForm pwdForm){

        if(null == pwdForm){
            throw new BusinessException(SassBizResultCodeEnum.PARAMETER_NULL,"重置密码参数不能为空","重置密码参数不能为空");
        }
        SassUserInfo sassUserInfo = this.getSassUser(request);
        // 创建用户信息不能更新用户密码以及账号信息，如果需要更新密码，走密码重置的方法即可
        String userAccount = pwdForm.getAccount();
        String userOldPwd = pwdForm.getOldPassword();
        String userNewPwd = pwdForm.getNewPassword();
            // 重置密码
        String randomPwd = RandomPasswordUtils.getPasswordSimple(4,4);
        String md5Pwd = SecureUtils.getMD5(randomPwd);
        this.userService.resetPassword(userAccount,md5Pwd,sassUserInfo.getAccount());
        String resultMsg = "你的密码已经重置为:[" + randomPwd +"],登录后请重新修改密码";
        return  resultMsg;
    }


    @SysLog("修改用户密码")
    @RequestMapping(value = "/password/modify",method = RequestMethod.POST)
    @ResponseBody
    public Object modifyUserPassword(HttpServletRequest request, @RequestBody UserPwdForm pwdForm){

        if(null == pwdForm){
            throw new BusinessException(SassBizResultCodeEnum.PARAMETER_NULL,"重置密码参数不能为空","重置密码参数不能为空");
        }
        SassUserInfo sassUserInfo = this.getSassUser(request);
        // 创建用户信息不能更新用户密码以及账号信息，如果需要更新密码，走密码重置的方法即可
        String userAccount = pwdForm.getAccount();
        String userOldPwd = pwdForm.getOldPassword();
        String userNewPwd = pwdForm.getNewPassword();
        // 新旧密码都不为空，表示修改密码
        this.userService.modifyPassword(userAccount,userOldPwd,userNewPwd,sassUserInfo.getAccount());
        String resultMsg = "你的密码已经修改成功，退出来重新登陆即可";

        return  resultMsg;
    }


    @SysLog("创建用户信息")
    @RequestMapping(value = "/create",method = RequestMethod.POST)
    @ResponseBody
    @RequiresRoles(value = {"sass_admin","admin","sass_mng"},logical= Logical.OR)
    public Object createUserInfo(HttpServletRequest request, @RequestBody UserForm userForm){

        if(null == userForm){
            throw new BusinessException(SassBizResultCodeEnum.PARAMETER_NULL,"用户创建信息不能为空","用户信息不能为空");
        }

        String opsTenantId = this.getOpsTenantId(request);

        if(StringUtils.isBlank(opsTenantId)){
            throw new BusinessException(SassBizResultCodeEnum.ILLEGAL_PARAMETER, "需要运营的租户不能为空");
        }

        SassUserInfo sassUserInfo = this.getSassUser(request);

        // 创建用户信息不能更新用户密码以及账号信息，如果需要更新密码，走密码重置的方法即可
        String userAccount = userForm.getAccount();

        if(!RegexUtils.isUsername(userAccount)){
            // 如果匹配不是useraccount格式
            throw new BusinessException(SassBizResultCodeEnum.ILLEGAL_PARAMETER,"账号不能包含特殊字符或者长度超过20","账号不能包含特殊字符或者长度超过20");
        }

        // 查询已经存在的用户信息
        UserDO existUserDO = this.userService.findByUserAccountAndTenantId(userAccount, opsTenantId);

        if(null != existUserDO){
            throw new BusinessException(SassBizResultCodeEnum.DATA_ALREADY_EXIST,"用户账号信息已经存在","用户账号信息已经存在");
        }

        UserDO userCreateDO = SassFormConvert.convertUserForm2UserDO(userForm);

        userCreateDO.setGender(Byte.valueOf(String.valueOf(userForm.getGender())));

        userCreateDO.setTenantId(opsTenantId);

        if(StringUtils.isEmpty(userCreateDO.getTenantId())){
            // FIXME: 先默认设置为xinxin租户
            userCreateDO.setTenantId("xinxin");
        }

        int result =0;

        try {
            result = this.userService.createUser(userCreateDO);
        }catch (DuplicateKeyException dex){
            throw new BusinessException(SassBizResultCodeEnum.ILLEGAL_PARAMETER, "编码不能重复","编码不能重复");
        }catch (Exception ex){
            throw new BusinessException(SassBizResultCodeEnum.FAIL, "处理异常，请稍后重试","处理异常，请稍后重试");
        }

        if(CollectionUtils.isNotEmpty(userForm.getRoles())){
            // 创建用户的角色信息
            List<UserRoleDO> userRoleDOS = Lists.newArrayList();
            List<RoleDO> roleDOList = this.roleService.queryByRoleCodes(userForm.getRoles());
            for(RoleDO roleDO : roleDOList){

                UserRoleDO userRoleDO = new UserRoleDO();
                userRoleDO.setTenantId(opsTenantId);
                userRoleDO.setUserAccount(userCreateDO.getAccount());
                userRoleDO.setUserName(userCreateDO.getName());
                userRoleDO.setRoleCode(roleDO.getCode());
                userRoleDO.setRoleName(roleDO.getName());
                userRoleDO.setGmtCreator(sassUserInfo.getAccount());
                userRoleDO.setGmtUpdater(sassUserInfo.getAccount());
                userRoleDOS.add(userRoleDO);
            }

            this.userRoleService.createUserRoles(userRoleDOS);
        }

        String userOrgCode = userForm.getOrgCode();

        if(StringUtils.isNotEmpty(userOrgCode)){
            // 如果组织机构编码不为空，则需要创建组装与用户的关系
            OrganizationDO organizationDO = this.organizationService.findByCode(userOrgCode);
            UserOrgDO userOrgDO = new UserOrgDO();
            userOrgDO.setTenantId(opsTenantId);
            userOrgDO.setUserAccount(userCreateDO.getAccount());
            userOrgDO.setUserName(userCreateDO.getName());
            userOrgDO.setOrgCode(organizationDO.getCode());
            userOrgDO.setOrgName(organizationDO.getName());
            userOrgDO.setGmtCreator(sassUserInfo.getAccount());
            userOrgDO.setGmtUpdater(sassUserInfo.getAccount());
            this.userService.createUserOrgRelations(userOrgDO);

        }
        return result;
    }


    @SysLog("更新用户信息")
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    @ResponseBody
    @RequiresRoles(value = {"sass_admin","admin","sass_mng"},logical= Logical.OR)
    public Object updateUserInfo(HttpServletRequest request, @RequestBody UserForm userForm){

        if(null == userForm){
            throw new BusinessException(SassBizResultCodeEnum.PARAMETER_NULL,"用户信息不能为空","用户信息不能为空");
        }

        String opsTenantId = this.getOpsTenantId(request);

        if(StringUtils.isBlank(opsTenantId)){
            throw new BusinessException(SassBizResultCodeEnum.ILLEGAL_PARAMETER, "需要运营的租户不能为空");
        }

        SassUserInfo sassUserInfo = this.getSassUser(request);

        // 更新用户信息不能更新用户密码以及账号信息，如果需要更新密码，走密码重置的方法即可
        String userAccount = userForm.getAccount();
        // 查询已经存在的用户信息
        UserDO userDO = this.userService.findByUserAccountAndTenantId(userAccount,sassUserInfo.getTenantId());

        if(StringUtils.isNotEmpty(userForm.getName())){
            userDO.setName(userForm.getName());
        }
        userDO.setExtension(userForm.getExtension());
        if(userForm.getGender() != null){
            userDO.setGender(userForm.getGender().byteValue());
        }
        if(userForm.getStatus() != null){
            userDO.setGender(userForm.getStatus().byteValue());
        }
        userDO.setGmtUpdater(sassUserInfo.getAccount());

        boolean result = this.userService.updateUser(userDO);

        if(CollectionUtils.isNotEmpty(userForm.getRoles())){
            // 首先批量删除用户下面的权限值
            this.userRoleService.deleteByAccounts(Lists.newArrayList(userDO.getAccount()));
            // 创建用户的角色信息
            List<UserRoleDO> userRoleDOS = Lists.newArrayList();
            List<RoleDO> roleDOList = this.roleService.queryByRoleCodes(userForm.getRoles());
            for(RoleDO roleDO : roleDOList){
                UserRoleDO userRoleDO = new UserRoleDO();
                userRoleDO.setUserAccount(userDO.getAccount());
                userRoleDO.setTenantId(opsTenantId);
                userRoleDO.setUserName(userDO.getName());
                userRoleDO.setRoleCode(roleDO.getCode());
                userRoleDO.setRoleName(roleDO.getName());
                userRoleDO.setGmtCreator(sassUserInfo.getAccount());
                userRoleDO.setGmtUpdater(sassUserInfo.getAccount());
                userRoleDOS.add(userRoleDO);
            }

            this.userRoleService.createUserRoles(userRoleDOS);

        }


        String userOrgCode = userForm.getOrgCode();

        if(StringUtils.isNotEmpty(userOrgCode)){
            // 如果组织机构编码不为空，则需要创建组装与用户的关系
            int deleted = this.userService.removeUserOrgRelationByAccount(userDO.getAccount());
            OrganizationDO organizationDO = this.organizationService.findByCode(userOrgCode);
            UserOrgDO userOrgDO = new UserOrgDO();
            userOrgDO.setTenantId(opsTenantId);
            userOrgDO.setUserAccount(userDO.getAccount());
            userOrgDO.setUserName(userDO.getName());
            userOrgDO.setOrgCode(organizationDO.getCode());
            userOrgDO.setOrgName(organizationDO.getName());
            userOrgDO.setGmtCreator(sassUserInfo.getAccount());
            userOrgDO.setGmtUpdater(sassUserInfo.getAccount());
            this.userService.createUserOrgRelations(userOrgDO);

        }

        return result;
    }


    @SysLog("删除用户信息")
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    @ResponseBody
    @RequiresRoles(value = {"sass_admin","admin","sass_mng"},logical= Logical.OR)
    public Object deleteUserInfo(HttpServletRequest request,@RequestBody DeleteUserForm deleteUserForm){

        if(deleteUserForm == null){
            throw new BusinessException(SassBizResultCodeEnum.ILLEGAL_PARAMETER);
        }

        if(CollectionUtils.isEmpty(deleteUserForm.getAccounts())){
            throw new BusinessException(SassBizResultCodeEnum.PARAMETER_NULL,"删除用户参数不能为空","删除用户参数不能为空");
        }

        SassUserInfo sassUserInfo = this.getSassUser(request);

        if(deleteUserForm.getAccounts().contains(sassUserInfo.getAccount())){
            // 不能删除自己的账户
            throw new BusinessException(SassBizResultCodeEnum.PARAMETER_NULL,"不能删除自己的账号","不能删除自己的账号");
        }

        // FIXME: 删除的时候同时需要清除token等缓存信息
        this.userService.deleteUserByAccounts(deleteUserForm.getAccounts());


        deleteUserForm.getAccounts().stream().forEach(account->{
            userAclTokenRepository.cleanSassUserTokenCache(account);
            userAclTokenRepository.cleanSassUserInfoCache(account);
        });

        // 删除用户角色关系
        this.userRoleService.deleteByAccounts(deleteUserForm.getAccounts());
        // 删除用户组织关系
        this.userService.removeUserOrgRelationByAccountList(deleteUserForm.getAccounts());

        return SassBizResultCodeEnum.SUCCESS;

    }



    /**
     * 用户角色授权接口
     * @param request
     * @param grantForm
     * @return
     */
    @SysLog("用户角色授权信息")
    @RequestMapping(value = "/grant",method = RequestMethod.POST)
    @ResponseBody
    @RequiresRoles(value = {"sass_admin","admin","sass_mng"},logical= Logical.OR)
    public Object grantRoleUserInfo(HttpServletRequest request,
                                    @RequestBody UserRoleGrantForm grantForm){
        if(null == grantForm){
            throw new BusinessException(SassBizResultCodeEnum.PARAMETER_NULL,"用户信息不能为空","用户信息不能为空");
        }


        String opsTenantId = this.getOpsTenantId(request);

        if(StringUtils.isBlank(opsTenantId)){
            throw new BusinessException(SassBizResultCodeEnum.ILLEGAL_PARAMETER, "需要运营的租户不能为空");
        }

        log.info("grantRoleUserInfo, grantForm = {}",grantForm);

        SassUserInfo sassUserInfo = this.getSassUser(request);

        String grantedUserAccount = grantForm.getUserAccount();

        String grantedUserName = grantForm.getUserName();

        List<UserRoleForm> userRoleForms = grantForm.getUserRoles();

        if(CollectionUtils.isEmpty(userRoleForms)){
            throw new BusinessException(SassBizResultCodeEnum.PARAMETER_NULL,"权限值不能为空","权限值不能为空");
        }

        List<UserRoleDO> userRoleDOS = Lists.newArrayList();

        for(UserRoleForm userRoleForm : userRoleForms){
            UserRoleDO userRoleDO = new UserRoleDO();
            userRoleDO.setTenantId(opsTenantId);
            userRoleDO.setRoleCode(userRoleForm.getRoleCode());
            userRoleDO.setRoleName(userRoleForm.getRoleName());
            userRoleDO.setUserAccount(grantedUserAccount);
            userRoleDO.setUserName(grantedUserName);
            userRoleDO.setGmtCreator(sassUserInfo.getAccount());
            userRoleDO.setGmtUpdater(sassUserInfo.getAccount());
            userRoleDOS.add(userRoleDO);
        }
        // 首先批量删除用户下面的权限值
        this.userRoleService.deleteByAccounts(Lists.newArrayList(grantForm.getUserAccount()));
        // 批量插入权限值
        this.userRoleService.createUserRoles(userRoleDOS);


        /**
         * 判断是否有必要更新权限值
         */
        SassUserInfo grantedUserInfo = userAclTokenRepository.getSassUserByUserAccount(grantedUserAccount);
        if(null != grantedUserInfo){
            // 缓存信息以及存在用户登录，在需要更新用户权限值
            //跟新用户缓存的角色以及权限值
            if (!CollectionUtils.isEmpty(userRoleDOS)){
                Set<String> roleCodes = new HashSet<>(userRoleDOS.size());
                userRoleDOS.forEach(userRoleDO -> roleCodes.add(userRoleDO.getRoleCode()));
                grantedUserInfo.setRoles(roleCodes);
            }else{
                grantedUserInfo.setRoles(new HashSet<>());
            }

            List<ResourceDO> resourceDOS = userService.findResourcesByAccount(grantedUserAccount);
            if (!CollectionUtils.isEmpty(resourceDOS)){
                Set<String> permissionUrls = new HashSet<>(resourceDOS.size());
                resourceDOS.forEach(resourceDO -> permissionUrls.add(resourceDO.getAuthority()));
                grantedUserInfo.setStringPermissions(permissionUrls);
            }else{
                grantedUserInfo.setStringPermissions(new HashSet<>());
            }
            // 设置用户的token以及角色，权限等信息缓存
            userAclTokenRepository.setSassUserByUserAccount(grantedUserAccount,grantedUserInfo);
        }else{
            log.info("grantRoleUserInfo, 无需更新用户权限值");
        }

        return SassBizResultCodeEnum.SUCCESS;
    }

}
