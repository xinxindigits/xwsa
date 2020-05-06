package cn.com.xinxin.sass.biz.service.impl;

import cn.com.xinxin.sass.biz.service.CustomerReceivedService;
import cn.com.xinxin.sass.common.CommonUtils;
import cn.com.xinxin.sass.common.enums.SassBizResultCodeEnum;
import cn.com.xinxin.sass.repository.dao.CustomerReceivedDOMapper;
import cn.com.xinxin.sass.repository.model.CustomerReceivedDO;
import com.xinxinfinance.commons.exception.BusinessException;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
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

    /**
     * 分页查询记录
     * @param taskId 任务id
     * @param memberUserIdS 成员userid列表
     * @param startId 开始的id
     * @param pageSize 页的大小
     * @return 客户暂存信息
     */
    @Override
    public List<CustomerReceivedDO> selectByTaskIdMemberUserIdS(String taskId, List<String> memberUserIdS, Long startId,
                                                          Long pageSize) {
        if (StringUtils.isBlank(taskId)) {
            LOGGER.error("分页查询记录, taskId不能为空");
            throw new BusinessException(SassBizResultCodeEnum.ILLEGAL_PARAMETER, "分页查询记录, taskId不能为空");
        }
        if (CollectionUtils.isEmpty(memberUserIdS)) {
            LOGGER.error("分页查询记录, memberUserIdS不能为空");
            throw new BusinessException(SassBizResultCodeEnum.ILLEGAL_PARAMETER, "分页查询记录, memberUserIdS不能为空");
        }
        if (null == startId) {
            LOGGER.error("分页查询记录, startId不能为空");
            throw new BusinessException(SassBizResultCodeEnum.ILLEGAL_PARAMETER, "分页查询记录, startId不能为空");
        }
        if (null == pageSize) {
            LOGGER.error("分页查询记录, pageSize不能为空");
            throw new BusinessException(SassBizResultCodeEnum.ILLEGAL_PARAMETER, "分页查询记录, pageSize不能为空");
        }
        return customerReceivedDOMapper.selectByTaskIdMemberUserIdS(taskId, memberUserIdS, startId, pageSize);
    }

    /**
     * 分批批量插入记录
     * @param customerReceivedDOS 记录
     * @param size 每次插入的数量
     */
    @Override
    public void insertBatchPartially(List<CustomerReceivedDO> customerReceivedDOS, int size) {
        List<List<CustomerReceivedDO>> customerReceivedDOSList = CommonUtils.split(customerReceivedDOS, size);
        customerReceivedDOSList.forEach(this::insertBatch);
    }
}
