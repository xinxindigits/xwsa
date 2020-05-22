package cn.com.xinxin.sass.biz.service.wechatwork.impl;

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

import cn.com.xinxin.sass.biz.model.bo.WeChatWorkFetchDataBO;
import cn.com.xinxin.sass.biz.model.bo.WeChatWorkImportDataBO;
import cn.com.xinxin.sass.biz.service.TenantBaseInfoService;
import cn.com.xinxin.sass.biz.service.TenantDataSyncConfigService;
import cn.com.xinxin.sass.biz.service.TenantDataSyncLogService;
import cn.com.xinxin.sass.biz.service.wechatwork.WeChatWorkDataService;
import cn.com.xinxin.sass.biz.service.wechatwork.WeChatWorkSyncService;
import cn.com.xinxin.sass.common.constants.CommonConstants;
import cn.com.xinxin.sass.common.enums.SassBizResultCodeEnum;
import cn.com.xinxin.sass.common.enums.TaskErrorEnum;
import cn.com.xinxin.sass.common.enums.TaskStatusEnum;
import cn.com.xinxin.sass.common.enums.TaskTypeEnum;
import cn.com.xinxin.sass.common.utils.DateUtils;
import cn.com.xinxin.sass.common.utils.SnowFlakeUtil;
import cn.com.xinxin.sass.repository.model.TenantBaseInfoDO;
import cn.com.xinxin.sass.repository.model.TenantDataSyncLogDO;
import com.xinxinfinance.commons.exception.BusinessException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * @author: liuhangzhou
 * @created: 2020/5/10.
 * @updater:
 * @description: 通讯录同步任务
 */
@Service(value = "weChatWorkAddressListSyncServiceImpl")
public class WeChatWorkAddressListSyncServiceImpl implements WeChatWorkSyncService {

    private static final Logger LOGGER = LoggerFactory.getLogger(WeChatWorkAddressListSyncServiceImpl.class);

    private TenantDataSyncConfigService tenantDataSyncConfigService;
    private TenantBaseInfoService tenantBaseInfoService;
    private WeChatWorkDataService weChatWorkMemberDataServiceImpl;
    private WeChatWorkDataService weChatWorkDepartmentDataServiceImpl;
    private WeChatWorkDataService weChatWorkCustomerDataServiceImpl;
    private TenantDataSyncLogService tenantDataSyncLogService;
    private WeChatWorkDataService weChatWorkGroupChatDataServiceImpl;

    public WeChatWorkAddressListSyncServiceImpl(TenantDataSyncConfigService tenantDataSyncConfigService,
                                                TenantBaseInfoService tenantBaseInfoService,
                                                @Qualifier(value = "weChatWorkMemberDataServiceImpl")
                                                WeChatWorkDataService weChatWorkMemberDataServiceImpl,
                                                @Qualifier(value = "weChatWorkDepartmentDataServiceImpl")
                                                WeChatWorkDataService weChatWorkDepartmentDataServiceImpl,
                                                @Qualifier(value = "weChatWorkCustomerDataServiceImpl")
                                                WeChatWorkDataService weChatWorkCustomerDataServiceImpl,
                                                TenantDataSyncLogService tenantDataSyncLogService,
                                                @Qualifier(value = "weChatWorkGroupChatDataServiceImpl")
                                                WeChatWorkDataService weChatWorkGroupChatDataServiceImpl) {
        this.tenantDataSyncConfigService = tenantDataSyncConfigService;
        this.tenantBaseInfoService = tenantBaseInfoService;
        this.weChatWorkCustomerDataServiceImpl = weChatWorkCustomerDataServiceImpl;
        this.weChatWorkDepartmentDataServiceImpl = weChatWorkDepartmentDataServiceImpl;
        this.weChatWorkMemberDataServiceImpl = weChatWorkMemberDataServiceImpl;
        this.tenantDataSyncLogService = tenantDataSyncLogService;
        this.weChatWorkGroupChatDataServiceImpl = weChatWorkGroupChatDataServiceImpl;
    }

