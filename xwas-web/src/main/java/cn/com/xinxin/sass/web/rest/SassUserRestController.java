package cn.com.xinxin.sass.web.rest;


import cn.com.xinxin.sass.auth.model.SassUserInfo;
import cn.com.xinxin.sass.biz.service.UserService;
import cn.com.xinxin.sass.common.enums.SassBizResultCodeEnum;
import cn.com.xinxin.sass.repository.model.ResourceDO;
import cn.com.xinxin.sass.repository.model.RoleDO;
import cn.com.xinxin.sass.repository.model.UserDO;
import cn.com.xinxin.sass.auth.web.AclController;
import cn.com.xinxin.sass.web.convert.SassFormConvert;
import cn.com.xinxin.sass.web.form.UserForm;
import cn.com.xinxin.sass.web.form.UserRoleForm;
import cn.com.xinxin.sass.web.vo.ResourceVO;
import cn.com.xinxin.sass.web.vo.RoleVO;
import cn.com.xinxin.sass.web.vo.UserInfoVO;
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
import java.util.List;

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

        // 更新用户信息不能更新用户密码以及账号信息，如果需要更新密码，走密码重置的方法即可
        String userAccount = userForm.getAccount();
        // 查询已经存在的用户信息
        UserDO userDO = this.userService.findByUserAccount(userAccount);

        if(StringUtils.isNotEmpty(userForm.getName())){
            userDO.setName(userForm.getName());
        }
        userDO.setGender(Byte.valueOf(String.valueOf(userForm.getGender())));
        boolean result = this.userService.updateUser(userDO);
        return result;
    }


    /**
     * 用户角色授权接口
     * @param request
     * @param userRoleForm
     * @return
     */
    @RequestMapping(value = "/grant",method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions("/user/grant")
    public Object grantRoleUserInfo(HttpServletRequest request, @RequestBody UserRoleForm userRoleForm){

        return null;

    }


}
