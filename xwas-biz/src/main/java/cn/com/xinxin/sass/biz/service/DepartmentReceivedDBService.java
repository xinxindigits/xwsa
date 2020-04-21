package cn.com.xinxin.sass.biz.service;

import cn.com.xinxin.sass.repository.model.DepartmentReceivedDO;

import java.util.List;

/**
 * @author: liuhangzhou
 * @created: 2020/4/21.
 * @updater:
 * @description: 部门信息暂存表数据库服务
 */
public interface DepartmentReceivedDBService {

    /**
     * 批量插入记录
     * @param departmentReceivedDOS 部门待导入表
     * @return 插入成功条数
     */
    int insertBatch(List<DepartmentReceivedDO> departmentReceivedDOS);
}
