package cn.com.xinxin.sass.biz.service.wechatwork.impl;

import cn.com.xinxin.sass.biz.model.bo.WeChatWorkFetchDataBO;
import cn.com.xinxin.sass.biz.model.bo.WeChatWorkImportDataBO;
import cn.com.xinxin.sass.biz.service.GroupChatService;
import cn.com.xinxin.sass.biz.service.wechatwork.WeChatWorkDataService;
import cn.com.xinxin.sass.common.CommonUtils;
import cn.com.xinxin.sass.common.constants.CommonConstants;
import cn.com.xinxin.sass.repository.model.GroupChatDO;
import cn.com.xinxin.sass.sal.model.wechatwork.WeChatWorkGroupChatBaseBO;
import cn.com.xinxin.sass.sal.model.wechatwork.WeChatWorkGroupChatDetailBO;
import cn.com.xinxin.sass.sal.model.wechatwork.request.WeChatWorkGroupChatRequestBO;
import cn.com.xinxin.sass.sal.wechatwork.WeChatWorkInteractionClient;
import cn.com.xinxin.sass.sal.wechatwork.WeChatWorkRoomClient;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: liuhangzhou
 * @created: 2020/5/18.
 * @updater:
 * @description:
 */
@Service(value = "weChatWorkGroupChatDataServiceImpl")
public class WeChatWorkGroupChatDataServiceImpl implements WeChatWorkDataService {

    private final static Logger LOGGER = LoggerFactory.getLogger(WeChatWorkGroupChatDataServiceImpl.class);

    private final WeChatWorkRoomClient weChatWorkRoomClient;
    private final WeChatWorkInteractionClient weChatWorkInteractionClient;
    private final GroupChatService groupChatService;

    public WeChatWorkGroupChatDataServiceImpl(final WeChatWorkRoomClient weChatWorkRoomClient,
                                              final WeChatWorkInteractionClient weChatWorkInteractionClient,
                                              final GroupChatService groupChatService) {
        this.weChatWorkRoomClient = weChatWorkRoomClient;
        this.weChatWorkInteractionClient = weChatWorkInteractionClient;
        this.groupChatService = groupChatService;
    }

    @Override
    public void fetchData(WeChatWorkFetchDataBO weChatWorkFetchDataBO) {
        //获取外部联系人管理应用api所需要的token
        String token =  weChatWorkInteractionClient.fetchToken(
                weChatWorkFetchDataBO.getTenantBaseInfoDO().getCorpId(),
                weChatWorkFetchDataBO.getTenantBaseInfoDO().getCustomerContactSecret());

        //获取群聊列表
        WeChatWorkGroupChatRequestBO weChatWorkGroupChatRequestBO = new WeChatWorkGroupChatRequestBO();
        weChatWorkGroupChatRequestBO.setLimit(1000);
        List<WeChatWorkGroupChatBaseBO> weChatWorkGroupChatBaseBOS = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            weChatWorkGroupChatRequestBO.setOffset(0);
            weChatWorkGroupChatRequestBO.setStatusFilter(i);
            while (true) {
                List<WeChatWorkGroupChatBaseBO> result = weChatWorkRoomClient.queryGroupChatList(
                        weChatWorkGroupChatRequestBO, token);
                if (CollectionUtils.isEmpty(result)) {
                    break;
                }
                weChatWorkGroupChatBaseBOS.addAll(result);
                if (result.size() < 1000) {
                    break;
                }
                weChatWorkGroupChatRequestBO.setOffset(weChatWorkGroupChatRequestBO.getOffset() + result.size());
            }
        }

        //获取群聊详情
        List<WeChatWorkGroupChatDetailBO> weChatWorkGroupChatDetailBOS = new ArrayList<>();
        weChatWorkGroupChatBaseBOS.forEach(chatBaseBO -> {
            WeChatWorkGroupChatDetailBO result = weChatWorkRoomClient.queryGroupChatDetail(token,
                    chatBaseBO.getChatId());
            result.setStatus(chatBaseBO.getStatus());
            weChatWorkGroupChatDetailBOS.add(result);
        });

