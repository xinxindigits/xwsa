package cn.com.xinxin.sass.biz.service.impl;

import cn.com.xinxin.sass.biz.service.OrgBaseInfoService;
import cn.com.xinxin.sass.common.enums.SassBizResultCodeEnum;
import cn.com.xinxin.sass.repository.dao.OrgBaseInfoDOMapper;
import cn.com.xinxin.sass.repository.model.OrgBaseInfoDO;
import com.xinxinfinance.commons.exception.BusinessException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: liuhangzhou
 * @created: 2020/4/21.
 * @updater:
 * @description: 机构基础信息配置
 */
@Service
public class OrgBaseInfoServiceImpl implements OrgBaseInfoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrgBaseInfoServiceImpl.class);

    private final OrgBaseInfoDOMapper orgBaseInfoDOMapper;

    public OrgBaseInfoServiceImpl(final OrgBaseInfoDOMapper orgBaseInfoDOMapper) {
        this.orgBaseInfoDOMapper = orgBaseInfoDOMapper;
    }

    /**
     * 通过机构id查询
     * @param orgId 机构id
     * @return 机构基础信息
     */
    @Override
    public OrgBaseInfoDO selectByOrgId(String orgId) {
        if (StringUtils.isBlank(orgId)) {
            LOGGER.error("通过机构id查询机构基础信息，orgId不能为空");
            throw new BusinessException(SassBizResultCodeEnum.ILLEGAL_PARAMETER,
                    "通过机构id查询机构基础信息，orgId不能为空");
        }

        OrgBaseInfoDO orgBaseInfoDO = orgBaseInfoDOMapper.selectByOrgId(orgId);

        if (null == orgBaseInfoDO) {
            LOGGER.error("无法通过机构id[{}]找到机构信息", orgId);
            throw new BusinessException(SassBizResultCodeEnum.DATA_NOT_EXIST, "找不到机构信息");
        }

        return orgBaseInfoDO;
    }

    @Override
    public boolean createOrgBaseInfo(OrgBaseInfoDO orgBaseInfoDO) {
        return orgBaseInfoDOMapper.insertSelective(orgBaseInfoDO) == 1;
    }

    @Override
    public boolean updateByOrgId(OrgBaseInfoDO orgBaseInfoDO) {
        return orgBaseInfoDOMapper.updateByOrgIdSelective(orgBaseInfoDO) == 1;
    }

    @Override
    public int deleteByCodes(List<String> codes) {
        return orgBaseInfoDOMapper.deleteByCodes(codes);
    }
}
