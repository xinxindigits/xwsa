package cn.com.xinxin.sass.biz.service;

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

    /**
     * 根据群id查询群名
     * @param chatId 群id
     * @param tenantId 租户id
     * @return 群名
     */
    String queryChatNameByChatId(String chatId, String tenantId);
}
