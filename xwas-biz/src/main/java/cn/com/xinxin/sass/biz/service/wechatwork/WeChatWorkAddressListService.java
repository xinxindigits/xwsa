package cn.com.xinxin.sass.biz.service.wechatwork;

/**
 * @author: liuhangzhou
 * @created: 2020/4/21.
 * @updater:
 * @description: 企业微信通讯录相关服务
 */
public interface WeChatWorkAddressListService {

    /**
     * 获取并导入通讯录数据到待导入表
     *
     * @param corporationId 机构id
     * @param addressListSecret 通讯录应用Secret
     * @param customerContactSecret 客户联系应用Secret
     * @param taskId 任务id
     * @param orgId 机构id
     */
    void fetchAndImportAddressList(String corporationId, String addressListSecret, String customerContactSecret,
                                   String taskId, String orgId);

}
