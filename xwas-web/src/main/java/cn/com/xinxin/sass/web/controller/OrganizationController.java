package cn.com.xinxin.sass.web.controller;

import cn.com.xinxin.oplog.client.enums.AppProductEnum;
import cn.com.xinxin.oplog.client.enums.OperationTypeEnum;
import cn.com.xinxin.sass.biz.service.OrganizationService;
import cn.com.xinxin.sass.common.Page;
import cn.com.xinxin.sass.repository.model.OrganizationDO;
import cn.com.xinxin.sass.session.annotation.RequirePermission;
import cn.com.xinxin.sass.session.controller.AclController;
import cn.com.xinxin.sass.session.model.PortalUser;
import cn.com.xinxin.sass.web.convert.PortalFormConvert;
import cn.com.xinxin.sass.web.form.OrganizationForm;
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
 * Created by dengyunhui on 2018/5/10
 **/
@Controller
@RequestMapping("/permission/organization")
public class OrganizationController extends AclController {

    private static final Logger log = LoggerFactory.getLogger(OrganizationController.class);

    @Autowired
    private OrganizationService organizationService;

    @RequestMapping(method = RequestMethod.GET)
    @RequirePermission("/permission/organization")
    public String index(Model model,HttpServletRequest request){
        return "organization/organization";
    }

    @RequestMapping(method = RequestMethod.POST,value = "list")
    @ResponseBody
    @RequirePermission("/permission/organization/list")
    public PortalPageViewResultVO list(HttpServletRequest request,
                                       @RequestParam Integer limit,
                                       @RequestParam Integer pageIndex,
                                       @RequestParam String code,
                                       @RequestParam String orgType,
                                       @RequestParam String name){

        log.info("OrganizationController.list,limit:{},pageIndex:{},code:{},orgType:{},name:{}",
            limit,pageIndex,code,orgType,name);

        //ShiroUser user = this.getShiroUser(request);

        Page page = new Page();
        page.setPageSize(limit);
        page.setPageNumber(pageIndex);

        OrganizationDO condition = new OrganizationDO();
        condition.setCode(code);
        condition.setName(name);
        condition.setOrgType(orgType);

        Page result = organizationService.findByCondition(page, condition);

        PortalPageViewResultVO portalPageViewResultVO = new PortalPageViewResultVO();
        portalPageViewResultVO.setPageNumber(result.getPageNumber());
        portalPageViewResultVO.setPageSize(result.getPageSize());
        portalPageViewResultVO.setRows(result.getRows());
        portalPageViewResultVO.setTotal(result.getTotal());
        log.info("result:{}",result);

        return portalPageViewResultVO;
    }

    @RequirePermission("/permission/organization/create")
    @RequestMapping(value = "/create",method = RequestMethod.POST)
    @ResponseBody
    public PortalSingleViewResultVO create(HttpServletRequest request, @RequestBody OrganizationForm organizationForm){

        log.info("OrganizationController.create,organizationForm :{}",organizationForm);

        PortalUser portalUser = this.getPortalUser(request);

        OrganizationDO organizationDO = PortalFormConvert.convertOrgForm2OrganizationDO(organizationForm);

        organizationDO.setGmtCreator(portalUser.getAccount());
        organizationDO.setGmtUpdater(portalUser.getAccount());

        OrganizationDO createResultDO = organizationService.createOrganization(organizationDO);

        PortalOplogUtil.logReq(
            AppProductEnum.XPORTAL_ADD_ORGANIZATION,
            OperationTypeEnum.ADD,
            portalUser.getAccount(),
            portalUser.getNo(),
            organizationForm.getCode(),
            portalUser.getIp(),
            portalUser.getDevice(),
            organizationForm);

        return PortalSingleViewResultVO.result(null,!(createResultDO != null),"操作失败");
    }

    @RequestMapping(value = "/findById/{id}",method = RequestMethod.GET)
    @ResponseBody
    public PortalSingleViewResultVO findById(HttpServletRequest request, @PathVariable Long id){
        log.info("OrganizationController.findById,id:{}",id);

        OrganizationDO organizationDO = organizationService.findById(id);
        return PortalSingleViewResultVO.success(organizationDO);
    }

    @RequirePermission("/permission/organization/update")
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    @ResponseBody
    public PortalSingleViewResultVO update(HttpServletRequest request, @RequestBody OrganizationForm organizationForm){

        log.info("OrganizationController.create,organizationForm :{}",organizationForm);

        PortalUser portalUser = this.getPortalUser(request);

        OrganizationDO organizationDO = PortalFormConvert.convertOrgForm2OrganizationDO(organizationForm);

        organizationDO.setGmtUpdater(portalUser.getAccount());

        OrganizationDO organizationDO1 = organizationService.updateOrganization(organizationDO);

        PortalOplogUtil.logReq(
            AppProductEnum.XPORTAL_MODIFY_ORGANIZATION,
            OperationTypeEnum.UPDATE,
            portalUser.getAccount(),
            portalUser.getNo(),
            organizationForm.getCode(),
            portalUser.getIp(),
            portalUser.getDevice(),
            organizationForm);

        return PortalSingleViewResultVO.result(null,organizationDO1 == null,"操作失败");
    }

    @RequirePermission("/permission/organization/delete")
    @RequestMapping(value = "/delete/{id}",method = RequestMethod.GET)
    @ResponseBody
    public PortalSingleViewResultVO delete(HttpServletRequest request, @PathVariable Long id){

        log.info("OrganizationController.delete,id:{}",id);

        PortalUser portalUser = this.getPortalUser(request);

        Boolean rst = organizationService.deleteById(id);

        PortalOplogUtil.logReq(
            AppProductEnum.XPORTAL_DELETE_ORGANIZATION,
            OperationTypeEnum.DELETE,
            portalUser.getAccount(),
            portalUser.getNo(),
            Long.toString(id),
            portalUser.getIp(),
            portalUser.getDevice(),
            id);

        return PortalSingleViewResultVO.result(null,!rst,"操作失败");
    }

}
