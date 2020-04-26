package cn.com.xinxin.sass.web.rest;

import cn.com.xinxin.sass.auth.model.SassUserInfo;
import cn.com.xinxin.sass.auth.web.AclController;
import cn.com.xinxin.sass.biz.service.OrgBaseInfoService;
import cn.com.xinxin.sass.biz.service.OrganizationService;
import cn.com.xinxin.sass.common.enums.SassBizResultCodeEnum;
import cn.com.xinxin.sass.common.model.PageResultVO;
import cn.com.xinxin.sass.repository.model.OrgBaseInfoDO;
import cn.com.xinxin.sass.repository.model.OrganizationDO;
import cn.com.xinxin.sass.web.convert.SassFormConvert;
import cn.com.xinxin.sass.web.form.OrgQueryForm;
import cn.com.xinxin.sass.web.form.OrganizationForm;
import cn.com.xinxin.sass.web.vo.OrganizationVO;
import com.xinxinfinance.commons.exception.BusinessException;
import com.xinxinfinance.commons.idgen.SnowFakeIdGenerator;
import com.xinxinfinance.commons.result.BizResultCode;
import com.xinxinfinance.commons.util.BaseConvert;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.xml.crypto.Data;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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

    private final OrgBaseInfoService orgBaseInfoService;

    private static final String OG = "OG";

    private static final String DATE_FORMAT_NOSIGN = "yyyyMMdd";

    private static final String PADDING = "000";

    public SassOrganizationRestController(OrganizationService organizationService, OrgBaseInfoService orgBaseInfoService) {
        this.organizationService = organizationService;
        this.orgBaseInfoService = orgBaseInfoService;
    }

    @RequestMapping(value = "/list",method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions("/organization/list")
    public Object OrganizationList(HttpServletRequest request, @RequestBody OrgQueryForm orgForm){

        if(null == orgForm){
            throw new BusinessException(SassBizResultCodeEnum.PARAMETER_NULL,"组织机构查询参数不能为空","组织机构查询参数");
        }

        loger.info("SassOrganizationRestController,createOrganization, orgName = {}",orgForm.getName());

        SassUserInfo sassUserInfo = this.getSassUser(request);
        // 参数转换设置

        PageResultVO page = new PageResultVO();
        page.setPageSize(orgForm.getPageSize());
        page.setPageNumber(orgForm.getPageIndex());

        OrganizationDO condition = new OrganizationDO();
        condition.setCode(orgForm.getCode());
        condition.setName(orgForm.getName());
        condition.setOrgType(orgForm.getOrgType());

        PageResultVO result = organizationService.findByCondition(page, condition);


        return result;

    }

    @RequestMapping(value = "/query/{code}",method = RequestMethod.GET)
    @ResponseBody
    @RequiresPermissions("/organization/query")
    public Object OrganizationList(HttpServletRequest request, @PathVariable(value = "code")String code){

        if(StringUtils.isEmpty(code)){
            throw new BusinessException(SassBizResultCodeEnum.PARAMETER_NULL,"组织机构查询参数不能为空","组织机构查询参数");
        }

        loger.info("SassOrganizationRestController,createOrganization, code = {}",code);

        //OrganizationDO organizationDO = organizationService.findByCode(code);
        OrgBaseInfoDO orgBaseInfoDO = orgBaseInfoService.selectByOrgId(code);
        OrganizationVO result = BaseConvert.convert(orgBaseInfoDO, OrganizationVO.class);
        result.setCode(orgBaseInfoDO.getOrgId());
        //result.setExtension(organizationDO.getExtension());
        result.setName(orgBaseInfoDO.getOrgName());

        return result;

    }

    /**
     * 获取组织机构的树形结构
     * @param request
     * @return
     */
    @RequestMapping(value = "/routes",method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions("/organization/routes")
    public Object OrganizationRoutes(HttpServletRequest request){


        loger.info("SassOrganizationRestController,createOrganization, orgName = {}");

        SassUserInfo sassUserInfo = this.getSassUser(request);
        // 参数转换设置


        List<OrganizationDO> organizationDOList = this.organizationService.queryOrgList();



        return null;

    }



    @RequestMapping(value = "/create",method = RequestMethod.POST)
    @ResponseBody
    @Transactional(rollbackFor = Exception.class)
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
        StringBuilder code = new StringBuilder();
        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOSIGN);
        code.append(OG).append(sdf.format(now)).append(PADDING);
        try {
            code.append(SnowFakeIdGenerator.getInstance().generateLongId());
        }catch (Exception e){
            loger.error("雪花算法生成id失败");
            throw new BusinessException(SassBizResultCodeEnum.GENERATE_ID_ERROR);
        }
        organizationDO.setCode(code.toString());
        organizationDO.setGmtCreator(sassUserInfo.getAccount());
        organizationDO.setGmtUpdater(sassUserInfo.getAccount());

        // 创建对象
        int result = this.organizationService.createOrganization(organizationDO);

        OrgBaseInfoDO orgBaseInfoDO = BaseConvert.convert(orgForm, OrgBaseInfoDO.class);
        orgBaseInfoDO.setOrgId(code.toString());
        orgBaseInfoDO.setOrgName(orgForm.getName());
        orgBaseInfoDO.setGmtCreator(sassUserInfo.getAccount());
        orgBaseInfoDO.setGmtUpdater(sassUserInfo.getAccount());

        orgBaseInfoService.createOrgBaseInfo(orgBaseInfoDO);

        if(result > 0){
            return SassBizResultCodeEnum.SUCCESS.getAlertMessage();
        }else {
            return SassBizResultCodeEnum.FAIL.getAlertMessage();
        }

    }


    @RequestMapping(value = "/update",method = RequestMethod.POST)
    @ResponseBody
    @Transactional(rollbackFor = Exception.class)
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
        int result = this.organizationService.updateOrganization(organizationDO);

        OrgBaseInfoDO orgBaseInfoDO = BaseConvert.convert(orgForm, OrgBaseInfoDO.class);
        orgBaseInfoDO.setOrgId(orgForm.getCode());
        orgBaseInfoDO.setOrgName(orgForm.getName());
        orgBaseInfoDO.setGmtUpdater(sassUserInfo.getAccount());
        this.orgBaseInfoService.updateByOrgId(orgBaseInfoDO);

        if(result > 0){
            return SassBizResultCodeEnum.SUCCESS.getAlertMessage();
        }else {
            return SassBizResultCodeEnum.FAIL.getAlertMessage();
        }

    }

    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    @ResponseBody
    @Transactional(rollbackFor = Exception.class)
    @RequiresPermissions("/organization/delete")
    public Object deleteOrganization(@RequestParam(value = "codes[]") List<String> codes,HttpServletRequest request){

        if(CollectionUtils.isEmpty(codes)){
            throw new BusinessException(SassBizResultCodeEnum.ILLEGAL_PARAMETER,"组织机构列表不能为空");
        }

        int result = this.organizationService.deleteByCodes(codes);
        this.orgBaseInfoService.deleteByCodes(codes);
        if(result > 0){
            return SassBizResultCodeEnum.SUCCESS.getAlertMessage();
        }else {
            return SassBizResultCodeEnum.FAIL.getAlertMessage();
        }
    }


}
