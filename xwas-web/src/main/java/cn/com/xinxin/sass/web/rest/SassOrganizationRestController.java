package cn.com.xinxin.sass.web.rest;

import cn.com.xinxin.sass.auth.model.SassUserInfo;
import cn.com.xinxin.sass.auth.web.AclController;
import cn.com.xinxin.sass.biz.service.OrganizationService;
import cn.com.xinxin.sass.common.enums.SassBizResultCodeEnum;
import cn.com.xinxin.sass.repository.model.OrganizationDO;
import cn.com.xinxin.sass.web.convert.SassFormConvert;
import cn.com.xinxin.sass.web.form.OrganizationForm;
import com.xinxinfinance.commons.exception.BusinessException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: zhouyang
 * @created: 17/04/2020.
 * @updater:
 * @description: 组织机构管理接口类
 */
@RestController
@RequestMapping(value = "/organization",produces = "application/json; charset=UTF-8")
public class SassOrganizationRestController extends AclController {

    private static final Logger loger = LoggerFactory.getLogger(SassOrganizationRestController.class);

    private final OrganizationService organizationService;

    public SassOrganizationRestController(OrganizationService organizationService) {
        this.organizationService = organizationService;
    }



    @RequestMapping(value = "/create",method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions("/organization/create")
    public Object createOrganization(HttpServletRequest request,
                                     @RequestBody OrganizationForm orgForm){

        if(null == orgForm){
            throw new BusinessException(SassBizResultCodeEnum.PARAMETER_NULL,"创建组织参数不能为空","创建组织参数不能为空");
        }

        loger.info("SassOrganizationRestController,createOrganization, orgName = {}",orgForm.getName());

        SassUserInfo sassUserInfo = this.getSassUser(request);
        // 参数转换设置
        OrganizationDO organizationDO = SassFormConvert.convertOrgForm2OrganizationDO(orgForm);
        organizationDO.setGmtCreator(sassUserInfo.getAccount());
        organizationDO.setGmtUpdater(sassUserInfo.getAccount());

        // 创建对象
        this.organizationService.createOrganization(organizationDO);

        return SassBizResultCodeEnum.SUCCESS.getAlertMessage();

    }


    @RequestMapping(value = "/update",method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions("/organization/update")
    public Object updateOrganization(HttpServletRequest request,
                                     @RequestBody OrganizationForm orgForm){

        if(null == orgForm){
            throw new BusinessException(SassBizResultCodeEnum.PARAMETER_NULL,"创建组织参数不能为空","创建组织参数不能为空");
        }

        loger.info("SassOrganizationRestController,updateOrganization, orgName = {}",orgForm.getName());

        SassUserInfo sassUserInfo = this.getSassUser(request);
        // 参数转换设置
        OrganizationDO organizationDO = SassFormConvert.convertOrgForm2OrganizationDO(orgForm);
     
        organizationDO.setGmtUpdater(sassUserInfo.getAccount());

        // 创建对象
        this.organizationService.updateOrganization(organizationDO);

        return SassBizResultCodeEnum.SUCCESS.getAlertMessage();

    }


}
