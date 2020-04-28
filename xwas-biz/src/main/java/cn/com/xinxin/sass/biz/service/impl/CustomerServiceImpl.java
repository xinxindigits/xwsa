package cn.com.xinxin.sass.biz.service.impl;

import cn.com.xinxin.sass.biz.service.CustomerService;
import cn.com.xinxin.sass.common.enums.SassBizResultCodeEnum;
import cn.com.xinxin.sass.common.model.PageResultVO;
import cn.com.xinxin.sass.repository.dao.CustomerDOMapper;
import cn.com.xinxin.sass.repository.model.CustomerDO;
import com.xinxinfinance.commons.exception.BusinessException;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: liuhangzhou
 * @created: 2020/4/24.
 * @updater:
 * @description: 客户信息表数据库服务
 */
@Service
public class CustomerServiceImpl implements CustomerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerServiceImpl.class);

    private final CustomerDOMapper customerDOMapper;

    public CustomerServiceImpl(final CustomerDOMapper customerDOMapper) {
        this.customerDOMapper = customerDOMapper;
    }

    /**
     * 通过机构id和客户userId查询客户信息
     * @param orgId 机构id
     * @param userIdS 客户userid
     * @return 客户信息
     */
    @Override
    public List<CustomerDO> selectByOrgIdAndUserId(String orgId, List<String> userIdS) {
        if (StringUtils.isBlank(orgId)) {
            LOGGER.error("通过机构id和客户userId查询客户信息,orgId不能为空");
            throw new BusinessException(SassBizResultCodeEnum.ILLEGAL_PARAMETER,
                    "通过机构id和客户userId查询客户信息,orgId不能为空");
        }
        if (CollectionUtils.isEmpty(userIdS)) {
            LOGGER.error("通过机构id和客户userId查询客户信息, userIdS不能为空");
            throw new BusinessException(SassBizResultCodeEnum.ILLEGAL_PARAMETER,
                    "通过机构id和客户userId查询客户信息, userIdS不能为空");
        }
        return customerDOMapper.selectByOrgIdAndUserId(orgId, userIdS);
    }

    /**
     * 批量插入记录
     * @param customerDOS 客户信息
     * @return 成功插入数量
     */
    @Override
    public int insertBatch(List<CustomerDO> customerDOS) {
        if (CollectionUtils.isEmpty(customerDOS)) {
            LOGGER.warn("批量插入记录, customerDOS为空");
            return 0;
        }
        return customerDOMapper.insertBatch(customerDOS);
    }

    /**
     * 批量更新数据
     * @param customerDOS 客户信息
     * @return 成功更新条数
     */
    @Override
    public int updateBatch(List<CustomerDO> customerDOS) {
        if (CollectionUtils.isEmpty(customerDOS)) {
            LOGGER.warn("批量更新数据, customerDOS为空");
            return 0;
        }
        return customerDOMapper.updateBatch(customerDOS);
    }

    /**
     * 根据成员UserId列表，以及添加客户的时间范围查询成员添加的客户
     * @param memberUserIdS 成员UserId列表
     * @param startTime 起始时间
     * @param endTime 终止时间
     * @param page 分页信息
     * @param orgId 机构id
     * @return 分页查询的客户信息
     */
    @Override
    public PageResultVO<CustomerDO> queryByOrgIdAndMemberUserIdSAndTime(List<String> memberUserIdS, String startTime,
                                                                        String endTime, PageResultVO page, String orgId) {

        if (StringUtils.isBlank(orgId)) {
            LOGGER.error("根据成员UserId列表，以及添加客户的时间范围查询成员添加的客户,orgId不能为空");
            throw new BusinessException(SassBizResultCodeEnum.ILLEGAL_PARAMETER,
                    "根据成员UserId列表，以及添加客户的时间范围查询成员添加的客户,orgId不能为空");
        }
        if (null == page || null == page.getPageNumber() || null == page.getPageSize()) {
            LOGGER.error("根据成员UserId列表，以及添加客户的时间范围查询成员添加的客户,page不能为空");
            throw new BusinessException(SassBizResultCodeEnum.ILLEGAL_PARAMETER,
                    "根据成员UserId列表，以及添加客户的时间范围查询成员添加的客户,page不能为空");
        }

        //计算分页的起始偏移量
        Long index = (page.getPageNumber() - 1) * page.getPageSize().longValue();

        Long count = customerDOMapper.selectCountByOrgIdAndMemberUserIdSAndTime(memberUserIdS, startTime, endTime, orgId);

        List<CustomerDO> customerDOS = new ArrayList<>();

        //结果
        PageResultVO<CustomerDO> resultVO = new PageResultVO<>();
        resultVO.setPageNumber(page.getPageNumber());
        resultVO.setPageSize(page.getPageSize());
        resultVO.setTotal(count);

        if (count > 0L) {
            //客户信息
            customerDOS = customerDOMapper.selectPageByOrgIdAndMemberUserIdSAndTime(memberUserIdS, startTime,
                    endTime, index, page.getPageSize(), orgId);
        }

        resultVO.setItems(customerDOS);

        return resultVO;
    }

}
