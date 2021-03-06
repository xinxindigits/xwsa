package cn.com.xinxin.sass.biz.service.impl;

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

import cn.com.xinxin.sass.biz.service.CustomerService;
import cn.com.xinxin.sass.common.CommonUtils;
import cn.com.xinxin.sass.common.enums.SassBizResultCodeEnum;
import cn.com.xinxin.sass.common.model.PageResultVO;
import cn.com.xinxin.sass.repository.dao.CustomerDOMapper;
import cn.com.xinxin.sass.repository.model.CustomerDO;
import cn.com.xinxin.sass.repository.model.MemberDO;
import com.github.pagehelper.PageHelper;
import com.xinxinfinance.commons.exception.BusinessException;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
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
     * @param customerName 客户名称
     * @return 分页查询的客户信息
     */
    @Override
    public PageResultVO<CustomerDO> queryByOrgIdAndMemberUserIdSAndTime(List<String> memberUserIdS, String startTime,
                                                                        String endTime, PageResultVO page,
                                                                        String orgId, String customerName) {

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

        Long count = customerDOMapper.selectCountByOrgIdAndMemberUserIdSAndTimeAndCustName(
                memberUserIdS, startTime, endTime, orgId, customerName);

        List<CustomerDO> customerDOS = new ArrayList<>();

        //结果
        PageResultVO<CustomerDO> resultVO = new PageResultVO<>();
        resultVO.setPageNumber(page.getPageNumber());
        resultVO.setPageSize(page.getPageSize());
        resultVO.setTotal(count);

        if (count > 0L) {
            //客户信息
            customerDOS = customerDOMapper.selectPageByOrgIdAndMemberUserIdSAndTimeAndCustName(memberUserIdS, startTime,
                    endTime, index, page.getPageSize(), orgId, customerName);
        }

        resultVO.setItems(customerDOS);

        return resultVO;
    }



    @Override
    public PageResultVO<CustomerDO> queryCustomerByPages(PageResultVO page) {


        com.github.pagehelper.Page doPage = PageHelper.startPage(page.getPageNumber(),page.getPageSize());


        List<CustomerDO> customerDOS = this.customerDOMapper.queryAllCustomerByPages();

        PageResultVO<CustomerDO> result = new PageResultVO<>();
        result.setPageNumber(page.getPageNumber());
        result.setPageSize(page.getPageSize());
        result.setTotal(doPage.getTotal());
        result.setItems(customerDOS);

        return result;

    }

    /**
     * 通过id查询
     * @param id 数据库主键
     * @return 客户信息
     */
    @Override
    public CustomerDO queryById(Long id) {
        if (null == id) {
            LOGGER.error("通过id查询客户,id不能为空");
            throw new BusinessException(SassBizResultCodeEnum.ILLEGAL_PARAMETER,
                    "通过id查询客户,id不能为空");
        }
        return customerDOMapper.selectByPrimaryKey(id);
    }

    /**
     * 分批批量插入记录
     * @param customerDOS 客户信息
     * @param size 大小
     */
    @Override
    public void insertBatchPartially(List<CustomerDO> customerDOS, int size) {
        List<List<CustomerDO>> customerDOSList = CommonUtils.split(customerDOS, size);
        customerDOSList.forEach(this::insertBatch);
    }

    /**
     * 分批批量更新记录
     * @param customerDOS 客户信息
     * @param size 大小
     */
    @Override
    public void updateBatchPartially(List<CustomerDO> customerDOS, int size) {
        List<List<CustomerDO>> customerDOSList = CommonUtils.split(customerDOS, size);
        customerDOSList.forEach(this::updateBatch);
    }

    /**
     * 将记录状态置为失效
     * @param tenantId 租户id
     * @param taskId 任务流水
     * @return 成功更新的条数
     */
    @Override
    public int updateInactiveStatus(String tenantId, String taskId) {
        if (StringUtils.isBlank(tenantId)) {
            LOGGER.error("将记录状态置为失效, tenantId不能为空");
            throw new BusinessException(SassBizResultCodeEnum.ILLEGAL_PARAMETER, "将记录状态置为失效, tenantId不能为空");
        }
        if (StringUtils.isBlank(taskId)) {
            LOGGER.error("将记录状态置为失效, taskId不能为空");
            throw new BusinessException(SassBizResultCodeEnum.ILLEGAL_PARAMETER, "将记录状态置为失效, taskId不能为空");
        }
        return customerDOMapper.updateInactiveStatus(tenantId, taskId);
    }

    /**
     * 查询客户用户名
     * @param tenantId 租户id
     * @param userIdS 客户userids
     * @return 客户用户名及userid
     */
    @Override
    public List<CustomerDO> queryCustomerNameByTenantIdAndUserIdS(String tenantId, List<String> userIdS) {
        if (StringUtils.isBlank(tenantId)) {
            LOGGER.error("通过租户id和客户userId查询客户信息,tenantId不能为空");
            throw new BusinessException(SassBizResultCodeEnum.ILLEGAL_PARAMETER,
                    "通过租户id和客户userId查询客户信息,tenantId不能为空");
        }
        if (CollectionUtils.isEmpty(userIdS)) {
            LOGGER.error("通过租户id和客户userId查询客户信息, userIdS不能为空");
            throw new BusinessException(SassBizResultCodeEnum.ILLEGAL_PARAMETER,
                    "通过租户id和客户userId查询客户信息, userIdS不能为空");
        }
        return customerDOMapper.queryCustomerNameByTenantIdAndUserIdS(tenantId, userIdS);
    }
}
