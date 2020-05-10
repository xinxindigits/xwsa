package cn.com.xinxin.sass.biz.service.wechatwork;

/**
 * @author: liuhangzhou
 * @created: 2020/5/10.
 * @updater:
 * @description: 企业微信同步服务
 */
public interface WeChatWorkSyncService {

    /**
     * 同步任务
     * @param tenantId 租户id
     */
    void sync(String tenantId);
}
