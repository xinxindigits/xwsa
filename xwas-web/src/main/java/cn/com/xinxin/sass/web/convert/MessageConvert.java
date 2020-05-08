package cn.com.xinxin.sass.web.convert;

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
        msgRecordVO.setContent(msgRecordDO.getContent());
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
}
