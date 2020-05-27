package cn.com.xinxin.sass.web.rest;

/*
 *
 * Copyright 2020 www.xinxindigits.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software
 * and associated documentation files (the "Software"),to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice
 * shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 * Redistribution and selling copies of the software are prohibited, only if the authorization from xinxin digits
 * was obtained.Neither the name of the xinxin digits; nor the names of its contributors may be used to
 * endorse or promote products derived from this software without specific prior written permission.
 *
 */

import cn.com.xinxin.sass.auth.model.SassUserInfo;
import cn.com.xinxin.sass.auth.web.AclController;
import cn.com.xinxin.sass.biz.log.SysLog;
import cn.com.xinxin.sass.biz.service.TenantBaseInfoService;
import cn.com.xinxin.sass.biz.service.OrganizationService;
import cn.com.xinxin.sass.biz.service.UserService;
import cn.com.xinxin.sass.common.enums.OrgTypeEnum;
import cn.com.xinxin.sass.common.enums.SassBizResultCodeEnum;
import cn.com.xinxin.sass.common.model.PageResultVO;
import cn.com.xinxin.sass.repository.model.TenantBaseInfoDO;
import cn.com.xinxin.sass.repository.model.OrganizationDO;
import cn.com.xinxin.sass.repository.model.UserOrgDO;
import cn.com.xinxin.sass.web.convert.SassFormConvert;
import cn.com.xinxin.sass.web.form.DeleteOrgForm;
import cn.com.xinxin.sass.web.form.OrgQueryForm;
import cn.com.xinxin.sass.web.form.OrganizationForm;
import cn.com.xinxin.sass.web.utils.RegexUtils;
import cn.com.xinxin.sass.web.utils.TreeResultUtil;
import cn.com.xinxin.sass.web.vo.DeptTreeVO;
import cn.com.xinxin.sass.web.vo.OrgTreeVO;
import cn.com.xinxin.sass.web.vo.OrganizationVO;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.xinxinfinance.commons.exception.BusinessException;
import com.xinxinfinance.commons.idgen.SnowFakeIdGenerator;
import com.xinxinfinance.commons.result.BizResultCode;
import com.xinxinfinance.commons.util.BaseConvert;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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

    private final UserService userService;

    public SassOrganizationRestController(OrganizationService organizationService,
                                          UserService userService) {
        this.organizationService = organizationService;
        this.userService = userService;

    }

    @RequestMapping(value = "/list",method = RequestMethod.POST)
    @ResponseBody
    @RequiresRoles(value = {"sass_admin","admin","sass_mng"},logical= Logical.OR)
    public Object organizationList(HttpServletRequest request, @RequestBody OrgQueryForm orgForm){

        if(null == orgForm){
            throw new BusinessException(SassBizResultCodeEnum.PARAMETER_NULL,"组织机构查询参数不能为空","组织机构查询参数");
        }

        loger.info("SassOrganizationRestController,createOrganization,request:{}",JSONObject.toJSONString(orgForm));

        SassUserInfo sassUserInfo = this.getSassUser(request);
        // 参数转换设置

        PageResultVO page = new PageResultVO();
        page.setPageSize(orgForm.getPageSize());
        page.setPageNumber(orgForm.getPageIndex());

        OrganizationDO condition = BaseConvert.convert(orgForm,OrganizationDO.class);
        condition.setCode(orgForm.getOrgId());
        if(StringUtils.isEmpty(orgForm.getTenantId())){
            condition.setTenantId(sassUserInfo.getTenantId());
        }else{
            condition.setTenantId(orgForm.getTenantId());
        }
        condition.setName(orgForm.getOrgName());
        condition.setParentId(0L);
        PageResultVO<OrganizationDO> result = organizationService.findByCondition(page, condition);
        PageResultVO resultVO =  BaseConvert.convert(result,PageResultVO.class);
        if(!CollectionUtils.isEmpty(result.getItems())){
            List<OrganizationDO> childrenOrganization = organizationService.findNotRoot(condition.getTenantId());
            loger.info(JSONObject.toJSONString(childrenOrganization));
            result.getItems().addAll(childrenOrganization);
            List<OrgTreeVO> orgTreeVOS = Lists.newArrayList();
            result.getItems().stream().forEach(organizationDO -> {
                OrgTreeVO orgTreeVO = new OrgTreeVO();
                orgTreeVO.setCode(organizationDO.getCode());
                orgTreeVO.setTenantId(organizationDO.getTenantId());
                orgTreeVO.setOrgId(String.valueOf(organizationDO.getId()));
                orgTreeVO.setOrgName(organizationDO.getName());
                orgTreeVO.setParentId(String.valueOf(organizationDO.getParentId()));
                orgTreeVO.setOrgType(organizationDO.getOrgType());
                orgTreeVO.setOrgTypeName(OrgTypeEnum.getEnumByCode(organizationDO.getOrgType()).getDesc());
                orgTreeVO.setStatus(organizationDO.getState());
                orgTreeVO.setExtension(organizationDO.getExtension());
                orgTreeVO.setGmtCreated(organizationDO.getGmtCreated());
                orgTreeVO.setGmtUpdated(organizationDO.getGmtUpdated());
                orgTreeVO.setRemark(organizationDO.getRemark());
                orgTreeVOS.add(orgTreeVO);
            });
            List<OrgTreeVO> resultTrees = TreeResultUtil.buildOrgTrees(orgTreeVOS);
            resultVO.setItems(resultTrees);
        }

        return resultVO;

    }

    /**
     * 获取组织机构的树形结构
     * @param request
     * @return
     */
    @RequestMapping(value = "/routes",method = RequestMethod.GET)
    @ResponseBody
    public Object OrganizationRoutes(HttpServletRequest request){

        loger.info("SassOrganizationRestController,OrganizationRoutes");
        SassUserInfo sassUserInfo = this.getSassUser(request);
        // 参数转换设置
        List<OrganizationDO> organizationDOList =
                this.organizationService.queryOrgListByTenantId(sassUserInfo.getTenantId());

        List<OrgTreeVO> orgTreeVOS = Lists.newArrayList();
        organizationDOList.stream().forEach(organizationDO -> {
            OrgTreeVO orgTreeVO = new OrgTreeVO();
            orgTreeVO.setCode(organizationDO.getCode());
            orgTreeVO.setTenantId(organizationDO.getTenantId());
            orgTreeVO.setOrgId(String.valueOf(organizationDO.getId()));
            orgTreeVO.setOrgName(organizationDO.getName());
            orgTreeVO.setParentId(String.valueOf(organizationDO.getParentId()));
            orgTreeVO.setOrgType(organizationDO.getOrgType());
            orgTreeVO.setOrgTypeName(OrgTypeEnum.getEnumByCode(organizationDO.getOrgType()).getDesc());
            orgTreeVO.setStatus(organizationDO.getState());
            orgTreeVO.setRemark(organizationDO.getRemark());
            orgTreeVOS.add(orgTreeVO);
        });
        List<OrgTreeVO> resultTrees = TreeResultUtil.buildOrgTrees(orgTreeVOS);
        return resultTrees;
    }


    @RequestMapping(value = "/create",method = RequestMethod.POST)
    @ResponseBody
    @Transactional(rollbackFor = Exception.class)
    @RequiresRoles(value = {"sass_admin","admin","sass_mng"},logical= Logical.OR)
    @SysLog("创建组织机构操作")
    public Object createOrganization(HttpServletRequest request,
                                     @RequestBody OrganizationForm orgForm){

        if(null == orgForm){
            throw new BusinessException(SassBizResultCodeEnum.PARAMETER_NULL,"创建组织参数不能为空","创建组织参数不能为空");
        }

        loger.info("SassOrganizationRestController,createOrganization,request:{}",JSONObject.toJSONString(orgForm));

        if(!RegexUtils.isDataCode(orgForm.getCode())){
            // 如果匹配不是useraccount格式
            throw new BusinessException(SassBizResultCodeEnum.ILLEGAL_PARAMETER,
                    "编码不能包含特殊字符或者长度超过16","编码不能包含特殊字符或者长度超过16");
        }


        SassUserInfo sassUserInfo = this.getSassUser(request);
        // 参数转换设置
        OrganizationDO organizationDO = SassFormConvert.convertOrgForm2OrganizationDO(orgForm);
        organizationDO.setGmtCreator(sassUserInfo.getAccount());
        organizationDO.setGmtUpdater(sassUserInfo.getAccount());
        if(StringUtils.isBlank(orgForm.getTenantId())){
            organizationDO.setTenantId(sassUserInfo.getTenantId());
        }
        // 创建对象
        try {
            int result = this.organizationService.createOrganization(organizationDO);

            if(result > 0){
                return SassBizResultCodeEnum.SUCCESS.getAlertMessage();
            }else {
                return SassBizResultCodeEnum.FAIL.getAlertMessage();
            }
        }catch (DuplicateKeyException dex){

            throw new BusinessException(SassBizResultCodeEnum.ILLEGAL_PARAMETER, "编码不能重复","编码不能重复");

        }catch (Exception ex){

            throw new BusinessException(SassBizResultCodeEnum.FAIL, "处理异常，请稍后重试","处理异常，请稍后重试");

        }


    }


    @RequestMapping(value = "/update",method = RequestMethod.POST)
    @ResponseBody
    @Transactional(rollbackFor = Exception.class)
    @RequiresRoles(value = {"sass_admin","admin","sass_mng"},logical= Logical.OR)
    @SysLog("更新组织机构操作")
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

        if(result > 0){
            return SassBizResultCodeEnum.SUCCESS.getAlertMessage();
        }else {
            return SassBizResultCodeEnum.FAIL.getAlertMessage();
        }

    }

    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    @ResponseBody
    @Transactional(rollbackFor = Exception.class)
    @RequiresRoles(value = {"sass_admin","admin","sass_mng"},logical= Logical.OR)
    @SysLog("删除组织机构操作")
    public Object deleteOrganization(@RequestBody DeleteOrgForm deleteOrgForm, HttpServletRequest request){

        if(deleteOrgForm == null){
            throw new BusinessException(SassBizResultCodeEnum.ILLEGAL_PARAMETER);
        }

        if(CollectionUtils.isEmpty(deleteOrgForm.getIds())){
            throw new BusinessException(SassBizResultCodeEnum.ILLEGAL_PARAMETER,"组织机构列表不能为空");
        }
        List<OrganizationDO> organizationDOS = this.organizationService.findChildren(deleteOrgForm.getIds());
        if(!CollectionUtils.isEmpty(organizationDOS)){
            throw new BusinessException(SassBizResultCodeEnum.NOT_PERMIT_DELETE);
        }

        List<OrganizationDO> organizationDOList = this.organizationService.queryOrgListByOrgIds(deleteOrgForm.getIds());

        List<String> orgCodes = organizationDOList.stream()
                .map(OrganizationDO::getCode).distinct().collect(Collectors.toList());

        List<UserOrgDO> userOrgDOList = userService.queryUserOrgsByOrgCodeLists(orgCodes);

        if(!CollectionUtils.isEmpty(userOrgDOList)) {
            throw new BusinessException(SassBizResultCodeEnum.NOT_PERMIT_DELETE);
        }

        int result = this.organizationService.deleteByIds(deleteOrgForm.getIds());
        if(result > 0){
            return SassBizResultCodeEnum.SUCCESS.getAlertMessage();
        }else {
            return SassBizResultCodeEnum.FAIL.getAlertMessage();
        }
    }



}
