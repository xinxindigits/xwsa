package cn.com.xinxin.sass.web.rest;

import cn.com.xinxin.sass.auth.model.SassUserInfo;
import cn.com.xinxin.sass.auth.web.AclController;
import cn.com.xinxin.sass.biz.service.RoleService;
import cn.com.xinxin.sass.biz.service.UserRoleService;
import cn.com.xinxin.sass.common.enums.SassBizResultCodeEnum;
import cn.com.xinxin.sass.common.model.PageResultVO;
import cn.com.xinxin.sass.repository.model.RoleDO;
import cn.com.xinxin.sass.repository.model.UserRoleDO;
import cn.com.xinxin.sass.web.convert.SassFormConvert;
import cn.com.xinxin.sass.web.form.RoleAuthorityForm;
import cn.com.xinxin.sass.web.form.RoleForm;
import cn.com.xinxin.sass.web.vo.RoleVO;
import com.xinxinfinance.commons.util.BaseConvert;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
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

    @RequestMapping(value = "/create",method = RequestMethod.POST)
    @RequiresPermissions("/role/create")
    public Object createRole(@RequestBody RoleForm roleForm, HttpServletRequest request){

        RoleDO roleDO = SassFormConvert.convertRoleForm2RoleDO(roleForm);
        roleDO.setId(null);
        SassUserInfo sassUserInfo = this.getSassUser(request);
        roleDO.setGmtCreator(sassUserInfo.getAccount());
        roleDO.setGmtUpdater(sassUserInfo.getAccount());
        roleService.createRole(roleDO);

        return SassBizResultCodeEnum.SUCCESS.getAlertMessage();
    }

    @RequestMapping(value = "/delete/{roleId}",method = RequestMethod.DELETE)
    @RequiresPermissions("/role/delete")
    public Object deleteRole(@PathVariable Long roleId, HttpServletRequest request){

        RoleDO roleDO = new RoleDO();
        SassUserInfo sassUserInfo = this.getSassUser(request);
        roleDO.setGmtUpdater(sassUserInfo.getAccount());
        roleDO.setId(roleId);
        roleDO.setDeleted(true);
        roleService.updateRole(roleDO);

        return SassBizResultCodeEnum.SUCCESS.getAlertMessage();
    }

    @RequestMapping(value = "/update",method = RequestMethod.GET)
    @RequiresPermissions("/role/update")
    public Object updateRole(@RequestBody RoleForm roleForm, HttpServletRequest request){

        RoleDO roleDO = SassFormConvert.convertRoleForm2RoleDO(roleForm);
        SassUserInfo sassUserInfo = this.getSassUser(request);
        roleDO.setGmtUpdater(sassUserInfo.getAccount());
        roleService.updateRole(roleDO);

        return SassBizResultCodeEnum.SUCCESS.getAlertMessage();
    }

    @RequestMapping(value = "/query/{roleId}",method = RequestMethod.GET)
    @ResponseBody
    @RequiresPermissions("/user/query")
    public Object pageQueryUser(@PathVariable Long roleId, HttpServletRequest request){

        RoleDO roleDO = roleService.findOne(roleId);
        roleDO.setDeleted(false);
        RoleVO roleVo = BaseConvert.convert(roleDO, RoleVO.class);
        return roleVo;
    }

    @RequestMapping(value = "/query/page",method = RequestMethod.POST)
    @RequiresPermissions("/role/query")
    public Object pageQueryRole(@RequestBody RoleForm roleForm, HttpServletRequest request){

        RoleDO roleDO = SassFormConvert.convertRoleForm2RoleDO(roleForm);
        roleDO.setDeleted(false);
        PageResultVO page = new PageResultVO();
        page.setPageNumber((roleForm.getPageNum() == null) ? PageResultVO.DEFAULT_PAGE_NUM : roleForm.getPageNum());
        page.setPageSize((roleForm.getPageSize() == null) ? PageResultVO.DEFAULT_PAGE_SIZE : roleForm.getPageSize());
        PageResultVO<RoleDO> pageRole = roleService.findByConditionPage(page, roleDO);

        return pageRole;
    }

    @RequestMapping(value = "/authority",method = RequestMethod.POST)
    @RequiresPermissions("/role/grant")
    @Transactional(rollbackFor = Exception.class)
    public Object authority(@RequestBody RoleAuthorityForm roleAuthorityForm, HttpServletRequest request){
        SassUserInfo sassUserInfo = this.getSassUser(request);
        userRoleService.deleteByRoleCode(roleAuthorityForm.getRoleCode());
        List<UserRoleDO> userRoleDOList = roleAuthorityForm.getUserList().stream().map(user -> {
            UserRoleDO userRoleDO = new UserRoleDO();
            userRoleDO.setUserAccount(user.getAccount());
            userRoleDO.setUserName(user.getName());
            userRoleDO.setExtension(user.getExtension());
            userRoleDO.setRoleName(roleAuthorityForm.getRoleName() );
            userRoleDO.setRoleCode(roleAuthorityForm.getRoleCode());
            userRoleDO.setGmtCreator(sassUserInfo.getAccount());
            userRoleDO.setGmtUpdater(sassUserInfo.getAccount());
            return userRoleDO;
        }).collect(Collectors.toList());
        userRoleService.createUserRoles(userRoleDOList);
        return SassBizResultCodeEnum.SUCCESS.getAlertMessage();
    }
}
