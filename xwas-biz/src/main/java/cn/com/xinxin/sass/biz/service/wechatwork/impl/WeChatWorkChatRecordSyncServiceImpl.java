package cn.com.xinxin.sass.biz.service.wechatwork.impl;

import cn.com.xinxin.sass.biz.convert.MsgRecordConvert;
import cn.com.xinxin.sass.biz.service.MsgRecordService;
import cn.com.xinxin.sass.biz.service.TenantBaseInfoService;
import cn.com.xinxin.sass.biz.service.TenantDataSyncConfigService;
import cn.com.xinxin.sass.biz.service.TenantDataSyncLogService;
import cn.com.xinxin.sass.biz.service.wechatwork.WeChatWorkSyncService;
import cn.com.xinxin.sass.common.constants.CommonConstants;
import cn.com.xinxin.sass.common.enums.SassBizResultCodeEnum;
import cn.com.xinxin.sass.common.enums.TaskErrorEnum;
import cn.com.xinxin.sass.common.enums.TaskStatusEnum;
import cn.com.xinxin.sass.common.enums.TaskTypeEnum;
import cn.com.xinxin.sass.common.utils.DateUtils;
import cn.com.xinxin.sass.common.utils.SnowFlakeUtil;
import cn.com.xinxin.sass.repository.model.MsgRecordDO;
import cn.com.xinxin.sass.repository.model.TenantBaseInfoDO;
import cn.com.xinxin.sass.repository.model.TenantDataSyncConfigDO;
import cn.com.xinxin.sass.repository.model.TenantDataSyncLogDO;
import cn.com.xinxin.sass.sal.model.wechatwork.WeChatWorkChatDataBO;
import cn.com.xinxin.sass.sal.model.wechatwork.WeChatWorkChattingRecordsBO;
import cn.com.xinxin.sass.sal.model.wechatwork.WeChatWorkChattingRecordsReqBO;
import cn.com.xinxin.sass.sal.model.wechatwork.response.WeChatWorkChattingRecordsResponseBO;
import cn.com.xinxin.sass.sal.wechatwork.WeChatWorkInteractionClient;
import com.tencent.wework.ChattingRecordsUtils;
import com.xinxinfinance.commons.exception.BusinessException;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * @author: liuhangzhou
 * @created: 2020/4/26.
 * @updater:
 * @description: 企业微信聊天记录服务
 */
@Service(value = "weChatWorkChatRecordSyncServiceImpl")
public class WeChatWorkChatRecordSyncServiceImpl implements WeChatWorkSyncService {

    private static final Logger LOGGER = LoggerFactory.getLogger(WeChatWorkChatRecordSyncServiceImpl.class);

    private final WeChatWorkInteractionClient weChatWorkInteractionClient;
    private final MsgRecordService msgRecordService;
    private final TenantBaseInfoService tenantBaseInfoService;
    private final TenantDataSyncConfigService tenantDataSyncConfigService;
    private final TenantDataSyncLogService tenantDataSyncLogService;

    public WeChatWorkChatRecordSyncServiceImpl(final WeChatWorkInteractionClient weChatWorkInteractionClient,
                                            final MsgRecordService msgRecordService,
                                            final TenantBaseInfoService tenantBaseInfoService,
                                            final TenantDataSyncConfigService tenantDataSyncConfigService,
                                            final TenantDataSyncLogService tenantDataSyncLogService) {
        this.weChatWorkInteractionClient = weChatWorkInteractionClient;
        this.msgRecordService = msgRecordService;
        this.tenantBaseInfoService = tenantBaseInfoService;
        this.tenantDataSyncConfigService = tenantDataSyncConfigService;
        this.tenantDataSyncLogService = tenantDataSyncLogService;
    }

