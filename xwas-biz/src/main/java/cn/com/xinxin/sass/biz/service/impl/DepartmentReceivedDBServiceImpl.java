package cn.com.xinxin.sass.biz.service.impl;

import cn.com.xinxin.sass.biz.service.DepartmentReceivedDBService;
import cn.com.xinxin.sass.repository.dao.DepartmentReceivedDOMapper;
import cn.com.xinxin.sass.repository.model.DepartmentReceivedDO;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: liuhangzhou
 * @created: 2020/4/21.
 * @updater:
 * @description: 部门信息暂存表数据库服务
 */
@Service
public class DepartmentReceivedDBServiceImpl implements DepartmentReceivedDBService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DepartmentReceivedDBServiceImpl.class);

    private final DepartmentReceivedDOMapper departmentReceivedDOMapper;

    public DepartmentReceivedDBServiceImpl(final DepartmentReceivedDOMapper departmentReceivedDOMapper) {
        this.departmentReceivedDOMapper = departmentReceivedDOMapper;
    }

    /**
     * 批量插入记录
     * @param departmentReceivedDOS 部门待导入表
     * @return 插入成功条数
     */
    @Override
    public int insertBatch(List<DepartmentReceivedDO> departmentReceivedDOS) {
        if (CollectionUtils.isEmpty(departmentReceivedDOS)) {
            LOGGER.warn("此次批量插入数据到部门信息暂存表，数据为空");
            return 0;
        }

        return departmentReceivedDOMapper.insertBatch(departmentReceivedDOS);
    }
}
