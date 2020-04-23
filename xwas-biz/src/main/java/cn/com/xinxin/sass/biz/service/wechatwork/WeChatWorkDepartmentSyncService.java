package cn.com.xinxin.sass.biz.service.wechatwork;

/**
 * @author: liuhangzhou
 * @created: 2020/4/22.
 * @updater:
 * @description: 企业微信同步部门服务
 */
public interface WeChatWorkDepartmentSyncService {
    /**
     * 同步部门信息
     *
     * @param taskId 任务id
     * @param orgId 机构编码
     */
    void syncDepartment(String taskId, String orgId);
}
