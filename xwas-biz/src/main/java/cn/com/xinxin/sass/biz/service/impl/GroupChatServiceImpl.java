package cn.com.xinxin.sass.biz.service.impl;

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

import cn.com.xinxin.sass.biz.service.GroupChatService;
import cn.com.xinxin.sass.biz.service.MsgRecordService;
import cn.com.xinxin.sass.biz.vo.ChatUserVO;
import cn.com.xinxin.sass.common.CommonUtils;
import cn.com.xinxin.sass.common.enums.SassBizResultCodeEnum;
import cn.com.xinxin.sass.repository.dao.GroupChatDOMapper;
import cn.com.xinxin.sass.repository.model.GroupChatDO;
import cn.com.xinxin.sass.sal.model.wechatwork.WeChatWorkGroupChatMemberListBO;
import com.alibaba.fastjson.JSON;
import com.xinxinfinance.commons.exception.BusinessException;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
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
    private final MsgRecordService msgRecordService;

    public GroupChatServiceImpl(final GroupChatDOMapper groupChatDOMapper,
                                final MsgRecordService msgRecordService) {
        this.groupChatDOMapper = groupChatDOMapper;
        this.msgRecordService = msgRecordService;
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

    /**
     * 根据群id查询群名
     * @param chatId 群id
     * @param tenantId 租户id
     * @return 群名
     */
    @Override
    public String queryChatNameByChatId(String chatId, String tenantId) {
        if (StringUtils.isEmpty(chatId)) {
            LOGGER.error("根据群id查询群名, chatId不能为空");
            throw new BusinessException(SassBizResultCodeEnum.ILLEGAL_PARAMETER, "根据群id查询群名, chatId不能为空");
        }
        GroupChatDO groupChatDO = groupChatDOMapper.selectByChatId(chatId);

        if (null == groupChatDO) {
            return chatId;
        }

        if (StringUtils.isNotBlank(groupChatDO.getChatName())) {
            return groupChatDO.getChatName();
        }

        List<WeChatWorkGroupChatMemberListBO> weChatWorkGroupChatMemberListBOS = JSON.parseArray(
                groupChatDO.getMemberList(), WeChatWorkGroupChatMemberListBO.class);

        StringBuilder sb = new StringBuilder();

        weChatWorkGroupChatMemberListBOS.forEach(w -> {
            ChatUserVO chatUserVO = msgRecordService.getChatUser(tenantId, w.getUserId());
            sb.append(chatUserVO.getChatUserName()).append(",");
        });

        if (StringUtils.isBlank(sb)) {
            return chatId;
        }

        return sb.deleteCharAt(sb.lastIndexOf(",")).toString();
    }
}
