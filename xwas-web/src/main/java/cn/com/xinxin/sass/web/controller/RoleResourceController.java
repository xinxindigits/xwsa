package cn.com.xinxin.sass.web.controller;

import cn.com.xinxin.sass.biz.service.RoleResourceService;
import cn.com.xinxin.sass.common.Page;
import cn.com.xinxin.sass.common.enums.SassBizResultCodeEnum;
import cn.com.xinxin.sass.repository.model.RoleResourceDO;
import cn.com.xinxin.sass.auth.annotation.RequirePermission;
import cn.com.xinxin.sass.auth.web.AclController;
import cn.com.xinxin.sass.auth.model.SassUserInfo;
import cn.com.xinxin.sass.web.convert.PortalFormConvert;
import cn.com.xinxin.sass.web.form.RoleResourceForm;
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


        SassUserInfo sassUserInfo = this.getSassUser(request);

        RoleResourceDO roleResourceDO = PortalFormConvert.convertRRForm2RoleResourceDO(roleResourceForm);

        roleResourceDO.setGmtUpdater(sassUserInfo.getAccount());

        roleResourceService.updateRoleResource(roleResourceDO);

        return PortalSingleViewResultVO.success(null);
    }

    @RequestMapping(value = "/create",method = RequestMethod.POST)
    @RequirePermission("/permission/roleResource/create")
    @ResponseBody
    public PortalSingleViewResultVO create(HttpServletRequest request, @RequestBody RoleResourceForm roleResourceForm){

        log.info("RoleResourceController.create,roleResourceForm:{}",roleResourceForm);

        SassUserInfo sassUserInfo = this.getSassUser(request);

        RoleResourceDO roleResourceDO = PortalFormConvert.convertRRForm2RoleResourceDO(roleResourceForm);

        roleResourceDO.setGmtCreator(sassUserInfo.getAccount());
        roleResourceDO.setGmtUpdater(sassUserInfo.getAccount());

        RoleResourceDO resourceDO = roleResourceService.createRoleResource(roleResourceDO);


        return PortalSingleViewResultVO.result(null,resourceDO == null,"操作失败");
    }

    @RequestMapping(value = "/createRoleResources",method = RequestMethod.POST)
    @RequirePermission("/permission/roleResource/createRoleResources")
    @ResponseBody
    public PortalSingleViewResultVO createRoleResources(HttpServletRequest request, @RequestBody List<RoleResourceForm> resourceForms){

        log.info("RoleResourceController.createRoleResources,resourceForms:{}",resourceForms);

        SassUserInfo sassUserInfo = this.getSassUser(request);

        List<RoleResourceDO> roleResourceDOS = PortalFormConvert.convertRRForm2RoleResourceDOList(resourceForms);

        // 设置用户
        roleResourceDOS.stream().forEach(roleResourceDO -> {
            roleResourceDO.setGmtCreator(sassUserInfo.getAccount());
            roleResourceDO.setGmtUpdater(sassUserInfo.getAccount());
        });

        roleResourceService.createRoleResources(roleResourceDOS);


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


        SassUserInfo sassUserInfo = this.getSassUser(request);

        Boolean rst = roleResourceService.deleteRoleResource(id);
        

        return PortalSingleViewResultVO.result(null,!rst,"操作失败");
    }


    private void checkRoleResourceFormParameter(final RoleResourceForm roleResourceForm){
        // 必要的参数验证
        if(null == roleResourceForm){
            throw new BusinessException(SassBizResultCodeEnum.PARAMETER_NULL,"角色信息参数不能为空");
        }

        if(StringUtils.isEmpty(roleResourceForm.getRoleCode())){
            throw new BusinessException(SassBizResultCodeEnum.PARAMETER_NULL,"角色账户信息参数不能为空");
        }

    }
}
