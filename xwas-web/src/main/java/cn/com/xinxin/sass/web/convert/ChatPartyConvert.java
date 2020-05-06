package cn.com.xinxin.sass.web.convert;

import cn.com.xinxin.sass.biz.model.bo.ChatPartyBO;
import cn.com.xinxin.sass.web.vo.ChatPartyVO;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: liuhangzhou
 * @created: 2020/5/6.
 * @updater:
 * @description: 聊天方转换类
 */
public class ChatPartyConvert {
    /**
     * 将BO转换成VO
     * @param chatPartyBO  聊天方BO
     * @return VO
     */
    public static ChatPartyVO convert2ChatParty(ChatPartyBO chatPartyBO) {
        ChatPartyVO chatPartyVO = new ChatPartyVO();
        chatPartyVO.setType(chatPartyBO.getType());
        chatPartyVO.setRoomId(chatPartyBO.getRoomId());
        chatPartyVO.setRommName(chatPartyBO.getRommName());
        chatPartyVO.setUserId(chatPartyBO.getUserId());
        chatPartyVO.setUserName(chatPartyBO.getUserName());
        return chatPartyVO;
    }

    /**
     * 将BOList转换成VOList
     * @param chatPartyBOS  聊天方BOS
     * @return VOS
     */
    public static List<ChatPartyVO> convert2ChatPartyList(List<ChatPartyBO> chatPartyBOS) {
        List<ChatPartyVO> chatPartyVOS = new ArrayList<>();
        chatPartyBOS.forEach(chatPartyBO -> chatPartyVOS.add(convert2ChatParty(chatPartyBO)));
        return chatPartyVOS;
    }
}
