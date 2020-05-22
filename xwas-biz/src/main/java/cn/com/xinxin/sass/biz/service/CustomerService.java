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
import cn.com.xinxin.sass.repository.model.CustomerDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author: liuhangzhou
 * @created: 2020/4/24.
 * @updater:
 * @description: 客户信息表数据库服务
 */
public interface CustomerService {
    /**
     * 通过机构id和客户userId查询客户信息
     * @param orgId 机构id
     * @param userIdS 客户userid
     * @return 客户信息
     */
    List<CustomerDO> selectByOrgIdAndUserId(String orgId, List<String> userIdS);

    /**
     * 批量插入记录
     * @param customerDOS 客户信息
     * @return 成功插入数量
     */
    int insertBatch(List<CustomerDO> customerDOS);

    /**
     * 批量更新数据
     * @param customerDOS 客户信息
     * @return 成功更新条数
     */
    int updateBatch(List<CustomerDO> customerDOS);

    /**
     * 根据成员UserId列表，以及添加客户的时间范围查询成员添加的客户
     * @param memberUserIdS 成员UserId列表
     * @param startTime 起始时间
     * @param endTime 终止时间
     * @param page 分页信息
     * @param orgId 机构id
     * @param customerName 客户名称
     * @return 分页查询的客户信息
     */
    PageResultVO<CustomerDO> queryByOrgIdAndMemberUserIdSAndTime(List<String> memberUserIdS, String startTime,
                                                                 String endTime, PageResultVO page,
                                                                 String orgId, String customerName);



    PageResultVO<CustomerDO> queryCustomerByPages(PageResultVO page);

    /**
     * 通过id查询
     * @param id 数据库主键
     * @return 客户信息
     */
    CustomerDO queryById(Long id);

    /**
     * 分批批量插入记录
     * @param customerDOS 客户信息
     * @param size 大小
     */
    void insertBatchPartially(List<CustomerDO> customerDOS, int size);

    /**
     * 分批批量更新记录
     * @param customerDOS 客户信息
     * @param size 大小
     */
    void updateBatchPartially(List<CustomerDO> customerDOS, int size);

    /**
     * 将记录状态置为失效
     * @param tenantId 租户id
     * @param taskId 任务流水
     * @return 成功更新的条数
     */
    int updateInactiveStatus(String tenantId, String taskId);

    /**
     * 查询客户用户名
     * @param tenantId 租户id
     * @param userIdS 客户userids
     * @return 客户用户名及userid
     */
    List<CustomerDO> queryCustomerNameByTenantIdAndUserIdS(String tenantId,
                                                           List<String> userIdS);
}
