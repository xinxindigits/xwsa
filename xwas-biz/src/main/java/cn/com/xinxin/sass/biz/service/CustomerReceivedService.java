package cn.com.xinxin.sass.biz.service;

import cn.com.xinxin.sass.repository.model.CustomerReceivedDO;

import java.util.List;

/**
 * @author: liuhangzhou
 * @created: 2020/4/21.
 * @updater:
 * @description: 客户信息暂存表数据库服务
 */
public interface CustomerReceivedService {
    /**
     * 批量插入记录
     * @param customerReceivedDOS 记录
     * @return 插入成功条数
     */
    int insertBatch(List<CustomerReceivedDO> customerReceivedDOS);

    /**
     * 分页查询记录
     * @param taskId 任务id
     * @param memberUserIdS 成员userid列表
     * @param startId 开始的id
     * @param pageSize 页的大小
     * @return 客户暂存信息
     */
    List<CustomerReceivedDO> selectByTaskIdMemberUserIdS(String taskId, List<String> memberUserIdS, Long startId,
                                                         Long pageSize);
}
