package cn.com.xinxin.sass.biz.service.wechatwork;

/**
 * @author: liuhangzhou
 * @created: 2020/5/6.
 * @updater:
 * @description: 企业微信通讯录同步服务
 */
public interface WeChatWorkAddressListSyncService {

    /**
     * 同步企业微信通讯录
     * @param tenantId 租户id
     */
    void syncWeChatWorkAddressList(String tenantId);
}
