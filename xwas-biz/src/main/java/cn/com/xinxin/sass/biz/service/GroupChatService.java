package cn.com.xinxin.sass.biz.service;

import cn.com.xinxin.sass.repository.model.GroupChatDO;

import java.util.List;

/**
 * @author: liuhangzhou
 * @created: 2020/5/18.
 * @updater:
 * @description: 企业微信群信息服务
 */
public interface GroupChatService {
    /**
     * 通过群聊id查询群信息
     * @param chatIdS 群id
     * @return 群信息
     */
    List<GroupChatDO> selectByChatIdS( List<String> chatIdS);

    /**
     * 批量插入记录
     * @param groupChatDOS 记录
     * @return 成功插入记录的条数
     */
    int insertBatch(List<GroupChatDO> groupChatDOS);

    /**
     * 批量更新记录
     * @param groupChatDOS 记录
     * @return 成功更新记录的条数
     */
    int updateBatch(List<GroupChatDO> groupChatDOS);

    /**
     * 分批批量插入记录
     * @param groupChatDOS 记录
     * @param size 大小
     */
    void insertBatchPartially(List<GroupChatDO> groupChatDOS, int size);

    /**
     * 分批批量更新记录
     * @param groupChatDOS 记录
     * @param size 大小
     */
    void updateBatchByIdPartially(List<GroupChatDO> groupChatDOS, int size);
}
