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

import cn.com.xinxin.sass.biz.service.TenantDataSyncLogService;
import cn.com.xinxin.sass.common.enums.SassBizResultCodeEnum;
import cn.com.xinxin.sass.common.model.PageResultVO;
import cn.com.xinxin.sass.repository.dao.TenantDataSyncLogDOMapper;
import cn.com.xinxin.sass.repository.model.TenantDataSyncLogDO;
import cn.com.xinxin.sass.repository.model.bo.TenantDataSyncLogBO;
import com.xinxinfinance.commons.exception.BusinessException;
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
 * @created: 2020/4/27.
 * @updater:
 * @description: 机构同步任务日志数据库服务
 */
@Service
public class TenantDataSyncLogServiceImpl implements TenantDataSyncLogService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TenantDataSyncLogServiceImpl.class);

    private final TenantDataSyncLogDOMapper tenantDataSyncLogDOMapper;

    public TenantDataSyncLogServiceImpl(final TenantDataSyncLogDOMapper tenantDataSyncLogDOMapper) {
        this.tenantDataSyncLogDOMapper = tenantDataSyncLogDOMapper;
    }

    /**
     * 根据id进行更新
     * @param tenantDataSyncLogDO 记录
     * @return 更新记录成功的条数
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public int updateById(TenantDataSyncLogDO tenantDataSyncLogDO) {
        if (null == tenantDataSyncLogDO) {
            LOGGER.error("根据id进行更新记录, orgDataSyncLogDO不能为空");
            throw new BusinessException(SassBizResultCodeEnum.ILLEGAL_PARAMETER,
                    "根据id进行更新记录, orgDataSyncLogDO不能为空");
        }
        if (null == tenantDataSyncLogDO.getId()) {
            LOGGER.error("根据id进行更新记录, id不能为空");
            throw new BusinessException(SassBizResultCodeEnum.ILLEGAL_PARAMETER,
                    "根据id进行更新记录, id不能为空");
        }
        return tenantDataSyncLogDOMapper.updateByPrimaryKeySelective(tenantDataSyncLogDO);
    }

    /**
     * 插入记录并将生成的id保存到orgDataSyncLogDO的id字段
     * @param tenantDataSyncLogDO 机构同步任务日志
     * @return 插入记录成功的条数
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public int insertReturnId(TenantDataSyncLogDO tenantDataSyncLogDO) {
        if (null == tenantDataSyncLogDO) {
            LOGGER.error("插入记录, orgDataSyncLogDO不能为空");
            throw new BusinessException(SassBizResultCodeEnum.ILLEGAL_PARAMETER,
                    "插入记录, orgDataSyncLogDO不能为空");
        }
        return tenantDataSyncLogDOMapper.insertReturnId(tenantDataSyncLogDO);
    }

    /**
     * 分页查询租户任务执行日志
     * @param tenantDataSyncLogBO 参数
     * @param page 分页参数
     * @return 日志
     */
    @Override
    public PageResultVO<TenantDataSyncLogDO> selectRecordSPage(TenantDataSyncLogBO tenantDataSyncLogBO, PageResultVO page) {
        if (null == tenantDataSyncLogBO) {
            LOGGER.error("分页查询租户任务执行日志, tenantDataSyncLogBO不能为空");
            throw new BusinessException(SassBizResultCodeEnum.ILLEGAL_PARAMETER,
                    "分页查询租户任务执行日志, tenantDataSyncLogBO不能为空");
        }
        if (StringUtils.isBlank(tenantDataSyncLogBO.getTenantId())) {
            LOGGER.error("分页查询租户任务执行日志, TenantId不能为空");
            throw new BusinessException(SassBizResultCodeEnum.ILLEGAL_PARAMETER,
                    "分页查询租户任务执行日志, TenantId不能为空");
        }
        if (null == page || null == page.getPageNumber() || null == page.getPageSize()) {
            LOGGER.error("分页查询租户任务执行日志,page不能为空");
            throw new BusinessException(SassBizResultCodeEnum.ILLEGAL_PARAMETER,
                    "分页查询租户任务执行日志,page不能为空");
        }

        //计算分页的起始偏移量
        Long index = (page.getPageNumber() - 1) * page.getPageSize().longValue();

        tenantDataSyncLogBO.setIndex(index);
        tenantDataSyncLogBO.setPageSize(page.getPageSize());

        Long count = tenantDataSyncLogDOMapper.selectRecordSCount(tenantDataSyncLogBO);

        List<TenantDataSyncLogDO> tenantDataSyncLogDOList = new ArrayList<>();

        //结果
        PageResultVO<TenantDataSyncLogDO> resultVO = new PageResultVO<>();
        resultVO.setPageNumber(page.getPageNumber());
        resultVO.setPageSize(page.getPageSize());
        resultVO.setTotal(count);

        if (count > 0) {
            tenantDataSyncLogDOList = tenantDataSyncLogDOMapper.selectRecordSPage(tenantDataSyncLogBO);
        }

        resultVO.setItems(tenantDataSyncLogDOList);

        return resultVO;
    }
}
