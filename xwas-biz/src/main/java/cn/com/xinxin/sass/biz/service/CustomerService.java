package cn.com.xinxin.sass.biz.service;

import cn.com.xinxin.sass.common.model.PageResultVO;
import cn.com.xinxin.sass.repository.model.CustomerDO;

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
     * @return 分页查询的客户信息
     */
    PageResultVO<CustomerDO> queryByOrgIdAndMemberUserIdSAndTime(List<String> memberUserIdS, String startTime,
                                                                 String endTime, PageResultVO page, String orgId);
}
