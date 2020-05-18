package cn.com.xinxin.sass.biz.service.impl;

import cn.com.xinxin.sass.biz.service.GroupChatService;
import cn.com.xinxin.sass.common.CommonUtils;
import cn.com.xinxin.sass.common.enums.SassBizResultCodeEnum;
import cn.com.xinxin.sass.repository.dao.GroupChatDOMapper;
import cn.com.xinxin.sass.repository.model.GroupChatDO;
import com.xinxinfinance.commons.exception.BusinessException;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: liuhangzhou
 * @created: 2020/5/18.
 * @updater:
 * @description: 企业微信群信息服务
 */
@Service
public class GroupChatServiceImpl implements GroupChatService {

    private static final Logger LOGGER = LoggerFactory.getLogger(GroupChatServiceImpl.class);

    private final GroupChatDOMapper groupChatDOMapper;

    public GroupChatServiceImpl(final GroupChatDOMapper groupChatDOMapper) {
        this.groupChatDOMapper = groupChatDOMapper;
    }

    /**
     * 通过群聊id查询群信息
     * @param chatIdS 群id
     * @return 群信息
     */
    @Override
    public List<GroupChatDO> selectByChatIdS(List<String> chatIdS) {
        if (CollectionUtils.isEmpty(chatIdS)) {
            LOGGER.error("通过群聊id查询群信息, chatIdS不能为空");
            throw new BusinessException(SassBizResultCodeEnum.ILLEGAL_PARAMETER, "通过群聊id查询群信息, chatIdS不能为空");
        }
        return groupChatDOMapper.selectByChatIdS(chatIdS);
    }

    /**
     * 批量插入记录
     * @param groupChatDOS 记录
     * @return 成功插入记录的条数
     */
    @Override
    public int insertBatch(List<GroupChatDO> groupChatDOS) {
        if (CollectionUtils.isEmpty(groupChatDOS)) {
            LOGGER.error("批量插入记录, groupChatDOS不能为空");
            throw new BusinessException(SassBizResultCodeEnum.ILLEGAL_PARAMETER, "批量插入记录, groupChatDOS不能为空");
        }
        return groupChatDOMapper.insertBatch(groupChatDOS);
    }

    /**
     * 批量更新记录
     * @param groupChatDOS 记录
     * @return 成功更新记录的条数
     */
    @Override
    public int updateBatch(List<GroupChatDO> groupChatDOS) {
        if (CollectionUtils.isEmpty(groupChatDOS)) {
            LOGGER.error("批量更新记录, groupChatDOS不能为空");
            throw new BusinessException(SassBizResultCodeEnum.ILLEGAL_PARAMETER, "批量更新记录, groupChatDOS不能为空");
        }
        return groupChatDOMapper.updateBatch(groupChatDOS);
    }

    /**
     * 分批批量插入记录
     * @param groupChatDOS 记录
     * @param size 大小
     */
    @Override
    public void insertBatchPartially(List<GroupChatDO> groupChatDOS, int size) {
        List<List<GroupChatDO>> groupChatDOSList = CommonUtils.split(groupChatDOS, size);
        groupChatDOSList.forEach(this::insertBatch);
    }

    /**
     * 分批批量更新记录
     * @param groupChatDOS 记录
     * @param size 大小
     */
    @Override
    public void updateBatchByIdPartially(List<GroupChatDO> groupChatDOS, int size){
        List<List<GroupChatDO>> groupChatDOSList = CommonUtils.split(groupChatDOS, size);
        groupChatDOSList.forEach(this::updateBatch);
    }
}
