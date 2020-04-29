package cn.com.xinxin.sass.biz.service.wechatwork.impl;

import cn.com.xinxin.sass.biz.convert.MsgRecordConvert;
import cn.com.xinxin.sass.biz.service.MsgRecordService;
import cn.com.xinxin.sass.biz.service.OrgBaseInfoService;
import cn.com.xinxin.sass.biz.service.OrgDataSyncConfigService;
import cn.com.xinxin.sass.biz.service.OrgDataSyncLogService;
import cn.com.xinxin.sass.biz.service.wechatwork.WeChatWorkChattingRecordsService;
import cn.com.xinxin.sass.common.enums.SassBizResultCodeEnum;
import cn.com.xinxin.sass.common.enums.TaskErrorEnum;
import cn.com.xinxin.sass.common.enums.TaskStatusEnum;
import cn.com.xinxin.sass.common.enums.TaskTypeEnum;
import cn.com.xinxin.sass.common.utils.DateUtils;
import cn.com.xinxin.sass.common.utils.SnowFlakeUtil;
import cn.com.xinxin.sass.repository.model.MsgRecordDO;
import cn.com.xinxin.sass.repository.model.OrgBaseInfoDO;
import cn.com.xinxin.sass.repository.model.OrgDataSyncConfigDO;
import cn.com.xinxin.sass.repository.model.OrgDataSyncLogDO;
import cn.com.xinxin.sass.sal.model.wechatwork.WeChatWorkChatDataBO;
import cn.com.xinxin.sass.sal.model.wechatwork.WeChatWorkChattingRecordsBO;
import cn.com.xinxin.sass.sal.model.wechatwork.WeChatWorkChattingRecordsReqBO;
import cn.com.xinxin.sass.sal.model.wechatwork.response.WeChatWorkChattingRecordsResponseBO;
import cn.com.xinxin.sass.sal.wechatwork.WeChatWorkInteractionClient;
import com.tencent.wework.ChattingRecordsUtils;
import com.xinxinfinance.commons.exception.BusinessException;
import com.xinxinfinance.commons.result.BizResultCode;
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
@Service
public class WeChatWorkChattingRecordsServiceImpl implements WeChatWorkChattingRecordsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(WeChatWorkChattingRecordsServiceImpl.class);

    private final WeChatWorkInteractionClient weChatWorkInteractionClient;
    private final MsgRecordService msgRecordService;
    private final OrgBaseInfoService orgBaseInfoService;
    private final OrgDataSyncConfigService orgDataSyncConfigService;
    private final OrgDataSyncLogService orgDataSyncLogService;

    public WeChatWorkChattingRecordsServiceImpl(final WeChatWorkInteractionClient weChatWorkInteractionClient,
                                                final MsgRecordService msgRecordService,
                                                final OrgBaseInfoService orgBaseInfoService,
                                                final OrgDataSyncConfigService orgDataSyncConfigService,
                                                final OrgDataSyncLogService orgDataSyncLogService) {
        this.weChatWorkInteractionClient = weChatWorkInteractionClient;
        this.msgRecordService = msgRecordService;
        this.orgBaseInfoService = orgBaseInfoService;
        this.orgDataSyncConfigService = orgDataSyncConfigService;
        this.orgDataSyncLogService = orgDataSyncLogService;
    }

    /**
     * 获取聊天记录
     * @param orgId 机构id
     */
    @Override
    public void syncChattingRecords(String orgId) {
        //参数检查
        if (StringUtils.isBlank(orgId)) {
            LOGGER.error("获取聊天记录，orgId不能为空");
            throw new BusinessException(SassBizResultCodeEnum.FAIL, "获取聊天记录，orgId不能为空");
        }

        //初始化任务日志,并保存到日志表中
        OrgDataSyncLogDO orgDataSyncLogDO = initLog(orgId);

        //此次更新的数量
        Integer count = 0;

        try {
            //机构基础信息
            OrgBaseInfoDO orgBaseInfoDO = orgBaseInfoService.selectByOrgId(orgDataSyncLogDO.getOrgId());

            //机构任务配置信息
            OrgDataSyncConfigDO orgDataSyncConfigDO = orgDataSyncConfigService.selectByOrgIdAndTaskType(
                    orgBaseInfoDO.getOrgId(), TaskTypeEnum.MESSAGE_SYNC.getType());

            //初始化sdk
            long sdk = ChattingRecordsUtils.initSdk(orgBaseInfoDO.getCorpId(), orgBaseInfoDO.getChatRecordSecret());

            //拉取数据的请求BO
            WeChatWorkChattingRecordsReqBO reqBO = new WeChatWorkChattingRecordsReqBO();
            reqBO.setLimit(orgDataSyncConfigDO.getCountCeiling().longValue());
            reqBO.setSdk(sdk);
            reqBO.setTimeout(orgDataSyncConfigDO.getTimeInterval().longValue());
            reqBO.setStartSeq(orgDataSyncConfigDO.getFetchedSeqNo());

            //拉取数据并保存
            try {
                count = fetchAndStockData(reqBO, orgBaseInfoDO.getPrivateKey(), orgDataSyncLogDO.getOrgId(),
                        orgDataSyncLogDO.getTaskId());
            } finally {
                //更新配置表中消息序号
                OrgDataSyncConfigDO updateOrgDataSyncConfig = new OrgDataSyncConfigDO();
                updateOrgDataSyncConfig.setId(orgDataSyncConfigDO.getId());
                updateOrgDataSyncConfig.setFetchedSeqNo(reqBO.getStartSeq());
                orgDataSyncConfigService.updateById(updateOrgDataSyncConfig);
            }

            //更新成功日志
            updateSuccessRecord(orgDataSyncLogDO, count);
        } catch (Exception e) {
            LOGGER.error("获取聊天记录异常，orgId[{}] taskid[{}]", orgDataSyncLogDO.getOrgId(),
                    orgDataSyncLogDO.getTaskId());
            //更新失败日志
            updateFailRecord(orgDataSyncLogDO, count, e.getMessage());
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
                    LOGGER.error("解密会话信息异常");
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
    private OrgDataSyncLogDO initLog(String orgId) {
        OrgDataSyncLogDO orgDataSyncLogDO = new OrgDataSyncLogDO();
        orgDataSyncLogDO.setTaskId(SnowFlakeUtil.generateId(SnowFlakeUtil.TK_PREFIX));
        orgDataSyncLogDO.setOrgId(orgId);
        orgDataSyncLogDO.setTaskType(TaskTypeEnum.MESSAGE_SYNC.getType());
        orgDataSyncLogDO.setTaskDate(DateUtils.getToday(DateUtils.DATE_FORMAT_NOSIGN));
        orgDataSyncLogDO.setTaskTime(DateUtils.getToday(DateUtils.DATE_FORMAT_TIME));
        orgDataSyncLogDO.setTaskStatus(TaskStatusEnum.INIT.getStatus());
        orgDataSyncLogService.insertReturnId(orgDataSyncLogDO);
        return orgDataSyncLogDO;
    }

    /**
     * 更新成功的日志
     * @param orgDataSyncLogDO 日志
     * @param count 此次更新成功的数量
     */
    private void updateSuccessRecord(OrgDataSyncLogDO orgDataSyncLogDO, Integer count) {
        OrgDataSyncLogDO successRecord = new OrgDataSyncLogDO();
        successRecord.setId(orgDataSyncLogDO.getId());
        successRecord.setMessageCount(count);
        successRecord.setTaskStatus(TaskStatusEnum.SUCCESS.getStatus());
        orgDataSyncLogService.updateById(successRecord);
    }

    /**
     * 更新失败的日志
     * @param orgDataSyncLogDO 日志
     * @param count 此次更新成功的数量
     * @param message 异常信息
     */
    private void updateFailRecord(OrgDataSyncLogDO orgDataSyncLogDO, Integer count, String message) {
        OrgDataSyncLogDO failRecord = new OrgDataSyncLogDO();
        failRecord.setTaskStatus(TaskStatusEnum.FAILURE.getStatus());
        failRecord.setErrorCode(TaskErrorEnum.IMPORTING_EXCEPTION.getErrorCode());
        failRecord.setErrorDesc(message.length() > 500
                ? StringUtils.substring(message, 0, 500) : message);
        failRecord.setMessageCount(count);
        failRecord.setId(orgDataSyncLogDO.getId());
        orgDataSyncLogService.updateById(failRecord);
    }

}
