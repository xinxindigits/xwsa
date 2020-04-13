package cn.com.xinxin.portal.web.controller;

import cn.com.xinxin.oplog.client.enums.AppProductEnum;
import cn.com.xinxin.oplog.client.enums.OperationTypeEnum;
import cn.com.xinxin.portal.biz.service.RoleResourceService;
import cn.com.xinxin.portal.common.Page;
import cn.com.xinxin.portal.common.enums.BizResultCodeEnum;
import cn.com.xinxin.portal.repository.model.RoleResourceDO;
import cn.com.xinxin.portal.session.annotation.RequirePermission;
import cn.com.xinxin.portal.session.controller.AclController;
import cn.com.xinxin.portal.session.model.PortalUser;
import cn.com.xinxin.portal.web.convert.PortalFormConvert;
import cn.com.xinxin.portal.web.form.RoleResourceForm;
import cn.com.xinxin.portal.web.utils.PortalOplogUtil;
import com.xinxinfinance.commons.exception.BusinessException;
import com.xinxinfinance.commons.portal.view.result.PortalPageViewResultVO;
import com.xinxinfinance.commons.portal.view.result.PortalSingleViewResultVO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by dengyunhui on 2018/5/9
 **/
@Controller
@RequestMapping("/permission/roleResource")
public class RoleResourceController extends AclController {

    private static final Logger log = LoggerFactory.getLogger(RoleResourceController.class);

    @Autowired
    private RoleResourceService roleResourceService;

    @RequirePermission("/permission/roleResource")
    @RequestMapping(method = RequestMethod.GET)
    public String index(HttpServletRequest request, Model model){
        return "roleResource/roleResource";
    }

    @RequestMapping(method = RequestMethod.POST,value = "list")
    @ResponseBody
    @RequirePermission("/permission/roleResource/list")
    public PortalPageViewResultVO list(HttpServletRequest request, @RequestParam Integer limit,
                                       @RequestParam Integer pageIndex,
                                       @RequestParam String roleName,
                                       @RequestParam String resourceName){
        log.info("RoleResourceController.list,limit:{},pageIndex:{},roleName:{},resourceName:{}",
            limit,pageIndex,roleName,resourceName);

        Page page = new Page();
        page.setPageSize(limit);
        page.setPageNumber(pageIndex);

        RoleResourceDO roleResourceDO = new RoleResourceDO();
        roleResourceDO.setResourceName(resourceName);
        roleResourceDO.setRoleName(roleName);
        Page<RoleResourceDO> pageResult = roleResourceService.findByConditionPage(page,roleResourceDO);

        PortalPageViewResultVO portalPageViewResultVO = new PortalPageViewResultVO();
        portalPageViewResultVO.setPageNumber(pageResult.getPageNumber());
        portalPageViewResultVO.setPageSize(pageResult.getPageSize());
        portalPageViewResultVO.setRows(pageResult.getRows());
        portalPageViewResultVO.setTotal(pageResult.getTotal());
        return portalPageViewResultVO;

    }

    @RequestMapping(value = "/update",method = RequestMethod.POST)
    @RequirePermission("/permission/roleResource/update")
    @ResponseBody
    public PortalSingleViewResultVO update(HttpServletRequest request, @RequestBody RoleResourceForm roleResourceForm){

        log.info("RoleResourceController.update,roleResourceForm = {}", roleResourceForm);


        PortalUser portalUser = this.getPortalUser(request);

        RoleResourceDO roleResourceDO = PortalFormConvert.convertRRForm2RoleResourceDO(roleResourceForm);

        roleResourceDO.setGmtUpdater(portalUser.getAccount());

        roleResourceService.updateRoleResource(roleResourceDO);

        PortalOplogUtil.logReq(
            AppProductEnum.XPORTAL_MODIFY_ROLE_RESOURCE,
            OperationTypeEnum.UPDATE,
            portalUser.getAccount(),
            portalUser.getNo(),
            Long.toString(roleResourceForm.getId()),
            portalUser.getIp(),
            portalUser.getDevice(),
            roleResourceForm);

        return PortalSingleViewResultVO.success(null);
    }

