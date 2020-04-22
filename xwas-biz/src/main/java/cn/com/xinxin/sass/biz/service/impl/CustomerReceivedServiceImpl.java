package cn.com.xinxin.sass.biz.service.impl;

import cn.com.xinxin.sass.biz.service.CustomerReceivedService;
import cn.com.xinxin.sass.repository.dao.CustomerReceivedDOMapper;
import cn.com.xinxin.sass.repository.model.CustomerReceivedDO;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: liuhangzhou
 * @created: 2020/4/21.
 * @updater:
 * @description: 客户信息暂存表数据库服务
 */
@Service
public class CustomerReceivedServiceImpl implements CustomerReceivedService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerReceivedServiceImpl.class);

    private final CustomerReceivedDOMapper customerReceivedDOMapper;

    public CustomerReceivedServiceImpl(final CustomerReceivedDOMapper customerReceivedDOMapper) {
        this.customerReceivedDOMapper = customerReceivedDOMapper;
    }

    /**
     * 批量插入记录
     * @param customerReceivedDOS 记录
     * @return 插入成功条数
     */
    @Override
    public int insertBatch(List<CustomerReceivedDO> customerReceivedDOS) {
        if (CollectionUtils.isEmpty(customerReceivedDOS)) {
            LOGGER.warn("此次批量插入数据到客户信息暂存表，数据为空");
            return 0;
        }
        return customerReceivedDOMapper.insertBatch(customerReceivedDOS);
    }
}
