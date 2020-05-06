package cn.com.xinxin.sass.web.rest;

import cn.com.xinxin.sass.auth.model.SassUserInfo;
import cn.com.xinxin.sass.auth.web.AclController;
import cn.com.xinxin.sass.biz.service.OrganizationService;
import cn.com.xinxin.sass.biz.service.TenantBaseInfoService;
import cn.com.xinxin.sass.common.enums.SassBizResultCodeEnum;
import cn.com.xinxin.sass.common.model.PageResultVO;
import cn.com.xinxin.sass.repository.model.OrganizationDO;
import cn.com.xinxin.sass.repository.model.TenantBaseInfoDO;
import cn.com.xinxin.sass.web.convert.SassFormConvert;
import cn.com.xinxin.sass.web.form.DeleteOrgForm;
import cn.com.xinxin.sass.web.form.OrgQueryForm;
import cn.com.xinxin.sass.web.form.OrganizationForm;
import cn.com.xinxin.sass.web.vo.CustomerVO;
import cn.com.xinxin.sass.web.vo.MemberVO;
import cn.com.xinxin.sass.web.vo.OrganizationVO;
import cn.com.xinxin.sass.web.vo.TenantInfoVO;
import com.xinxinfinance.commons.exception.BusinessException;
import com.xinxinfinance.commons.idgen.SnowFakeIdGenerator;
import com.xinxinfinance.commons.util.BaseConvert;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author: zhouyang
 * @created: 17/04/2020.
 * @updater:
 * @description: 租户管理接口
 */
@RestController
@RequestMapping(value = "/tenant",produces = "application/json; charset=UTF-8")
public class SassTenantRestController extends AclController {

    private static final Logger loger = LoggerFactory.getLogger(SassTenantRestController.class);

    private final OrganizationService organizationService;

    private final TenantBaseInfoService tenantBaseInfoService;

    private static final String OG = "OG";

    private static final String DATE_FORMAT_NOSIGN = "yyyyMMdd";

    private static final String PADDING = "000";

    public SassTenantRestController(OrganizationService organizationService, TenantBaseInfoService tenantBaseInfoService) {
        this.organizationService = organizationService;
        this.tenantBaseInfoService = tenantBaseInfoService;
    }

    @RequestMapping(value = "/list",method = RequestMethod.POST)
    @ResponseBody
//    @RequiresPermissions("/tenant/list")
    public Object tenantList(HttpServletRequest request, @RequestBody OrgQueryForm orgForm){

        if(null == orgForm){
            throw new BusinessException(SassBizResultCodeEnum.PARAMETER_NULL,"租户查询参数不能为空","租户查询参数不能为空");
        }

        loger.info("SassTenantRestController,tenantList, orgName = {}",orgForm.getName());

        SassUserInfo sassUserInfo = this.getSassUser(request);
        // 参数转换设置

        PageResultVO page = new PageResultVO();
        page.setPageSize(orgForm.getPageSize());
        page.setPageNumber(orgForm.getPageIndex());

        PageResultVO<TenantBaseInfoDO> result = tenantBaseInfoService.listAllTenants(page);

        PageResultVO<TenantInfoVO> results = BaseConvert.convert(result,PageResultVO.class);

        List<TenantInfoVO> tenantInfoVOS = results.getItems();

        results.setItems(tenantInfoVOS);

        return result;

    }


}
