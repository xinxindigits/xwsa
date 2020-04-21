package cn.com.xinxin.sass.sal.model.wechatwork.response;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

/**
 * @author: liuhangzhou
 * @created: 2020/4/20.
 * @updater:
 * @description: 企业微信客户的user_id查询BO
 */
public class WeChatWorkCustomerUserIdQueryResponseBO extends WeChatWorkResponseBaseBO {
    /**
     * 外部联系人的userid列表
     */
    @JSONField(name = "external_userid")
    private List<String> customerUserIdS;

    public List<String> getCustomerUserIdS() {
        return customerUserIdS;
    }

    public void setCustomerUserIdS(List<String> customerUserIdS) {
        this.customerUserIdS = customerUserIdS;
    }

    @Override
    public String toString() {
        return "WeChatWorkCustomerUserIdQueryResponseBO{" +
                "customerUserIdS=" + customerUserIdS +
                '}';
    }
}
