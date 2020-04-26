package cn.com.xinxin.sass.web.rest;


import cn.com.xinxin.sass.auth.model.SassUserInfo;
import cn.com.xinxin.sass.auth.repository.UserAclTokenRepository;
import cn.com.xinxin.sass.biz.service.UserRoleService;
import cn.com.xinxin.sass.biz.service.UserService;
import cn.com.xinxin.sass.biz.vo.QueryUserConditionVO;
import cn.com.xinxin.sass.common.enums.SassBizResultCodeEnum;
import cn.com.xinxin.sass.common.model.PageResultVO;
import cn.com.xinxin.sass.repository.model.ResourceDO;
import cn.com.xinxin.sass.repository.model.RoleDO;
import cn.com.xinxin.sass.repository.model.UserDO;
import cn.com.xinxin.sass.auth.web.AclController;
import cn.com.xinxin.sass.repository.model.UserRoleDO;
import cn.com.xinxin.sass.web.convert.SassFormConvert;
import cn.com.xinxin.sass.web.form.UserForm;
import cn.com.xinxin.sass.web.form.UserLoginForm;
import cn.com.xinxin.sass.web.form.UserRoleForm;
import cn.com.xinxin.sass.web.form.UserRoleGrantForm;
import cn.com.xinxin.sass.web.vo.ResourceVO;
import cn.com.xinxin.sass.web.vo.RoleVO;
import cn.com.xinxin.sass.web.vo.UserInfoVO;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.xinxinfinance.commons.exception.BusinessException;
import com.xinxinfinance.commons.util.BaseConvert;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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


    @RequestMapping(value = "/list",method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions("/user/list")
    public Object pageQueryUser(@RequestBody UserForm userForm, HttpServletRequest request){
        if(userForm == null){
            throw new BusinessException(SassBizResultCodeEnum.ILLEGAL_PARAMETER,"更新角色参数不能为空");
        }
        log.info("--------SassUserRestController.pageQueryUser.Request:{}--------",JSONObject.toJSONString(userForm));

        PageResultVO page = new PageResultVO();
        page.setPageNumber((userForm.getPageNum() == null) ? PageResultVO.DEFAULT_PAGE_NUM : userForm.getPageNum());
        page.setPageSize((userForm.getPageSize() == null) ? PageResultVO.DEFAULT_PAGE_SIZE : userForm.getPageSize());
        QueryUserConditionVO queryUserConditionVO = BaseConvert.convert(userForm, QueryUserConditionVO.class);
        PageResultVO<UserDO> pageUser = userService.findByConditionPage(page, queryUserConditionVO);

        return pageUser;
    }

    @RequestMapping(value = "/query/{account}",method = RequestMethod.GET)
    @ResponseBody
    @RequiresPermissions("/user/query")
    public Object queryUserByAccount(HttpServletRequest request,@PathVariable String account){

        log.info("queryUserByAccount, account = {}",account);

        UserDO userDO = this.userService.findByUserAccount(account);

        UserInfoVO userInfoVO = BaseConvert.convert(userDO, UserInfoVO.class);

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

        return userInfoVO;

    }


    @RequestMapping(value = "/restpwd",method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions("/user/restpwd")
    public Object restUserPassword(HttpServletRequest request, @RequestBody UserLoginForm pwdForm){

        if(null == pwdForm){
            throw new BusinessException(SassBizResultCodeEnum.PARAMETER_NULL,"重置密码参数不能为空","重置密码参数不能为空");
        }

        // 创建用户信息不能更新用户密码以及账号信息，如果需要更新密码，走密码重置的方法即可
        String userAccount = pwdForm.getAccount();

        String userPwd = pwdForm.getPassword();

        SassUserInfo sassUserInfo = this.getSassUser(request);

        this.userService.resetPassword(userAccount,userPwd,sassUserInfo.getAccount());
        //FIXME: 重置密码同时需要清除用户缓存以及对应的token
        userAclTokenRepository.cleanSassUserTokenCache(userAccount);
        userAclTokenRepository.cleanSassUserInfoCache(userAccount);
        return SassBizResultCodeEnum.SUCCESS;
    }


    @RequestMapping(value = "/create",method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions("/user/create")
    public Object createUserInfo(HttpServletRequest request, @RequestBody UserForm userForm){

        if(null == userForm){
            throw new BusinessException(SassBizResultCodeEnum.PARAMETER_NULL,"用户创建信息不能为空","用户信息不能为空");
        }

        // 创建用户信息不能更新用户密码以及账号信息，如果需要更新密码，走密码重置的方法即可
        String userAccount = userForm.getAccount();
        // 查询已经存在的用户信息
        UserDO existUserDO = this.userService.findByUserAccount(userAccount);

        if(null != existUserDO){
            throw new BusinessException(SassBizResultCodeEnum.DATA_ALREADY_EXIST,"用户账号信息已经存在","用户账号信息已经存在");
        }

        UserDO userCreateDO = SassFormConvert.convertUserForm2UserDO(userForm);

        userCreateDO.setGender(Byte.valueOf(String.valueOf(userForm.getGender())));

        int result = this.userService.createUser(userCreateDO);

        return result;
    }



    @RequestMapping(value = "/update",method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions("/user/update")
    public Object updateUserInfo(HttpServletRequest request, @RequestBody UserForm userForm){

        if(null == userForm){
            throw new BusinessException(SassBizResultCodeEnum.PARAMETER_NULL,"用户信息不能为空","用户信息不能为空");
        }


        SassUserInfo sassUserInfo = this.getSassUser(request);

        // 更新用户信息不能更新用户密码以及账号信息，如果需要更新密码，走密码重置的方法即可
        String userAccount = userForm.getAccount();
        // 查询已经存在的用户信息
        UserDO userDO = this.userService.findByUserAccount(userAccount);

        if(StringUtils.isNotEmpty(userForm.getName())){
            userDO.setName(userForm.getName());
        }
        userDO.setGender(Byte.valueOf(String.valueOf(userForm.getGender())));
        userDO.setGmtUpdater(sassUserInfo.getAccount());

        boolean result = this.userService.updateUser(userDO);
        return result;
    }


    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions("/user/delete")
    public Object deleteUserInfo(HttpServletRequest request, @RequestParam(value = "accounts[]") List<String> accounts){

        if(CollectionUtils.isEmpty(accounts)){
            throw new BusinessException(SassBizResultCodeEnum.PARAMETER_NULL,"删除用户参数不能为空","删除用户参数不能为空");
        }

        // FIXME: 删除的时候同时需要清除token等缓存信息

        this.userService.deleteUserByAccounts(accounts);
        accounts.stream().forEach(account->{
            userAclTokenRepository.cleanSassUserTokenCache(account);
            userAclTokenRepository.cleanSassUserInfoCache(account);
        });
        return SassBizResultCodeEnum.SUCCESS;

    }



    /**
     * 用户角色授权接口
     * @param request
     * @param grantForm
     * @return
     */
    @RequestMapping(value = "/grant",method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions("/user/grant")
    public Object grantRoleUserInfo(HttpServletRequest request,
                                    @RequestBody UserRoleGrantForm grantForm){
        if(null == grantForm){
            throw new BusinessException(SassBizResultCodeEnum.PARAMETER_NULL,"用户信息不能为空","用户信息不能为空");
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
            // 缓存信息以及存在用户登陆，在需要更新用户权限值
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
