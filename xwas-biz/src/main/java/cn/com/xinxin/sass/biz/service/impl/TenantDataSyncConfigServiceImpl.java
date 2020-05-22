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

import cn.com.xinxin.sass.biz.service.TenantDataSyncConfigService;
import cn.com.xinxin.sass.common.constants.CommonConstants;
import cn.com.xinxin.sass.common.enums.SassBizResultCodeEnum;
import cn.com.xinxin.sass.common.utils.DateUtils;
import cn.com.xinxin.sass.repository.dao.TenantDataSyncConfigDOMapper;
import cn.com.xinxin.sass.repository.model.TenantDataSyncConfigDO;
import com.xinxinfinance.commons.exception.BusinessException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;


/**
 * @author: liuhangzhou
 * @created: 2020/4/27.
 * @updater:
 * @description: 机构同步配置信息DB服务
 */
@Service
public class TenantDataSyncConfigServiceImpl implements TenantDataSyncConfigService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TenantDataSyncConfigServiceImpl.class);

    private final TenantDataSyncConfigDOMapper tenantDataSyncConfigDOMapper;

    public TenantDataSyncConfigServiceImpl(final TenantDataSyncConfigDOMapper tenantDataSyncConfigDOMapper) {
        this.tenantDataSyncConfigDOMapper = tenantDataSyncConfigDOMapper;
    }

    /**
     * 通过机构id和任务类型查询记录
     * @param tenantId 机构id
     * @param taskType 任务类型
     * @return 机构同步配置信息
     */
    @Override
    public TenantDataSyncConfigDO selectByOrgIdAndTaskType(String tenantId, String taskType) {
        if (StringUtils.isBlank(tenantId)) {
            LOGGER.error("通过机构id和任务类型查询记录, tenantId不能为空");
            throw new BusinessException(SassBizResultCodeEnum.ILLEGAL_PARAMETER,
                    "通过机构id和任务类型查询记录, tenantId不能为空");
        }
        if (StringUtils.isBlank(taskType)) {
            LOGGER.error("通过机构id和任务类型查询记录, taskType不能为空");
            throw new BusinessException(SassBizResultCodeEnum.ILLEGAL_PARAMETER,
                    "通过机构id和任务类型查询记录, taskType不能为空");
        }
        TenantDataSyncConfigDO tenantDataSyncConfigDO = tenantDataSyncConfigDOMapper.selectByOrgIdAndTaskType(tenantId, taskType);

        return tenantDataSyncConfigDO;
    }

    /**
     * 根据id更新记录
     * @param tenantDataSyncConfigDO 记录
     * @return 更新成功的条数
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public int updateById(TenantDataSyncConfigDO tenantDataSyncConfigDO) {
        if (null == tenantDataSyncConfigDO) {
            LOGGER.error("根据id更新记录记录, orgDataSyncConfigDO不能为空");
            throw new BusinessException(SassBizResultCodeEnum.ILLEGAL_PARAMETER,
                    "根据id更新记录记录, orgDataSyncConfigDO不能为空");
        }
        if (null == tenantDataSyncConfigDO.getId()) {
            LOGGER.error("根据id更新记录记录, id不能为空");
            throw new BusinessException(SassBizResultCodeEnum.ILLEGAL_PARAMETER,
                    "根据id更新记录记录, id不能为空");
        }
        return tenantDataSyncConfigDOMapper.updateByPrimaryKeySelective(tenantDataSyncConfigDO);
    }

    /**
     * 任务上锁
     * @param tenantId 租户id
     * @param taskType 任务类型
     * @return 成功更新记录条数
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public int updateLockByTenantIdAndTaskType(String tenantId, String taskType) {
        if (StringUtils.isBlank(tenantId)) {
            LOGGER.error("通过机构id和任务类型对任务上锁, tenantId不能为空");
            throw new BusinessException(SassBizResultCodeEnum.ILLEGAL_PARAMETER,
                    "通过机构id和任务类型对任务上锁, tenantId不能为空");
        }
        if (StringUtils.isBlank(taskType)) {
            LOGGER.error("通过机构id和任务类型对任务上锁, taskType不能为空");
            throw new BusinessException(SassBizResultCodeEnum.ILLEGAL_PARAMETER,
                    "通过机构id和任务类型对任务上锁, taskType不能为空");
        }

        int result = tenantDataSyncConfigDOMapper.updateLockByTenantIdAndTaskType(tenantId, taskType);

        if (result < 1) {
            TenantDataSyncConfigDO configDO = tenantDataSyncConfigDOMapper.selectByOrgIdAndTaskType(tenantId, taskType);
            if (null != configDO && DateUtils.diffHour(configDO.getLockTime(), new Date()) > CommonConstants.THREE) {
                return 1;
            }
            LOGGER.error("当前有任务在执行，无法再次执行，请确认，机构id[{}], 项目编码[{}]", tenantId, taskType);
            throw new BusinessException(SassBizResultCodeEnum.FAIL,
                    "当前有任务在执行，无法再次执行，请确认");
        }

        return result;
    }

    /**
     * 任务解锁
     * @param tenantId 租户id
     * @param taskType 任务类型
     * @return 成功更新记录条数
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public int updateUnLockByTenantIdAndTaskType(String tenantId, String taskType) {
        if (StringUtils.isBlank(tenantId)) {
            LOGGER.error("通过机构id和任务类型对任务解锁, tenantId不能为空");
            throw new BusinessException(SassBizResultCodeEnum.ILLEGAL_PARAMETER,
                    "通过机构id和任务类型对任务解锁, tenantId不能为空");
        }
        if (StringUtils.isBlank(taskType)) {
            LOGGER.error("通过机构id和任务类型对任务解锁, taskType不能为空");
            throw new BusinessException(SassBizResultCodeEnum.ILLEGAL_PARAMETER,
                    "通过机构id和任务类型对任务解锁, taskType不能为空");
        }

        int result = tenantDataSyncConfigDOMapper.updateUnLockByTenantIdAndTaskType(tenantId, taskType);

        if (result < 1) {
            LOGGER.error("任务解锁失败，机构id[{}], 项目编码[{}]", tenantId, taskType);
            throw new BusinessException(SassBizResultCodeEnum.FAIL,
                    "任务解锁失败，请检查");
        }

        return result;
    }

    @Override
    public List<TenantDataSyncConfigDO> selectByTenantId(String tenantId) {
        if (StringUtils.isBlank(tenantId)) {
            LOGGER.error("通过机构id和任务类型查询记录, tenantId不能为空");
            throw new BusinessException(SassBizResultCodeEnum.ILLEGAL_PARAMETER,
                    "通过机构id和任务类型查询记录, tenantId不能为空");
        }
        return tenantDataSyncConfigDOMapper.selectByTenantId(tenantId);
    }

    @Override
    public List<TenantDataSyncConfigDO> queryValidRecord() {
        return tenantDataSyncConfigDOMapper.queryValidRecord();
    }

    /**
     * 插入配置
     * @param tenantDataSyncConfigDO 配置
     * @return 成功插入条数
     */
    @Override
    public int insert(TenantDataSyncConfigDO tenantDataSyncConfigDO) {
        if (null == tenantDataSyncConfigDO) {
            LOGGER.error("插入配置, tenantDataSyncConfigDO不能为空");
            throw new BusinessException(SassBizResultCodeEnum.ILLEGAL_PARAMETER,
                    "插入配置, tenantDataSyncConfigDO不能为空");
        }
        if (StringUtils.isBlank(tenantDataSyncConfigDO.getTenantId())) {
            LOGGER.error("插入配置, TenantId不能为空");
            throw new BusinessException(SassBizResultCodeEnum.ILLEGAL_PARAMETER,
                    "插入配置, TenantId不能为空");
        }
        if (StringUtils.isBlank(tenantDataSyncConfigDO.getTaskType())) {
            LOGGER.error("插入配置, TaskType不能为空");
            throw new BusinessException(SassBizResultCodeEnum.ILLEGAL_PARAMETER,
                    "插入配置, TaskType不能为空");
        }
        if (StringUtils.isBlank(tenantDataSyncConfigDO.getCronExpression())) {
            LOGGER.error("插入配置, CronExpression不能为空");
            throw new BusinessException(SassBizResultCodeEnum.ILLEGAL_PARAMETER,
                    "插入配置, CronExpression不能为空");
        }

        return tenantDataSyncConfigDOMapper.insertSelective(tenantDataSyncConfigDO);
    }

    /**
     * 通过id查询记录
     * @param id 数据库主键
     * @return 记录
     */
    @Override
    public TenantDataSyncConfigDO selectById(Long id) {
        if (null == id) {
            LOGGER.error("通过id查询记录, id不能为空");
            throw new BusinessException(SassBizResultCodeEnum.ILLEGAL_PARAMETER,
                    "通过id查询记录, id不能为空");
        }
        return tenantDataSyncConfigDOMapper.selectByPrimaryKey(id);
    }
}
