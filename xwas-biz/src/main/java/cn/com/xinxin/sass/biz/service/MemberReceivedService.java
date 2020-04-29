package cn.com.xinxin.sass.biz.service;

import cn.com.xinxin.sass.repository.model.MemberReceivedDO;

import java.util.List;

/**
 * @author: liuhangzhou
 * @created: 2020/4/21.
 * @updater:
 * @description: 成员信息暂存表数据库服务
 */
public interface MemberReceivedService {
    /**
     * 批量插入记录
     * @param memberReceivedDOS 记录
     * @return 插入成功条数
     */
    int insertBatch(List<MemberReceivedDO> memberReceivedDOS);

    /**
     * 通过takId和orgId和部门id查询记录
     * @param taskId 任务id
     * @param orgId 机构id
     * @param departmentId 部门id
     * @return 部门暂存信息
     */
    List<MemberReceivedDO> queryByTaskIdAndOrgIdAndDepartmentId(String taskId, String orgId, String departmentId);
}