    /**
     * 获取聊天记录
     * @param tenantId 机构id
     */
    @Override
    public void sync(String tenantId) {
        //参数检查
        if (StringUtils.isBlank(tenantId)) {
            LOGGER.error("获取聊天记录，tenantId不能为空");
            throw new BusinessException(SassBizResultCodeEnum.FAIL, "获取聊天记录，tenantId不能为空");
        }

        //任务上锁
        tenantDataSyncConfigService.updateLockByTenantIdAndTaskType(tenantId, TaskTypeEnum.MESSAGE_SYNC.getType());

        TenantDataSyncLogDO tenantDataSyncLogDO;

        try {
            //初始化并持久化租户数据同步日志
            tenantDataSyncLogDO = initLog(tenantId);
        } catch (Exception e) {
            LOGGER.error("初始化获取聊天记录任务日志失败", e);
            //任务解锁
            tenantDataSyncConfigService.updateUnLockByTenantIdAndTaskType(tenantId, TaskTypeEnum.CONTACT_SYNC.getType());

            throw new BusinessException(SassBizResultCodeEnum.FAIL, "初始化获取聊天记录任务日志失败");
        }

        //此次更新的数量
        Integer count = 0;

        try {
            //机构基础信息
            TenantBaseInfoDO tenantBaseInfoDO = tenantBaseInfoService.selectByTenantId(tenantDataSyncLogDO.getTenantId());

            //机构任务配置信息
            TenantDataSyncConfigDO tenantDataSyncConfigDO = tenantDataSyncConfigService.selectByOrgIdAndTaskType(
                    tenantBaseInfoDO.getTenantId(), TaskTypeEnum.MESSAGE_SYNC.getType());

            //初始化sdk
            long sdk = ChattingRecordsUtils.initSdk(tenantBaseInfoDO.getCorpId(), tenantBaseInfoDO.getChatRecordSecret());

            //拉取数据的请求BO
            WeChatWorkChattingRecordsReqBO reqBO = new WeChatWorkChattingRecordsReqBO();
            reqBO.setLimit(tenantDataSyncConfigDO.getCountCeiling().longValue());
            reqBO.setSdk(sdk);
            reqBO.setTimeout(tenantDataSyncConfigDO.getTimeInterval().longValue());
            reqBO.setStartSeq(tenantDataSyncConfigDO.getFetchedSeqNo());

            //拉取数据并保存
            try {
                count = fetchAndStockData(reqBO, tenantBaseInfoDO.getPrivateKey(), tenantDataSyncLogDO.getTenantId(),
                        tenantDataSyncLogDO.getTaskId());
            } finally {
                //更新配置表中消息序号
                TenantDataSyncConfigDO updateOrgDataSyncConfig = new TenantDataSyncConfigDO();
                updateOrgDataSyncConfig.setId(tenantDataSyncConfigDO.getId());
                updateOrgDataSyncConfig.setFetchedSeqNo(reqBO.getStartSeq());
                tenantDataSyncConfigService.updateById(updateOrgDataSyncConfig);
            }

            //更新成功日志
            updateSuccessRecord(tenantDataSyncLogDO, count);
        } catch (Exception e) {
            LOGGER.error("获取聊天记录异常，orgId[{}] taskid[{}]", tenantDataSyncLogDO.getTenantId(),
                    tenantDataSyncLogDO.getTaskId(), e);
            //更新失败日志
            updateFailRecord(tenantDataSyncLogDO, count, e.toString());
        } finally {
            if (StringUtils.equals(tenantDataSyncLogDO.getTaskStatus(), TaskStatusEnum.FAILURE.getStatus())
                    || StringUtils.equals(tenantDataSyncLogDO.getTaskStatus(), TaskStatusEnum.SUCCESS.getStatus())) {
                //更新失败日志
                updateFailRecord(tenantDataSyncLogDO, count, "运行中出现未知异常");
            }
            //任务解锁
            tenantDataSyncConfigService.updateUnLockByTenantIdAndTaskType(tenantId, TaskTypeEnum.MESSAGE_SYNC.getType());
        }
    }

