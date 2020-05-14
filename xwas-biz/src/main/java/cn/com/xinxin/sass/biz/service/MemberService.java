package cn.com.xinxin.sass.biz.service;

import cn.com.xinxin.sass.common.model.PageResultVO;
import cn.com.xinxin.sass.repository.model.MemberDO;

import java.util.List;

/**
 * @author: liuhangzhou
 * @created: 2020/4/24.
 * @updater:
 * @description: 成员信息表数据库服务
 */
public interface MemberService {
    /**
     * 通过机构id和用户id查询记录
     * @param orgId 机构id
     * @param userIdS 用户id
     * @return 成员列表
     */
    List<MemberDO> queryByOrgIdAndUserId(String orgId, List<String> userIdS);

    /**
     * 批量插入
     * @param memberDOS 成员列表
     * @return 插入成功数量
     */
    int insertBatch(List<MemberDO> memberDOS);

    /**
     * 批量更新
     * @param memberDOS 成员列表
     * @return 更新成功数量
     */
    int updateBatchById(List<MemberDO> memberDOS);


    /**
     * 查询某个部门下的member
     * @param deptId
     * @return
     */
    PageResultVO<MemberDO> queryByDeptId(String deptId, PageResultVO page);


    /**
     * @param page
     * @return
     */
    PageResultVO<MemberDO> queryMembersByPages(PageResultVO page);


    /**
     * @param memeberId
     * @return
     */
    MemberDO queryMemberDetailById(String memeberId);


    /**
     *
     * @param memberName
     * @param mobile
     * @param page
     * @return
     */
    PageResultVO<MemberDO> queryByNameAndMobile(String memberName,
                                                String mobile,
                                                PageResultVO page);

    /**
     * 分批批量插入
     * @param memberDOS 成员列表
     * @param size 大小
     */
    void insertBatchPartially(List<MemberDO> memberDOS, int size);

    /**
     * 分批批量更新
     * @param memberDOS 成员列表
     * @param size 大小
     */
    void updateBatchByIdPartially(List<MemberDO> memberDOS, int size);

    /**
     * 将记录状态置为失效
     * @param tenantId 租户id
     * @param taskId 任务流水
     * @return 成功更新的条数
     */
    int updateInactiveStatus(String tenantId, String taskId);

}
