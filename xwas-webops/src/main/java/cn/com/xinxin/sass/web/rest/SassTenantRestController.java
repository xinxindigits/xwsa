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
import cn.com.xinxin.sass.biz.schedule.service.QuartzJobService;
import cn.com.xinxin.sass.biz.service.TenantBaseInfoService;
import cn.com.xinxin.sass.biz.service.TenantDataSyncConfigService;
import cn.com.xinxin.sass.biz.service.TenantDataSyncLogService;
import cn.com.xinxin.sass.biz.service.wechatwork.WeChatWorkSyncService;
import cn.com.xinxin.sass.biz.tenant.TenantIdContext;
import cn.com.xinxin.sass.common.enums.SassBizResultCodeEnum;
import cn.com.xinxin.sass.common.enums.TaskTypeEnum;
import cn.com.xinxin.sass.common.model.PageResultVO;
import cn.com.xinxin.sass.common.utils.DateUtils;
import cn.com.xinxin.sass.repository.model.TenantBaseInfoDO;
import cn.com.xinxin.sass.repository.model.TenantDataSyncConfigDO;
import cn.com.xinxin.sass.repository.model.TenantDataSyncLogDO;
import cn.com.xinxin.sass.repository.model.bo.TenantDataSyncLogBO;
import cn.com.xinxin.sass.web.convert.TenantDataSyncLogConvert;
import cn.com.xinxin.sass.web.form.DeleteOrgForm;
import cn.com.xinxin.sass.web.form.TenantConfigForm;
import cn.com.xinxin.sass.web.form.TenantForm;
import cn.com.xinxin.sass.web.form.TenantJobLogQueryForm;
import cn.com.xinxin.sass.web.vo.*;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonObjectFormatVisitor;
import com.google.common.collect.Lists;
import com.xinxinfinance.commons.exception.BusinessException;
import com.xinxinfinance.commons.idgen.SnowFakeIdGenerator;
import com.xinxinfinance.commons.util.BaseConvert;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DuplicateKeyException;
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

    private final TenantBaseInfoService tenantBaseInfoService;

    private final TenantDataSyncConfigService tenantDataSyncConfigService;

    private final QuartzJobService quartzJobService;

    private final WeChatWorkSyncService weChatWorkAddressListSyncServiceImpl;

    private final WeChatWorkSyncService weChatWorkChatRecordSyncServiceImpl;

    private final TenantDataSyncLogService tenantDataSyncLogService;

    private static final String OG = "TD";

    private static final String DATE_FORMAT_NOSIGN = "yyyyMMdd";

    private static final String PADDING = "000";

    public SassTenantRestController(TenantBaseInfoService tenantBaseInfoService,
                                    TenantDataSyncConfigService tenantDataSyncConfigService,
                                    final QuartzJobService quartzJobService,
                                    @Qualifier(value = "weChatWorkAddressListSyncServiceImpl")
                                    final WeChatWorkSyncService weChatWorkAddressListSyncServiceImpl,
                                    @Qualifier(value = "weChatWorkChatRecordSyncServiceImpl")
                                    final WeChatWorkSyncService weChatWorkChatRecordSyncServiceImpl,
                                    final TenantDataSyncLogService tenantDataSyncLogService) {
        this.tenantBaseInfoService = tenantBaseInfoService;
        this.tenantDataSyncConfigService = tenantDataSyncConfigService;
        this.quartzJobService = quartzJobService;
        this.weChatWorkAddressListSyncServiceImpl = weChatWorkAddressListSyncServiceImpl;
        this.weChatWorkChatRecordSyncServiceImpl = weChatWorkChatRecordSyncServiceImpl;
        this.tenantDataSyncLogService = tenantDataSyncLogService;
    }

    @RequestMapping(value = "/list",method = RequestMethod.POST)
    @ResponseBody
    @RequiresRoles(value = {"sass_admin","admin","sass_mng"},logical= Logical.OR)
    public Object tenantList(HttpServletRequest request, @RequestBody TenantForm tenantForm){

        if(null == tenantForm){
            throw new BusinessException(SassBizResultCodeEnum.PARAMETER_NULL,"租户查询参数不能为空","租户查询参数不能为空");
        }

        loger.info("SassTenantRestController,tenantList, tenantForm = {}", JSONObject.toJSONString(tenantForm));

        SassUserInfo sassUserInfo = this.getSassUser(request);
        // 参数转换设置

        String opsTenantId = this.getOpsTenantId(request);

        if(StringUtils.isBlank(opsTenantId)){
            throw new BusinessException(SassBizResultCodeEnum.ILLEGAL_PARAMETER, "需要运营的租户不能为空");
        }

        PageResultVO page = new PageResultVO();
        page.setPageSize(tenantForm.getPageSize());
        page.setPageNumber(tenantForm.getPageIndex());

        TenantBaseInfoDO condition = BaseConvert.convert(tenantForm, TenantBaseInfoDO.class);
        condition.setTenantName(tenantForm.getName());
        // 设置操作的租户编码
        //condition.setTenantId(tenantForm.getCode());
        condition.setTenantId(opsTenantId);
        PageResultVO<TenantBaseInfoDO> result = tenantBaseInfoService.findByCondition(page, condition);
        PageResultVO<TenantInfoVO> resultVO = BaseConvert.convert(result,PageResultVO.class);
        resultVO.setItems(BaseConvert.convertList(result.getItems(),TenantInfoVO.class));
        return resultVO;
    }

    @RequestMapping(value = "/query/{code}",method = RequestMethod.GET)
    @ResponseBody
    @RequiresRoles(value = {"sass_admin","admin","sass_mng"},logical= Logical.OR)
    public Object queryTenantDetail(HttpServletRequest request, @PathVariable(value = "code")String code){

        if(StringUtils.isEmpty(code)){
            throw new BusinessException(SassBizResultCodeEnum.PARAMETER_NULL,"组织机构查询参数不能为空","组织机构查询参数");
        }

        loger.info("SassTenantRestController,queryTenantDetail, code = {}",code);

        TenantBaseInfoDO tenantBaseInfoDO = tenantBaseInfoService.selectByTenantId(code);
        if(tenantBaseInfoDO == null){
            throw new BusinessException(SassBizResultCodeEnum.DATA_NOT_EXIST);
        }
        TenantDetailVO result = BaseConvert.convert(tenantBaseInfoDO, TenantDetailVO.class);
        result.setTenantId(tenantBaseInfoDO.getTenantId());
        result.setTenantName(tenantBaseInfoDO.getTenantName());

        return result;

    }

    @RequestMapping(value = "/create",method = RequestMethod.POST)
    @ResponseBody
    @Transactional(rollbackFor = Exception.class)
    @RequiresRoles(value = {"sass_admin","admin","sass_mng"},logical= Logical.OR)
    @SysLog("创建租户操作")
    public Object createTenant(HttpServletRequest request, @RequestBody TenantForm tenantForm){

        if(null == tenantForm){
            throw new BusinessException(SassBizResultCodeEnum.PARAMETER_NULL,"创建租户参数不能为空","创建租户参数不能为空");
        }
        loger.info("SassTenantRestController,createTenant, tenantForm:{}",JSONObject.toJSONString(tenantForm));
        SassUserInfo sassUserInfo = this.getSassUser(request);
        String tenantId =  "";
        if(StringUtils.isEmpty(tenantId)){
            StringBuilder code = new StringBuilder();
            code.append(OG);
            try {
                code.append(SnowFakeIdGenerator.getInstance().generateLongId());
            }catch (Exception e){
                loger.error("生成id失败");
                throw new BusinessException(SassBizResultCodeEnum.GENERATE_ID_ERROR);
            }
            tenantId = code.toString();
        }
        // 参数转换设置
        TenantBaseInfoDO tenantBaseInfoDO = BaseConvert.convert(tenantForm, TenantBaseInfoDO.class);
        tenantBaseInfoDO.setTenantId(tenantId);
        tenantBaseInfoDO.setTenantName(tenantForm.getName());
        tenantBaseInfoDO.setGmtCreator(sassUserInfo.getAccount());
        tenantBaseInfoDO.setGmtUpdater(sassUserInfo.getAccount());

        try {

            boolean result = tenantBaseInfoService.createOrgBaseInfo(tenantBaseInfoDO);
            // 移除操作租户
            if(result){
                return SassBizResultCodeEnum.SUCCESS.getAlertMessage();
            }else {
                return SassBizResultCodeEnum.FAIL.getAlertMessage();
            }
        }catch (DuplicateKeyException dex){
            throw new BusinessException(SassBizResultCodeEnum.ILLEGAL_PARAMETER, "编码不能重复","编码不能重复");
        }catch (Exception ex){
            loger.error("createTenant", ex.getMessage());
            throw new BusinessException(SassBizResultCodeEnum.FAIL, "处理异常，请稍后重试","处理异常，请稍后重试");
        }

    }


    @RequestMapping(value = "/update",method = RequestMethod.POST)
    @ResponseBody
    @Transactional(rollbackFor = Exception.class)
    @RequiresRoles(value = {"sass_admin","admin","sass_mng"},logical= Logical.OR)
    @SysLog("更新租户操作")
    public Object updateTenant(HttpServletRequest request,
                                     @RequestBody TenantForm tenantForm){

        if(null == tenantForm){
            throw new BusinessException(SassBizResultCodeEnum.PARAMETER_NULL,"更新租户参数不能为空","更新租户参数不能为空");
        }

        loger.info("SassTenantRestController,updateTenant, orgName = {}",tenantForm.getName());

        SassUserInfo sassUserInfo = this.getSassUser(request);

        TenantBaseInfoDO tenantBaseInfoDO = BaseConvert.convert(tenantForm, TenantBaseInfoDO.class);
        tenantBaseInfoDO.setTenantId(tenantForm.getCode());
        tenantBaseInfoDO.setTenantName(tenantForm.getName());
        tenantBaseInfoDO.setGmtUpdater(sassUserInfo.getAccount());
        boolean result = this.tenantBaseInfoService.updateByOrgId(tenantBaseInfoDO);

        if(result){
            return SassBizResultCodeEnum.SUCCESS.getAlertMessage();
        }else {
            return SassBizResultCodeEnum.FAIL.getAlertMessage();
        }
    }

    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    @ResponseBody
    @Transactional(rollbackFor = Exception.class)
    @RequiresRoles(value = {"sass_admin","admin","sass_mng"},logical= Logical.OR)
    @SysLog("删除租户操作")
    public Object deleteTenant(@RequestBody DeleteOrgForm deleteOrgForm, HttpServletRequest request){

        if(deleteOrgForm == null){
            throw new BusinessException(SassBizResultCodeEnum.ILLEGAL_PARAMETER);
        }

        if(CollectionUtils.isEmpty(deleteOrgForm.getCodes())){
            throw new BusinessException(SassBizResultCodeEnum.ILLEGAL_PARAMETER,"组织机构列表不能为空");
        }

        int result = this.tenantBaseInfoService.deleteByCodes(deleteOrgForm.getCodes());
        if(result > 0){
            return SassBizResultCodeEnum.SUCCESS.getAlertMessage();
        }else {
            return SassBizResultCodeEnum.FAIL.getAlertMessage();
        }
    }

    @RequestMapping(value = "/queryConfig",method = RequestMethod.GET)
    @ResponseBody
    @RequiresRoles(value = {"sass_admin","admin","sass_mng"},logical= Logical.OR)
    public Object queryTenantConfig(@RequestParam(required = false) String tenantId, HttpServletRequest request){

        SassUserInfo sassUserInfo = this.getSassUser(request);

        String opsTenantId = this.getOpsTenantId(request);

        if(StringUtils.isBlank(opsTenantId)){
            throw new BusinessException(SassBizResultCodeEnum.ILLEGAL_PARAMETER, "需要运营的租户不能为空");
        }

        if(StringUtils.isEmpty(tenantId)){
            tenantId = opsTenantId;
        }

        List<TenantDataSyncConfigDO> tenantDataSyncConfigDOS = tenantDataSyncConfigService.selectByTenantId(tenantId);
        return tenantDataSyncConfigDOS;
    }

    @RequestMapping(value = "/tenantConfig/create",method = RequestMethod.POST)
    @ResponseBody
    @Transactional(rollbackFor = Exception.class)
    @RequiresRoles(value = {"sass_admin","admin","sass_mng"},logical= Logical.OR)
    @SysLog("新增任务配置操作")
    public Object insertTenantConfig(@RequestBody TenantConfigForm queryForm, HttpServletRequest request) {

        if (StringUtils.isBlank(queryForm.getTaskType())) {
            loger.error("插入配置, TaskType不能为空");
            throw new BusinessException(SassBizResultCodeEnum.ILLEGAL_PARAMETER,
                    "插入配置, TaskType不能为空");
        }
        if (StringUtils.isBlank(queryForm.getCronExpression())) {
            loger.error("插入配置, CronExpression不能为空");
            throw new BusinessException(SassBizResultCodeEnum.ILLEGAL_PARAMETER,
                    "插入配置, CronExpression不能为空");
        }
        if (null == queryForm.getStatus()) {
            loger.error("插入配置, Status不能为空");
            throw new BusinessException(SassBizResultCodeEnum.ILLEGAL_PARAMETER,
                    "插入配置, Status不能为空");
        }

        String opsTenantId = this.getOpsTenantId(request);

        if(StringUtils.isBlank(opsTenantId)){
            throw new BusinessException(SassBizResultCodeEnum.ILLEGAL_PARAMETER, "需要运营的租户不能为空");
        }

        SassUserInfo sassUserInfo = this.getSassUser(request);
        String tenantId = opsTenantId;


        TenantDataSyncConfigDO tenantDataSyncConfigDO = new TenantDataSyncConfigDO();
        tenantDataSyncConfigDO.setTenantId(tenantId);
        tenantDataSyncConfigDO.setTaskType(queryForm.getTaskType());
        tenantDataSyncConfigDO.setCronExpression(queryForm.getCronExpression());
        tenantDataSyncConfigDO.setCountCeiling(queryForm.getCountCeiling());
        tenantDataSyncConfigDO.setTimeInterval(queryForm.getTimeInterval());
        tenantDataSyncConfigDO.setGmtCreator(tenantId);
        tenantDataSyncConfigDO.setStatus(queryForm.getStatus());

        TenantDataSyncConfigDO dataSyncConfigDO = tenantDataSyncConfigService.selectByOrgIdAndTaskType(tenantId, queryForm.getTaskType());
        if(dataSyncConfigDO != null){
            throw new BusinessException(SassBizResultCodeEnum.DATA_ALREADY_EXIST,"该租户存在相同类型的任务");
        }

        tenantDataSyncConfigService.insert(tenantDataSyncConfigDO);

        if (0 == queryForm.getStatus()) {
            quartzJobService.startJob(tenantId, queryForm.getTaskType(), queryForm.getCronExpression());
        }

        return SassBizResultCodeEnum.SUCCESS.getAlertMessage();
    }


    @RequestMapping(value = "/tenantConfig/update",method = RequestMethod.POST)
    @ResponseBody
    @Transactional(rollbackFor = Exception.class)
    @RequiresRoles(value = {"sass_admin","admin","sass_mng"},logical= Logical.OR)
    @SysLog("更新任务配置操作")
    public Object updateTenantConfig(@RequestBody TenantConfigForm queryForm, HttpServletRequest request) {

        if (null == queryForm.getId()) {
            loger.error("更新配置, Id不能为空");
            throw new BusinessException(SassBizResultCodeEnum.ILLEGAL_PARAMETER,
                    "更新配置, Id不能为空");
        }
        if (StringUtils.isBlank(queryForm.getTaskType())) {
            loger.error("更新配置, TaskType不能为空");
            throw new BusinessException(SassBizResultCodeEnum.ILLEGAL_PARAMETER,
                    "更新配置, TaskType不能为空");
        }
        if (StringUtils.isBlank(queryForm.getCronExpression())) {
            loger.error("更新配置, CronExpression不能为空");
            throw new BusinessException(SassBizResultCodeEnum.ILLEGAL_PARAMETER,
                    "更新配置, CronExpression不能为空");
        }
        if (null == queryForm.getStatus()) {
            loger.error("更新配置, Status不能为空");
            throw new BusinessException(SassBizResultCodeEnum.ILLEGAL_PARAMETER,
                    "更新配置, Status不能为空");
        }

        String opsTenantId = this.getOpsTenantId(request);

        if(StringUtils.isBlank(opsTenantId)){
            throw new BusinessException(SassBizResultCodeEnum.ILLEGAL_PARAMETER, "需要运营的租户不能为空");
        }

        TenantDataSyncConfigDO originRecord = tenantDataSyncConfigService.selectById(queryForm.getId());

        if (null == originRecord) {
            loger.error("无法通过id[{}]查询到租户配置记录", queryForm.getId());
            throw new BusinessException(SassBizResultCodeEnum.DATA_NOT_EXIST, "数据不存在");
        }

        TenantDataSyncConfigDO tenantDataSyncConfigDO = new TenantDataSyncConfigDO();
        tenantDataSyncConfigDO.setId(queryForm.getId());
        tenantDataSyncConfigDO.setTaskType(queryForm.getTaskType());
        tenantDataSyncConfigDO.setCronExpression(queryForm.getCronExpression());
        tenantDataSyncConfigDO.setCountCeiling(queryForm.getCountCeiling());
        tenantDataSyncConfigDO.setTimeInterval(queryForm.getTimeInterval());
        tenantDataSyncConfigDO.setGmtUpdater(opsTenantId);
        tenantDataSyncConfigDO.setStatus(queryForm.getStatus());
        tenantDataSyncConfigService.updateById(tenantDataSyncConfigDO);

        quartzJobService.stopJob(opsTenantId, queryForm.getTaskType());

        if (0 == queryForm.getStatus()) {
            quartzJobService.startJob(opsTenantId, queryForm.getTaskType(), queryForm.getCronExpression());
        }

        return SassBizResultCodeEnum.SUCCESS.getAlertMessage();
    }


    @RequestMapping(value = "/executeJob",method = RequestMethod.GET)
    @ResponseBody
    @RequiresRoles(value = {"sass_admin","admin","sass_mng"},logical= Logical.OR)
    @SysLog("手动执行租户任务操作")
    public Object executeJob(@RequestParam String taskType, HttpServletRequest request){

        SassUserInfo sassUserInfo = this.getSassUser(request);

        String opsTenantId = this.getOpsTenantId(request);

        if(StringUtils.isBlank(opsTenantId)){
            throw new BusinessException(SassBizResultCodeEnum.ILLEGAL_PARAMETER, "需要运营的租户不能为空");
        }

        if (StringUtils.equals(taskType, TaskTypeEnum.MESSAGE_SYNC.getType())) {
            weChatWorkChatRecordSyncServiceImpl.sync(opsTenantId);
        } else if (StringUtils.equals(taskType, TaskTypeEnum.CONTACT_SYNC.getType())) {
            weChatWorkAddressListSyncServiceImpl.sync(opsTenantId);
        } else {
            loger.error("手动执行任务，任务类型错误, tenantid[{}], taskType[{}]", opsTenantId, taskType);
            throw new BusinessException(SassBizResultCodeEnum.ILLEGAL_PARAMETER, "手动执行任务，任务类型失败");
        }

        return SassBizResultCodeEnum.SUCCESS.getAlertMessage();
    }

    @RequestMapping(value = "/jobLog/query",method = RequestMethod.POST)
    @ResponseBody
    @Transactional(rollbackFor = Exception.class)
    @RequiresRoles(value = {"sass_admin","admin","sass_mng"},logical= Logical.OR)
    public Object queryTenantJobLog(@RequestBody TenantJobLogQueryForm queryForm, HttpServletRequest request) {
        if (null == queryForm) {
            loger.error("查询租户job日志，TenantJobLogQueryForm不能为空");
            throw new BusinessException(SassBizResultCodeEnum.ILLEGAL_PARAMETER,
                    "查询租户job日志，TenantJobLogQueryForm不能为空");
        }
        SassUserInfo sassUserInfo = this.getSassUser(request);

        String opsTenantId = this.getOpsTenantId(request);

        if(StringUtils.isBlank(opsTenantId)){
            throw new BusinessException(SassBizResultCodeEnum.ILLEGAL_PARAMETER, "需要运营的租户不能为空");
        }

        PageResultVO page = new PageResultVO();
        page.setPageNumber((queryForm.getPageIndex() == null) ? PageResultVO.DEFAULT_PAGE_NUM : queryForm.getPageIndex());
        page.setPageSize((queryForm.getPageSize() == null) ? PageResultVO.DEFAULT_PAGE_SIZE : queryForm.getPageSize());

        //将时间戳格式转化为string
        String startTime = StringUtils.isBlank(queryForm.getStartTime()) ? ""
                : DateUtils.formatTime(new Long(queryForm.getStartTime()), DateUtils.DATE_FORMAT_TIME);
        String endTime = StringUtils.isBlank(queryForm.getEndTime()) ? ""
                : DateUtils.formatTime(new Long(queryForm.getEndTime()), DateUtils.DATE_FORMAT_TIME);

        TenantDataSyncLogBO tenantDataSyncLogBO = new TenantDataSyncLogBO();
        tenantDataSyncLogBO.setTenantId(opsTenantId);
        tenantDataSyncLogBO.setTaskType(queryForm.getTaskType());
        tenantDataSyncLogBO.setTaskDate(queryForm.getTaskDate());
        tenantDataSyncLogBO.setTaskStatus(queryForm.getTaskStatus());
        tenantDataSyncLogBO.setStartTime(startTime);
        tenantDataSyncLogBO.setEndTime(endTime);

        PageResultVO<TenantDataSyncLogDO> pageResultDO = tenantDataSyncLogService.selectRecordSPage(tenantDataSyncLogBO, page);

        PageResultVO<TenantDataSyncLogVO> pageResultVO = new PageResultVO<>();
        pageResultVO.setPageNumber(pageResultDO.getPageNumber());
        pageResultVO.setPageSize(pageResultDO.getPageSize());
        pageResultVO.setTotal(pageResultDO.getTotal());
        pageResultVO.setItems(TenantDataSyncLogConvert.convert2TenantDataSyncLogVOList(pageResultDO.getItems()));

        return pageResultVO;
    }



    @RequestMapping(value = "/routes",method = RequestMethod.GET)
    @ResponseBody
    @RequiresRoles(value = {"sass_admin","admin","sass_mng"},logical= Logical.OR)
    public  Object queryAllTenants(HttpServletRequest request){


        List<TenantBaseInfoDO> tenantBaseInfoDOList = this.tenantBaseInfoService.queryAllTenants();

        List<TenantInfoVO> tenantInfoVOS = BaseConvert.convertList(tenantBaseInfoDOList,TenantInfoVO.class);

        return tenantInfoVOS;
    }
}