        weChatWorkFetchDataBO.setWeChatWorkGroupChatDetailBOS(weChatWorkGroupChatDetailBOS.stream()
                .filter(CommonUtils.distinctByKey(m -> (m.getChatId()))).collect(Collectors.toList()));
    }

    @Override
    public void importData(WeChatWorkImportDataBO weChatWorkImportDataBO) {
        List<List<WeChatWorkGroupChatDetailBO>> weChatWorkGroupChatDetailBOSList = CommonUtils.split(
                weChatWorkImportDataBO.getWeChatWorkGroupChatDetailBOS(), 900);


        List<GroupChatDO> insertGroupChatDOS = new ArrayList<>();
        List<GroupChatDO> updateGroupChatDOS = new ArrayList<>();

        weChatWorkGroupChatDetailBOSList.forEach(w -> {
            List<GroupChatDO> groupChatDOS = groupChatService.selectByChatIdS(
                    w.stream().map(WeChatWorkGroupChatBaseBO::getChatId).collect(Collectors.toList()));

            w.forEach(groupChatDetailBO -> {
                GroupChatDO groupChatDO = groupChatDOS.stream()
                        .filter(g -> StringUtils.equals(g.getChatId(), groupChatDetailBO.getChatId())).findFirst()
                        .orElse(null);

                //获取需要插入的记录
                if (null == groupChatDO) {
                    GroupChatDO insertGroupChatDO = new GroupChatDO();
                    insertGroupChatDO.setTenantId(weChatWorkImportDataBO.getTenantDataSyncLogDO().getTenantId());
                    insertGroupChatDO.setChatId(groupChatDetailBO.getChatId());
                    insertGroupChatDO.setChatName(groupChatDetailBO.getName());
                    insertGroupChatDO.setOwner(groupChatDetailBO.getOwner());
                    insertGroupChatDO.setCreateTime(groupChatDetailBO.getCreateTime());
                    insertGroupChatDO.setStatus(groupChatDetailBO.getStatus());
                    insertGroupChatDO.setTaskId(weChatWorkImportDataBO.getTenantDataSyncLogDO().getTaskId());
                    insertGroupChatDO.setGmtCreator(CommonConstants.GMT_CREATOR_SYSTEM);
                    insertGroupChatDO.setNotice(groupChatDetailBO.getNotice());
                    insertGroupChatDO.setMemberList(groupChatDetailBO.getMemberList());
                    insertGroupChatDOS.add(insertGroupChatDO);
                } else {
                    //获取需要更新的记录
                    fetchUpdateRecord(groupChatDetailBO, groupChatDO, updateGroupChatDOS, weChatWorkImportDataBO
                            .getTenantDataSyncLogDO().getTaskId());
                }
            });
        });

        groupChatService.insertBatchPartially(insertGroupChatDOS, CommonConstants.ONE_HUNDRED);
        groupChatService.updateBatchByIdPartially(updateGroupChatDOS, CommonConstants.ONE_HUNDRED);
    }

    /**
     * 获取需要更新的记录
     * @param weChatWorkGroupChatDetailBO 此次获取的记录
     * @param groupChatDO 数据库的记录
     * @param updateGroupChatDOS 需要更新的记录
     * @param taskId 任务id
     */
    private void fetchUpdateRecord(WeChatWorkGroupChatDetailBO weChatWorkGroupChatDetailBO, GroupChatDO groupChatDO,
                                   List<GroupChatDO> updateGroupChatDOS, String taskId) {
        GroupChatDO updateGroupChatDO = new GroupChatDO();
        int count = 0;
        if (!StringUtils.equals(weChatWorkGroupChatDetailBO.getName(), groupChatDO.getChatName())) {
            updateGroupChatDO.setChatName(weChatWorkGroupChatDetailBO.getName());
            count ++;
        }
        if (!StringUtils.equals(weChatWorkGroupChatDetailBO.getNotice(), groupChatDO.getNotice())) {
            updateGroupChatDO.setNotice(weChatWorkGroupChatDetailBO.getNotice());
            count ++;
        }
        if (!StringUtils.equals(weChatWorkGroupChatDetailBO.getOwner(), groupChatDO.getOwner())) {
            updateGroupChatDO.setOwner(weChatWorkGroupChatDetailBO.getOwner());
            count ++;
        }
        if (!StringUtils.equals(weChatWorkGroupChatDetailBO.getMemberList(), groupChatDO.getMemberList())) {
            updateGroupChatDO.setMemberList(weChatWorkGroupChatDetailBO.getMemberList());
            count ++;
        }
        if (null != weChatWorkGroupChatDetailBO.getStatus()
                && !weChatWorkGroupChatDetailBO.getStatus().equals(groupChatDO.getStatus())) {
            updateGroupChatDO.setStatus(weChatWorkGroupChatDetailBO.getStatus());
            count ++;
        }

        if (count > 0) {
            updateGroupChatDO.setId(groupChatDO.getId());
            updateGroupChatDO.setTaskId(taskId);
            updateGroupChatDO.setGmtUpdater(CommonConstants.GMT_CREATOR_SYSTEM);
            updateGroupChatDOS.add(updateGroupChatDO);
            LOGGER.info("此次同步群信息，群[{}]更新[{}]个属性", groupChatDO.getChatId(), count);
        }
    }
}
