package cn.com.xinxin.sass.biz.service.impl;

import cn.com.xinxin.sass.biz.service.TenantDataSyncConfigService;
import cn.com.xinxin.sass.common.enums.SassBizResultCodeEnum;
import cn.com.xinxin.sass.repository.dao.TenantDataSyncConfigDOMapper;
import cn.com.xinxin.sass.repository.model.TenantDataSyncConfigDO;
import com.xinxinfinance.commons.exception.BusinessException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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

        if (null == tenantDataSyncConfigDO) {
            LOGGER.error("无法通过机构id[{}]找到机构同步[{}]任务配置信息", tenantId, taskType);
            throw new BusinessException(SassBizResultCodeEnum.DATA_NOT_EXIST, "找不到机构同步任务配置信息");
        }

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
    public List<TenantDataSyncConfigDO> queryValidRecord() {
        return tenantDataSyncConfigDOMapper.queryValidRecord();
    }
}
