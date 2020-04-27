package cn.com.xinxin.sass.biz.service.wechatwork;

/**
 * @author: liuhangzhou
 * @created: 2020/4/26.
 * @updater:
 * @description: 企业微信聊天记录服务
 */
public interface WeChatWorkChattingRecordsService {

    /**
     * 获取聊天记录
     * @param orgId 机构id
     */
    void fetchChattingRecords(String orgId);
}