    /**
     * 通讯录同步任务
     * @param tenantId 租户id
     */
    @Override
    public void sync(String tenantId) {
        //参数检查
        if (StringUtils.isBlank(tenantId)) {
            LOGGER.error("同步企业微信通讯录，tenantId不能为空");
            throw new BusinessException(SassBizResultCodeEnum.FAIL, "同步企业微信通讯录，tenantId不能为空");
        }

        //任务上锁
        tenantDataSyncConfigService.updateLockByTenantIdAndTaskType(tenantId, TaskTypeEnum.CONTACT_SYNC.getType());

        TenantDataSyncLogDO tenantDataSyncLogDO;

        try {
            //初始化并持久化租户数据同步日志
            tenantDataSyncLogDO = initAndInsertLog(tenantId);
        } catch (Exception e) {
            LOGGER.error("初始化通讯录同步任务日志失败", e);
            //任务解锁
            tenantDataSyncConfigService.updateUnLockByTenantIdAndTaskType(tenantId, TaskTypeEnum.CONTACT_SYNC.getType());

            throw new BusinessException(SassBizResultCodeEnum.FAIL, "初始化通讯录同步任务日志失败");
        }

        try {
            //机构基础信息
            TenantBaseInfoDO tenantBaseInfoDO = tenantBaseInfoService.selectByTenantId(tenantDataSyncLogDO.getTenantId());

            //数据获取BO
            WeChatWorkFetchDataBO weChatWorkFetchDataBO = new WeChatWorkFetchDataBO();
            weChatWorkFetchDataBO.setTaskId(tenantDataSyncLogDO.getTaskId());
            weChatWorkFetchDataBO.setTenantBaseInfoDO(tenantBaseInfoDO);

            try {
                //获取部门信息
                weChatWorkDepartmentDataServiceImpl.fetchData(weChatWorkFetchDataBO);
                //获取成员信息
                weChatWorkMemberDataServiceImpl.fetchData(weChatWorkFetchDataBO);
                //获取客户信息
                weChatWorkCustomerDataServiceImpl.fetchData(weChatWorkFetchDataBO);
                //获取群信息
                weChatWorkGroupChatDataServiceImpl.fetchData(weChatWorkFetchDataBO);
            } catch (Exception e) {
                LOGGER.error("从企业微信拉取通讯录数据保存到相关数据暂存表失败，TenantId[{}]",
                        tenantDataSyncLogDO.getTenantId(), e);
                updateLog(tenantDataSyncLogDO, TaskErrorEnum.RECEIVE_EXCEPTION.getErrorCode(), e.getMessage(),
                        TaskStatusEnum.FAILURE.getStatus());
                throw new BusinessException(SassBizResultCodeEnum.FAIL, "从企业微信拉取通讯录数据保存到相关数据暂存表失败");
            }


            //数据导入BO
            WeChatWorkImportDataBO weChatWorkImportDataBO = new WeChatWorkImportDataBO();
            weChatWorkImportDataBO.setMemberReceivedUserIdS(new ArrayList<>());
            weChatWorkImportDataBO.setTenantDataSyncLogDO(tenantDataSyncLogDO);
            weChatWorkImportDataBO.setWeChatWorkGroupChatDetailBOS(weChatWorkFetchDataBO.getWeChatWorkGroupChatDetailBOS());

            try {
                //导入部门信息
                weChatWorkDepartmentDataServiceImpl.importData(weChatWorkImportDataBO);
                //导入成员信息
                weChatWorkMemberDataServiceImpl.importData(weChatWorkImportDataBO);
                //导入客户信息
                weChatWorkCustomerDataServiceImpl.importData(weChatWorkImportDataBO);
                //导入群信息
                weChatWorkGroupChatDataServiceImpl.importData(weChatWorkImportDataBO);
            } catch (Exception e) {
                LOGGER.error("导入更新通讯录失败，orgId[{}]", tenantDataSyncLogDO.getTenantId(), e);
                updateLog(tenantDataSyncLogDO, TaskErrorEnum.IMPORTING_EXCEPTION.getErrorCode(), e.getMessage(),
                        TaskStatusEnum.FAILURE.getStatus());
                throw new BusinessException(SassBizResultCodeEnum.FAIL, "导入更新通讯录失败");
            }

            //更新成功日志
            updateLog(tenantDataSyncLogDO, "", "", TaskStatusEnum.SUCCESS.getStatus());

            LOGGER.info("机构[{}]同步通讯录成功", tenantId);
        } catch (Exception e) {
            LOGGER.error("同步企业微信数据失败，租户[{}]", tenantId, e);
            throw new BusinessException(SassBizResultCodeEnum.FAIL, "同步企业微信数据失败");
        } finally {
            //任务解锁
            tenantDataSyncConfigService.updateUnLockByTenantIdAndTaskType(tenantId, TaskTypeEnum.CONTACT_SYNC.getType());
        }
    }

    /**
     * 初始化并持久化租户数据同步日志
     * @param tenantId 租户id
     * @return 租户数据同步日志
     */
    private TenantDataSyncLogDO initAndInsertLog(String tenantId) {
        TenantDataSyncLogDO tenantDataSyncLogDO = new TenantDataSyncLogDO();
        tenantDataSyncLogDO.setTaskId(SnowFlakeUtil.generateId(SnowFlakeUtil.TK_PREFIX));
        tenantDataSyncLogDO.setTenantId(tenantId);
        tenantDataSyncLogDO.setTaskType(TaskTypeEnum.CONTACT_SYNC.getType());
        tenantDataSyncLogDO.setTaskDate(DateUtils.getToday(DateUtils.DATE_FORMAT_NOSIGN));
        tenantDataSyncLogDO.setTaskTime(DateUtils.getToday(DateUtils.DATE_FORMAT_TIME));
        tenantDataSyncLogDO.setDepartmentCount(0);
        tenantDataSyncLogDO.setCustomerCount(0);
        tenantDataSyncLogDO.setMemberCount(0);
        tenantDataSyncLogDO.setTaskStatus(TaskStatusEnum.INIT.getStatus());
        tenantDataSyncLogDO.setGmtCreator(CommonConstants.GMT_CREATOR_SYSTEM);
        tenantDataSyncLogService.insertReturnId(tenantDataSyncLogDO);
        return tenantDataSyncLogDO;
    }

    /**
     * 更新租户数据同步日志
     * @param tenantDataSyncLogDO 租户数据同步日志
     * @param errorCode 异常码
     * @param message 异常信息
     * @param taskStatus 同步状态
     */
    private void updateLog(TenantDataSyncLogDO tenantDataSyncLogDO, String errorCode, String message, String taskStatus) {
        TenantDataSyncLogDO record = new TenantDataSyncLogDO();
        record.setTaskStatus(taskStatus);
        record.setErrorCode(errorCode);
        record.setErrorDesc(message.length() > 500
                ? StringUtils.substring(message, 0, 500) : message);
        record.setId(tenantDataSyncLogDO.getId());
        record.setMemberCount(tenantDataSyncLogDO.getMemberCount());
        record.setCustomerCount(tenantDataSyncLogDO.getCustomerCount());
        record.setDepartmentCount(tenantDataSyncLogDO.getDepartmentCount());
        tenantDataSyncLogService.updateById(record);
    }
}
