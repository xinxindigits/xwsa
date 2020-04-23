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
}
