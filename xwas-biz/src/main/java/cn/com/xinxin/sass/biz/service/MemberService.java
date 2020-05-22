package cn.com.xinxin.sass.biz.service;

/*
 *
 * Copyright 2020 www.xinxindigits.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software
 * and associated documentation files (the "Software"),to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice
 * shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 * Redistribution and selling copies of the software are prohibited, only if the authorization from xinxin digits
 * was obtained.Neither the name of the xinxin digits; nor the names of its contributors may be used to
 * endorse or promote products derived from this software without specific prior written permission.
 *
 */

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
     * @param userId
     * @return
     */
    MemberDO queryMemberDetailByUserId(String userId);



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

    /**
     * 查询成员用户名
     * @param tenantId 租户id
     * @param userIdS 成员userids
     * @return 成员用户名及userid
     */
    List<MemberDO> queryMemberNameByTenantIdAndUserIdS(String tenantId,
                                                       List<String> userIdS);

}
