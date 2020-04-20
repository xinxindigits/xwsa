package cn.com.xinxin.sass.sal.model.wechatwork.response;

import cn.com.xinxin.sass.sal.model.wechatwork.WeChatWorkUserBO;
import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

/**
 * @author: liuhangzhou
 * @created: 2020/4/20.
 * @updater:
 * @description: 企业微信成员查询BO
 */
public class WeChatWorkUserQueryResponseBO extends WeChatWorkResponseBaseBO {
    /**
     * 成员列表
     */
    @JSONField(name = "userlist")
    private List<WeChatWorkUserBO> weChatWorkUserBOS;

    public List<WeChatWorkUserBO> getWeChatWorkUserBOS() {
        return weChatWorkUserBOS;
    }

    public void setWeChatWorkUserBOS(List<WeChatWorkUserBO> weChatWorkUserBOS) {
        this.weChatWorkUserBOS = weChatWorkUserBOS;
    }

    @Override
    public String toString() {
        return "WeChatWorkUserQueryResponseBO{" +
                "weChatWorkUserBOS=" + weChatWorkUserBOS +
                '}';
    }
}
