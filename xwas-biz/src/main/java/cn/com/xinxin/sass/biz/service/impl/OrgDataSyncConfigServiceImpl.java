package cn.com.xinxin.sass.biz.service.impl;

import cn.com.xinxin.sass.biz.service.OrgDataSyncConfigService;
import cn.com.xinxin.sass.common.enums.SassBizResultCodeEnum;
import cn.com.xinxin.sass.repository.dao.OrgDataSyncConfigDOMapper;
import cn.com.xinxin.sass.repository.model.OrgDataSyncConfigDO;
import com.xinxinfinance.commons.exception.BusinessException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


/**
 * @author: liuhangzhou
 * @created: 2020/4/27.
 * @updater:
 * @description: 机构同步配置信息DB服务
 */
@Service
public class OrgDataSyncConfigServiceImpl implements OrgDataSyncConfigService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrgDataSyncConfigServiceImpl.class);

    private final OrgDataSyncConfigDOMapper orgDataSyncConfigDOMapper;

    public OrgDataSyncConfigServiceImpl(final OrgDataSyncConfigDOMapper orgDataSyncConfigDOMapper) {
        this.orgDataSyncConfigDOMapper = orgDataSyncConfigDOMapper;
    }

    /**
     * 通过机构id和任务类型查询记录
     * @param orgId 机构id
     * @param taskType 任务类型
     * @return 机构同步配置信息
     */
    @Override
    public OrgDataSyncConfigDO selectByOrgIdAndTaskType(String orgId, String taskType) {
        if (StringUtils.isBlank(orgId)) {
            LOGGER.error("通过机构id和任务类型查询记录, orgId不能为空");
            throw new BusinessException(SassBizResultCodeEnum.ILLEGAL_PARAMETER,
                    "通过机构id和任务类型查询记录, orgId不能为空");
        }
        if (StringUtils.isBlank(taskType)) {
            LOGGER.error("通过机构id和任务类型查询记录, taskType不能为空");
            throw new BusinessException(SassBizResultCodeEnum.ILLEGAL_PARAMETER,
                    "通过机构id和任务类型查询记录, taskType不能为空");
        }
        OrgDataSyncConfigDO orgDataSyncConfigDO = orgDataSyncConfigDOMapper.selectByOrgIdAndTaskType(orgId, taskType);

        if (null == orgDataSyncConfigDO) {
            LOGGER.error("无法通过机构id[{}]找到机构同步[{}]任务配置信息", orgId, taskType);
            throw new BusinessException(SassBizResultCodeEnum.DATA_NOT_EXIST, "找不到机构同步任务配置信息");
        }

        return orgDataSyncConfigDO;
    }

    /**
     * 根据id更新记录
     * @param orgDataSyncConfigDO 记录
     * @return 更新成功的条数
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public int updateById(OrgDataSyncConfigDO orgDataSyncConfigDO) {
        if (null == orgDataSyncConfigDO) {
            LOGGER.error("根据id更新记录记录, orgDataSyncConfigDO不能为空");
            throw new BusinessException(SassBizResultCodeEnum.ILLEGAL_PARAMETER,
                    "根据id更新记录记录, orgDataSyncConfigDO不能为空");
        }
        if (null == orgDataSyncConfigDO.getId()) {
            LOGGER.error("根据id更新记录记录, id不能为空");
            throw new BusinessException(SassBizResultCodeEnum.ILLEGAL_PARAMETER,
                    "根据id更新记录记录, id不能为空");
        }
        return orgDataSyncConfigDOMapper.updateByPrimaryKeySelective(orgDataSyncConfigDO);
    }
}
