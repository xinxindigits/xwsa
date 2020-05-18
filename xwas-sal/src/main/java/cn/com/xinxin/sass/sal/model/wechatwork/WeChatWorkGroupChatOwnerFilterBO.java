package cn.com.xinxin.sass.sal.model.wechatwork;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

/**
 * @author: liuhangzhou
 * @created: 2020/5/15.
 * @updater:
 * @description: 企业微信查询群列表 群所有者过滤BO
 */
public class WeChatWorkGroupChatOwnerFilterBO {
    /**
     * 用户id列表
     */
    @JSONField(name = "userid_list")
    private List<String> userIdList;

    /**
     * 部门id列表
     */
    @JSONField(name = "partyid_list")
    private List<Integer> partyIdList;

    public List<String> getUserIdList() {
        return userIdList;
    }

    public void setUserIdList(List<String> userIdList) {
        this.userIdList = userIdList;
    }

    public List<Integer> getPartyIdList() {
        return partyIdList;
    }

    public void setPartyIdList(List<Integer> partyIdList) {
        this.partyIdList = partyIdList;
    }
}
