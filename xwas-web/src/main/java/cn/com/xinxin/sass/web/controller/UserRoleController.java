package cn.com.xinxin.sass.web.controller;

import cn.com.xinxin.oplog.client.enums.AppProductEnum;
import cn.com.xinxin.oplog.client.enums.OperationTypeEnum;
import cn.com.xinxin.sass.biz.service.UserRoleService;
import cn.com.xinxin.sass.common.Page;
import cn.com.xinxin.sass.repository.model.UserRoleDO;
import cn.com.xinxin.sass.auth.annotation.RequirePermission;
import cn.com.xinxin.sass.auth.web.AclController;
import cn.com.xinxin.sass.auth.model.SassUserInfo;
import cn.com.xinxin.sass.web.convert.PortalFormConvert;
import cn.com.xinxin.sass.web.form.UserRoleForm;
import cn.com.xinxin.sass.web.utils.PortalOplogUtil;
import com.xinxinfinance.commons.portal.view.result.PortalPageViewResultVO;
import com.xinxinfinance.commons.portal.view.result.PortalSingleViewResultVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by dengyunhui on 2018/5/9
 **/
@Controller
@RequestMapping("/permission/userRole")
public class UserRoleController extends AclController {

    private static final Logger log = LoggerFactory.getLogger(UserRoleController.class);

    @Autowired
    private UserRoleService userRoleService;

    @RequirePermission("/permission/userRole")
    @RequestMapping(method = RequestMethod.GET)
    public String index(HttpServletRequest request, Model model){
        return "userRole/userRole";
    }

    @RequestMapping(method = RequestMethod.POST,value = "list")
    @ResponseBody
    @RequirePermission("/permission/userRole/list")
    public PortalPageViewResultVO list(HttpServletRequest request, @RequestParam Integer limit,
                                       @RequestParam Integer pageIndex,
                                       @RequestParam String roleName,
                                       @RequestParam String userName){
        log.info("UserRoleController.list,limit:{},pageIndex:{},roleName:{},userName:{}",limit,pageIndex,roleName,userName);

        Page page = new Page();
        page.setPageSize(limit);
        page.setPageNumber(pageIndex);

        UserRoleDO condition = new UserRoleDO();
        condition.setUserName(userName);
        condition.setRoleName(roleName);

        Page<UserRoleDO> page1 = userRoleService.findByConditionPage(page,condition);
        PortalPageViewResultVO portalPageViewResultVO = new PortalPageViewResultVO();
        portalPageViewResultVO.setPageNumber(page1.getPageNumber());
        portalPageViewResultVO.setPageSize(page1.getPageSize());
        portalPageViewResultVO.setRows(page1.getRows());
        portalPageViewResultVO.setTotal(page1.getTotal());
        return portalPageViewResultVO;
    }

    @RequirePermission("/permission/userRole/update")
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    @ResponseBody
    public PortalSingleViewResultVO update(HttpServletRequest request, @RequestBody UserRoleForm userRoleForm){

        log.info("UserRoleController.update,userRoleForm:{}",userRoleForm);

        SassUserInfo sassUserInfo = this.getPortalUser(request);

        UserRoleDO userRoleDO = PortalFormConvert.convertUserRoleForm2UserRoleDO(userRoleForm);

        userRoleDO.setGmtUpdater(sassUserInfo.getAccount());

        Boolean rst = userRoleService.updateUserRole(userRoleDO);

        PortalOplogUtil.logReq(
            AppProductEnum.XPORTAL_MODIFY_USER_ROLE,
            OperationTypeEnum.UPDATE,
            sassUserInfo.getAccount(),
            sassUserInfo.getNo(),
            Long.toString(userRoleForm.getId()),
            sassUserInfo.getIp(),
            sassUserInfo.getDevice(),
            userRoleForm);

        return PortalSingleViewResultVO.result(null,!rst,"操作失败");
    }

    @RequirePermission("/permission/userRole/create")
    @RequestMapping(value = "/create",method = RequestMethod.POST)
    @ResponseBody
    public PortalSingleViewResultVO create(HttpServletRequest request,@RequestBody UserRoleForm userRoleForm){

        log.info("UserRoleController.create,userRoleForm={}",userRoleForm);

        SassUserInfo sassUserInfo = this.getPortalUser(request);

        UserRoleDO userRoleDO = PortalFormConvert.convertUserRoleForm2UserRoleDO(userRoleForm);

        userRoleDO.setGmtCreator(sassUserInfo.getAccount());
        userRoleDO.setGmtUpdater(sassUserInfo.getAccount());

        UserRoleDO userRoleDOCreate = userRoleService.createUserRole(userRoleDO);

        PortalOplogUtil.logReq(
            AppProductEnum.XPORTAL_ADD_USER_ROLE,
            OperationTypeEnum.ADD,
            sassUserInfo.getAccount(),
            sassUserInfo.getNo(),
            Long.toString(userRoleForm.getId()),
            sassUserInfo.getIp(),
            sassUserInfo.getDevice(),
            userRoleForm);

        return PortalSingleViewResultVO.result(null,userRoleDOCreate == null,"操作失败");
    }

    @RequestMapping(value = "/findById/{id}",method = RequestMethod.GET)
    @ResponseBody
    public PortalSingleViewResultVO findById(HttpServletRequest request,@PathVariable Long id){
        log.info("UserRoleController.findById,id:{}",id);
        UserRoleDO userRoleDO = userRoleService.findById(id);
        return PortalSingleViewResultVO.success(userRoleDO);
    }

    @RequirePermission("/permission/userRole/delete")
    @RequestMapping(value = "/delete/{id}",method = RequestMethod.GET)
    @ResponseBody
    public PortalSingleViewResultVO delete(HttpServletRequest request,@PathVariable Long id){

        log.info("UserRoleController.delete,id:{}",id);
        SassUserInfo sassUserInfo = this.getPortalUser(request);

        Boolean rst = userRoleService.deleteUserRole(id);

        PortalOplogUtil.logReq(
            AppProductEnum.XPORTAL_DELETE_USER_ROLE,
            OperationTypeEnum.DELETE,
            sassUserInfo.getAccount(),
            sassUserInfo.getNo(),
            Long.toString(id),
            sassUserInfo.getIp(),
            sassUserInfo.getDevice(),
            id);

        return PortalSingleViewResultVO.result(null,!rst,"操作失败");
    }
}