    @RequestMapping(value = "/create",method = RequestMethod.POST)
    @RequirePermission("/permission/roleResource/create")
    @ResponseBody
    public PortalSingleViewResultVO create(HttpServletRequest request, @RequestBody RoleResourceForm roleResourceForm){

        log.info("RoleResourceController.create,roleResourceForm:{}",roleResourceForm);

        PortalUser portalUser = this.getPortalUser(request);

        RoleResourceDO roleResourceDO = PortalFormConvert.convertRRForm2RoleResourceDO(roleResourceForm);

        roleResourceDO.setGmtCreator(portalUser.getAccount());
        roleResourceDO.setGmtUpdater(portalUser.getAccount());

        RoleResourceDO resourceDO = roleResourceService.createRoleResource(roleResourceDO);

        PortalOplogUtil.logReq(
            AppProductEnum.XPORTAL_ADD_ROLE_RESOURCE,
            OperationTypeEnum.ADD,
            portalUser.getAccount(),
            portalUser.getNo(),
            Long.toString(roleResourceForm.getId()),
            portalUser.getIp(),
            portalUser.getDevice(),
            roleResourceForm);

        return PortalSingleViewResultVO.result(null,resourceDO == null,"操作失败");
    }

    @RequestMapping(value = "/createRoleResources",method = RequestMethod.POST)
    @RequirePermission("/permission/roleResource/createRoleResources")
    @ResponseBody
    public PortalSingleViewResultVO createRoleResources(HttpServletRequest request, @RequestBody List<RoleResourceForm> resourceForms){

        log.info("RoleResourceController.createRoleResources,resourceForms:{}",resourceForms);

        PortalUser portalUser = this.getPortalUser(request);

        List<RoleResourceDO> roleResourceDOS = PortalFormConvert.convertRRForm2RoleResourceDOList(resourceForms);

        // 设置用户
        roleResourceDOS.stream().forEach(roleResourceDO -> {
            roleResourceDO.setGmtCreator(portalUser.getAccount());
            roleResourceDO.setGmtUpdater(portalUser.getAccount());
        });

        roleResourceService.createRoleResources(roleResourceDOS);

        PortalOplogUtil.logReq(
            AppProductEnum.XPORTAL_ADD_ROLE_RESOURCE,
            OperationTypeEnum.ADD,
            portalUser.getAccount(),
            portalUser.getNo(),
            "createRoleResources",
            portalUser.getIp(),
            portalUser.getDevice(),
            resourceForms);

        return PortalSingleViewResultVO.success(null);
    }

    @RequestMapping(value = "/findById/{id}",method = RequestMethod.GET)
    @ResponseBody
    public PortalSingleViewResultVO findById(HttpServletRequest request,@PathVariable Long id){
        log.info("RoleResourceController.findById,id:{}",id);
        RoleResourceDO resourceDO = roleResourceService.findById(id);
        return PortalSingleViewResultVO.success(resourceDO);
    }

    @RequestMapping(value = "/delete/{id}",method = RequestMethod.GET)
    @RequirePermission("/permission/roleResource/delete")
    @ResponseBody
    public PortalSingleViewResultVO delete(HttpServletRequest request,@PathVariable Long id){

        log.info("RoleResourceController.delete,id:{}",id);


        PortalUser portalUser = this.getPortalUser(request);

        Boolean rst = roleResourceService.deleteRoleResource(id);

        PortalOplogUtil.logReq(
            AppProductEnum.XPORTAL_DELETE_ROLE_RESOURCE,
            OperationTypeEnum.DELETE,
            portalUser.getAccount(),
            portalUser.getNo(),
            Long.toString(id),
            portalUser.getIp(),
            portalUser.getDevice(),
            id);

        return PortalSingleViewResultVO.result(null,!rst,"操作失败");
    }


    private void checkRoleResourceFormParameter(final RoleResourceForm roleResourceForm){
        // 必要的参数验证
        if(null == roleResourceForm){
            throw new BusinessException(BizResultCodeEnum.PARAMETER_NULL,"角色信息参数不能为空");
        }

        if(StringUtils.isEmpty(roleResourceForm.getRoleCode())){
            throw new BusinessException(BizResultCodeEnum.PARAMETER_NULL,"角色账户信息参数不能为空");
        }

    }
}
