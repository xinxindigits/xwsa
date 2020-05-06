package cn.com.xinxin.sass.biz.service;

import cn.com.xinxin.sass.biz.model.bo.ChatPartyBO;
import cn.com.xinxin.sass.biz.vo.ChatUserVO;
import cn.com.xinxin.sass.common.model.PageResultVO;
import cn.com.xinxin.sass.repository.model.MsgRecordDO;

import java.util.List;

/**
 * @author: liuhangzhou
 * @created: 2020/4/27.
 * @updater:
 * @description: 消息记录服务
 */
public interface MsgRecordService {

    /**
     * 批量插入记录
     * @param msgRecordDOS 消息记录
     * @return 插入记录条数
     */
    int insertBatch(List<MsgRecordDO> msgRecordDOS);

    /**
     * 通过机构id，userid，消息发送时间范围查询记录
     * @param userId 用户id
     * @param startTime 消息发送时间范围之起始时间
     * @param endTime 消息发送时间范围之终止时间
     * @param page 分页信息
     * @param orgId 机构id
     * @return 消息记录
     */
    PageResultVO<MsgRecordDO> queryByOrgIdAndMemberUserIdAndTime(String userId, String startTime,
                                                                  String endTime, PageResultVO page, String orgId);

    /**
     * 通过id查询会话详情
     * @param id id
     * @return 消息纪录
     */
    MsgRecordDO queryById(Long id);

    PageResultVO<MsgRecordDO> selectMsgRecordBetweenPersons(PageResultVO page,String orgId, String userIdOne, String userIdTwo);

    PageResultVO<MsgRecordDO> selectRoomMsgRecord(PageResultVO page,String orgId, String roomId);

    ChatUserVO getChatUser(String orgId, String chatUserId);

    /**
     * 通过租户id和成员userid查询聊天方
     * @param tenantId 租户id
     * @param userId 成员userid
     * @return 聊天方
     */
    List<ChatPartyBO> selectByMemberUserId(String tenantId, String userId);
}