    /**
     * 获取并存储数据
     * @param reqBO 拉取数据的请求BO
     * @param privateKey 私钥
     * @param orgId 机构id
     * @param taskId 任务id
     * @return 此次成功的数量
     */
    private Integer fetchAndStockData(WeChatWorkChattingRecordsReqBO reqBO, String privateKey, String orgId,
                                   String taskId) {
        Integer count = 0;
        while (true) {
            WeChatWorkChattingRecordsResponseBO responseBO = ChattingRecordsUtils.fetchChattingRecords(reqBO);
            weChatWorkInteractionClient.checkResponseStatus(responseBO.getErrorCode(), responseBO.getErrorMessage(),
                    "拉取会话数据失败");

            List<WeChatWorkChatDataBO> weChatWorkChatDataBOS = responseBO.getWeChatWorkChatDataBOS();

            if (CollectionUtils.isEmpty(weChatWorkChatDataBOS)) {
                LOGGER.warn("拉取会话数据，记录为空");
                LOGGER.info("此次[{}]拉取聊天记录，一共同步[{}]条记录", taskId, count);
                break;
            }

            //会话消息排序
            weChatWorkChatDataBOS.sort(Comparator.comparing(WeChatWorkChatDataBO::getSequenceNo));

            List<MsgRecordDO> msgRecordDOS = new ArrayList<>();
            weChatWorkChatDataBOS.forEach(w -> {
                try {
                    //解密消息
                    WeChatWorkChattingRecordsBO recordsBO = ChattingRecordsUtils.decryptMessage(
                            reqBO.getSdk(), privateKey, w.getEncryptRandomKey(), w.getEncryptChatMessage());
                    //转换实体并放到msgRecordDOS中
                    msgRecordDOS.add(MsgRecordConvert.convert2MsgRecordDO(recordsBO, orgId, taskId, w.getSequenceNo()));
                } catch (Exception e) {
                    LOGGER.error("解密会话信息异常", e);
                    throw new BusinessException(SassBizResultCodeEnum.FAIL, "解密会话信息异常");
                }
            });

            //将记录落库
            msgRecordService.insertBatch(msgRecordDOS);

            count = count + weChatWorkChatDataBOS.size();
            LOGGER.info("此次[{}]拉取聊天记录，已同步[{}]条记录", taskId, count);

            //拿最新的消息序列号
            reqBO.setStartSeq(weChatWorkChatDataBOS.get(weChatWorkChatDataBOS.size() - 1).getSequenceNo());
        }
        return count;
    }

    /**
     * 初始化任务日志
     * @param orgId 机构id
     * @return 初始化任务日志
     */
    private TenantDataSyncLogDO initLog(String orgId) {
        TenantDataSyncLogDO tenantDataSyncLogDO = new TenantDataSyncLogDO();
        tenantDataSyncLogDO.setTaskId(SnowFlakeUtil.generateId(SnowFlakeUtil.TK_PREFIX));
        tenantDataSyncLogDO.setTenantId(orgId);
        tenantDataSyncLogDO.setTaskType(TaskTypeEnum.MESSAGE_SYNC.getType());
        tenantDataSyncLogDO.setTaskDate(DateUtils.getToday(DateUtils.DATE_FORMAT_NOSIGN));
        tenantDataSyncLogDO.setTaskTime(DateUtils.getToday(DateUtils.DATE_FORMAT_TIME));
        tenantDataSyncLogDO.setTaskStatus(TaskStatusEnum.INIT.getStatus());
        tenantDataSyncLogDO.setGmtCreator(CommonConstants.GMT_CREATOR_SYSTEM);
        tenantDataSyncLogService.insertReturnId(tenantDataSyncLogDO);
        return tenantDataSyncLogDO;
    }

    /**
     * 更新成功的日志
     * @param tenantDataSyncLogDO 日志
     * @param count 此次更新成功的数量
     */
    private void updateSuccessRecord(TenantDataSyncLogDO tenantDataSyncLogDO, Integer count) {
        TenantDataSyncLogDO successRecord = new TenantDataSyncLogDO();
        successRecord.setId(tenantDataSyncLogDO.getId());
        successRecord.setMessageCount(count);
        successRecord.setTaskStatus(TaskStatusEnum.SUCCESS.getStatus());
        tenantDataSyncLogService.updateById(successRecord);
    }

    /**
     * 更新失败的日志
     * @param tenantDataSyncLogDO 日志
     * @param count 此次更新成功的数量
     * @param message 异常信息
     */
    private void updateFailRecord(TenantDataSyncLogDO tenantDataSyncLogDO, Integer count, String message) {
        TenantDataSyncLogDO failRecord = new TenantDataSyncLogDO();
        failRecord.setTaskStatus(TaskStatusEnum.FAILURE.getStatus());
        failRecord.setErrorCode(TaskErrorEnum.IMPORTING_EXCEPTION.getErrorCode());
        if (StringUtils.isNotBlank(message)) {
            failRecord.setErrorDesc(message.length() > 500
                    ? StringUtils.substring(message, 0, 500) : message);
        } else {
            failRecord.setErrorDesc(TaskErrorEnum.IMPORTING_EXCEPTION.getErrorDesc());
        }

        failRecord.setMessageCount(count);
        failRecord.setId(tenantDataSyncLogDO.getId());
        tenantDataSyncLogService.updateById(failRecord);
    }

}
