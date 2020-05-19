package cn.com.xinxin.sass.biz.service;

import cn.com.xinxin.sass.biz.model.bo.ChatPartyBO;
import cn.com.xinxin.sass.biz.vo.ChatUserVO;
import cn.com.xinxin.sass.biz.vo.PageVO;
import cn.com.xinxin.sass.biz.vo.QueryMsgConditionVO;
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
     * @param keyWord 关键字
     * @return 消息记录
     */
    PageResultVO<MsgRecordDO> queryByOrgIdAndMemberUserIdAndTime(String userId, String startTime,
                                                                  String endTime, PageResultVO page, String orgId,
                                                                 String keyWord);

    /**
     * 通过id查询会话详情
     * @param id id
     * @return 消息纪录
     */
    MsgRecordDO queryById(Long id);

    PageResultVO<MsgRecordDO> selectMsgRecordBetweenPersons(PageResultVO page,String orgId, String userIdOne, String userIdTwo, QueryMsgConditionVO conditionVO);

    PageResultVO<MsgRecordDO> selectRoomMsgRecord(PageResultVO page,String orgId, String roomId, QueryMsgConditionVO conditionVO);

    /**
     *
     * @param id 消息id
     * @param tenantId  租户id
     * @param roomId 群聊id
     * @param userIdOne 用户1的id
     * @param userIdTwo 用户2的id
     * @param pageSize 页面大小
     * @return
     */
    PageVO getPageIndex(Long id, String tenantId, String roomId, String userIdOne, String userIdTwo, Integer pageSize);

    ChatUserVO getChatUser(String orgId, String chatUserId);

    /**
     * 通过租户id和成员userid查询聊天方
     * @param tenantId 租户id
     * @param userId 成员userid
     * @param keyWord 关键字
     * @param startTime 消息发送时间范围之起始时间
     * @param endTime 消息发送时间范围之终止时间
     * @return 会话记录
     */
    List<ChatPartyBO> selectByMemberUserIdAndKeyWordAndTime(String tenantId,
                                                            String userId,
                                                            String keyWord,
                                                            String startTime,
                                                            String endTime);

    /**
     * 获取聊天对象用户名
     * @param tenantId 租户id
     * @param chatUserIdS 聊天对象userid
     * @return 聊天对象用户名
     */
    List<String> getChatPartyNameList(String tenantId, List<String> chatUserIdS);
}
