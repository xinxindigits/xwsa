package cn.com.xinxin.sass.biz.service.wechatwork;

/**
 * @author: liuhangzhou
 * @created: 2020/4/22.
 * @updater:
 * @description: 企业微信同步成员服务
 */
public interface WeChatWorkMemberSyncService {
    /**
     * 同步部门成员
     *
     * @param taskId 任务id
     * @param orgId 机构编码
     * @param departmentId 部门id
     */
    void syncMember(String taskId, String orgId, String departmentId);
}
