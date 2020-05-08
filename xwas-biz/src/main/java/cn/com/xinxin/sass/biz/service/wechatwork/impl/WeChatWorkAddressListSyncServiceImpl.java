package cn.com.xinxin.sass.biz.service.wechatwork.impl;

import cn.com.xinxin.sass.biz.service.TenantBaseInfoService;
import cn.com.xinxin.sass.biz.service.TenantDataSyncConfigService;
import cn.com.xinxin.sass.biz.service.TenantDataSyncLogService;
import cn.com.xinxin.sass.biz.service.wechatwork.WeChatWorkAddressListService;
import cn.com.xinxin.sass.biz.service.wechatwork.WeChatWorkAddressListSyncService;
import cn.com.xinxin.sass.biz.service.wechatwork.WeChatWorkDepartmentSyncService;
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
import org.springframework.stereotype.Service;

/**
 * @author: liuhangzhou
 * @created: 2020/5/6.
 * @updater:
 * @description: 企业微信通讯录同步服务
 */
@Service
@Deprecated
public class WeChatWorkAddressListSyncServiceImpl implements WeChatWorkAddressListSyncService {

    private static final Logger LOGGER = LoggerFactory.getLogger(WeChatWorkAddressListSyncServiceImpl.class);

    private final WeChatWorkDepartmentSyncService weChatWorkDepartmentSyncService;
    private final TenantDataSyncLogService tenantDataSyncLogService;
    private final TenantBaseInfoService tenantBaseInfoService;
    private final TenantDataSyncConfigService tenantDataSyncConfigService;
    private final WeChatWorkAddressListService weChatWorkAddressListService;

    public WeChatWorkAddressListSyncServiceImpl(final WeChatWorkDepartmentSyncService weChatWorkDepartmentSyncService,
                                                final TenantDataSyncLogService tenantDataSyncLogService,
                                                final TenantBaseInfoService tenantBaseInfoService,
                                                final TenantDataSyncConfigService tenantDataSyncConfigService,
                                                final WeChatWorkAddressListService weChatWorkAddressListService) {
        this.weChatWorkDepartmentSyncService = weChatWorkDepartmentSyncService;
        this.tenantDataSyncLogService = tenantDataSyncLogService;
        this.tenantBaseInfoService = tenantBaseInfoService;
        this.tenantDataSyncConfigService = tenantDataSyncConfigService;
        this.weChatWorkAddressListService = weChatWorkAddressListService;
    }

    /**
     * 同步企业微信通讯录
     * @param tenantId 租户id
     */
    @Override
    public void syncWeChatWorkAddressList(String tenantId) {
        //参数检查
        if (StringUtils.isBlank(tenantId)) {
            LOGGER.error("同步企业微信通讯录，tenantId不能为空");
            throw new BusinessException(SassBizResultCodeEnum.FAIL, "同步企业微信通讯录，tenantId不能为空");
        }

        //初始化并持久化租户数据同步日志
        TenantDataSyncLogDO tenantDataSyncLogDO = initAndInsertLog(tenantId);

        //机构基础信息
        TenantBaseInfoDO tenantBaseInfoDO = tenantBaseInfoService.selectByTenantId(tenantDataSyncLogDO.getTenantId());

        //向企业微信提取数据
        fetchData(tenantDataSyncLogDO, tenantBaseInfoDO);

        //同步企业微信数据
        syncData(tenantDataSyncLogDO);

        LOGGER.info("机构[{}]同步通讯录成功", tenantId);
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
     * 向企业微信提取数据
     * @param tenantDataSyncLogDO 租户数据同步日志
     * @param tenantBaseInfoDO 租户基础信息
     */
    private void fetchData(TenantDataSyncLogDO tenantDataSyncLogDO, TenantBaseInfoDO tenantBaseInfoDO) {
        updateLog(tenantDataSyncLogDO, "", "", TaskStatusEnum.RECEIVING.getStatus());
        //从企业微信拉取通讯录数据保存到相关数据暂存表
        try {
            weChatWorkAddressListService.fetchAndImportAddressList(tenantBaseInfoDO.getCorpId(),
                    tenantBaseInfoDO.getAddressListSecret(), tenantBaseInfoDO.getCustomerContactSecret(),
                    tenantDataSyncLogDO.getTaskId(), tenantDataSyncLogDO.getTenantId());
        } catch (Exception e) {
            LOGGER.error("从企业微信拉取通讯录数据保存到相关数据暂存表失败，TenantId[{}]",
                    tenantDataSyncLogDO.getTenantId(), e);
            updateLog(tenantDataSyncLogDO, TaskErrorEnum.RECEIVE_EXCEPTION.getErrorCode(), e.getMessage(),
                    TaskStatusEnum.FAILURE.getStatus());
            throw new BusinessException(SassBizResultCodeEnum.FAIL, "从企业微信拉取通讯录数据保存到相关数据暂存表失败");
        }
    }

    /**
     * 同步企业微信数据
     * @param tenantDataSyncLogDO 租户数据同步日志
     */
    private void syncData(TenantDataSyncLogDO tenantDataSyncLogDO) {
        updateLog(tenantDataSyncLogDO, "", "", TaskStatusEnum.IMPORTING.getStatus());

        //同步通讯录，首先同步部门，再同步部门中的成员，再同步成员所关联的客户
        try {
            weChatWorkDepartmentSyncService.syncDepartment(tenantDataSyncLogDO);
        } catch (Exception e) {
            LOGGER.error("导入更新通讯录失败，orgId[{}]", tenantDataSyncLogDO.getTenantId(), e);
            updateLog(tenantDataSyncLogDO, TaskErrorEnum.IMPORTING_EXCEPTION.getErrorCode(), e.getMessage(),
                    TaskStatusEnum.FAILURE.getStatus());
            throw new BusinessException(SassBizResultCodeEnum.FAIL, "导入更新通讯录失败");
        }

        updateLog(tenantDataSyncLogDO, "", "", TaskStatusEnum.SUCCESS.getStatus());
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
