package cn.com.xinxin.sass.web.convert;

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

import cn.com.xinxin.sass.biz.vo.ChatUserVO;
import cn.com.xinxin.sass.common.constants.CommonConstants;
import cn.com.xinxin.sass.common.constants.WeChatWorkChatRecordsTypeConstants;
import cn.com.xinxin.sass.repository.model.MsgRecordDO;
import cn.com.xinxin.sass.web.vo.MsgRecordVO;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: liuhangzhou
 * @created: 2020/4/29.
 * @updater:
 * @description: 企业微信会话记录转换类
 */
public class MessageConvert {
    /**
     * 将MsgRecordDO转化为MsgRecordVO
     * @param msgRecordDO 消息记录DO
     * @return 消息记录VO
     */
    public static MsgRecordVO convert2MsgRecordVO(MsgRecordDO msgRecordDO) {
        MsgRecordVO msgRecordVO = new MsgRecordVO();
        if (null == msgRecordDO) {
            return msgRecordVO;
        }
        msgRecordVO.setId(msgRecordDO.getId());
        msgRecordVO.setTenantId(msgRecordDO.getTenantId());
        msgRecordVO.setSeqId(msgRecordDO.getSeqId());
        msgRecordVO.setMsgId(msgRecordDO.getMsgId());
        msgRecordVO.setAction(msgRecordDO.getAction());
        msgRecordVO.setFromUserId(msgRecordDO.getFromUserId());
        msgRecordVO.setRoomId(msgRecordDO.getRoomId());
        msgRecordVO.setMsgTime(msgRecordDO.getMsgTime());
        msgRecordVO.setMsgType(msgRecordDO.getMsgType());
        msgRecordVO.setToUserId(msgRecordDO.getToUserId());
        JSONObject jsonObject = JSONObject.parseObject(msgRecordDO.getContent());
        if (null != jsonObject) {
            msgRecordVO.setContent((String)JSONObject.parseObject(msgRecordDO.getContent()).get(CommonConstants.CONTENT));
        }
        return msgRecordVO;
    }

    /**
     * 将MsgRecordDOList转化为MsgRecordVOList
     * @param msgRecordDOS 消息记录DOList
     * @return 消息记录VOList
     */
    public static List<MsgRecordVO> convert2MsgRecordVOList(List<MsgRecordDO> msgRecordDOS) {
        List<MsgRecordVO> msgRecordVOS = new ArrayList<>();
        msgRecordDOS.forEach(m -> msgRecordVOS.add(convert2MsgRecordVO(m)));
        return msgRecordVOS;
    }

    public static List<MsgRecordVO> convert2MsgRecordVOList(List<MsgRecordDO> msgRecordDOS, ChatUserVO chatUserOneVO, ChatUserVO chatUserTwoVO) {
        List<MsgRecordVO> msgRecordVOS = new ArrayList<>();
        msgRecordDOS.forEach(msgRecordDO -> {
            MsgRecordVO msgRecordVO = convert2MsgRecordVO(msgRecordDO);
            if(WeChatWorkChatRecordsTypeConstants.TEXT.equals(msgRecordDO.getMsgType())){
                msgRecordVO.setContent((String)JSONObject.parseObject(msgRecordDO.getContent()).get(CommonConstants.CONTENT));
            }
            if(StringUtils.equals(msgRecordDO.getFromUserId(),chatUserOneVO.getChatUserId())){
                msgRecordVO.setFromUserName(chatUserOneVO.getChatUserName());
                msgRecordVO.setAvatar(chatUserOneVO.getAvatar());
            }else{
                msgRecordVO.setFromUserName(chatUserTwoVO.getChatUserName());
                msgRecordVO.setAvatar(chatUserTwoVO.getAvatar());
            }
             msgRecordVOS.add(msgRecordVO);
        });
        return msgRecordVOS;
    }

    /**
     * 将MsgRecordDO转化为MsgRecordVO
     * @param msgRecordDO 消息记录DO
     * @param fromUserName 发送方
     * @param toName 接受方
     * @param type 类型 0-人 ，1-群
     * @return 消息记录VO
     */
    public static MsgRecordVO convert2MsgRecordVO(MsgRecordDO msgRecordDO, String fromUserName, String toName,
                                                  Integer type) {
        MsgRecordVO msgRecordVO = convert2MsgRecordVO(msgRecordDO);
        msgRecordVO.setFromUserName(fromUserName);
        msgRecordVO.setToChatPartyName(toName);
        msgRecordVO.setType(type);
        return msgRecordVO;
    }
}
