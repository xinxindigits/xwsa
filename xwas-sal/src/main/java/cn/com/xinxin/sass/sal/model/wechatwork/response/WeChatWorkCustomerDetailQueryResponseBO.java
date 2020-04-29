package cn.com.xinxin.sass.sal.model.wechatwork.response;

import cn.com.xinxin.sass.sal.model.wechatwork.WeChatWorkCustomerBO;
import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author: liuhangzhou
 * @created: 2020/4/20.
 * @updater:
 * @description: 企业微信客户的详细信息BO
 */
public class WeChatWorkCustomerDetailQueryResponseBO extends WeChatWorkResponseBaseBO {

    /**
     * 企业微信客户的详细信息
     */
    @JSONField(name = "external_contact")
    private WeChatWorkCustomerBO weChatWorkCustomerBO;

    public WeChatWorkCustomerBO getWeChatWorkCustomerBO() {
        return weChatWorkCustomerBO;
    }

    public void setWeChatWorkCustomerBO(WeChatWorkCustomerBO weChatWorkCustomerBO) {
        this.weChatWorkCustomerBO = weChatWorkCustomerBO;
    }

    @Override
    public String toString() {
        return "WeChatWorkCustomerDetailQueryResponseBO{" +
                "weChatWorkCustomerBO=" + weChatWorkCustomerBO +
                '}';
    }
}
