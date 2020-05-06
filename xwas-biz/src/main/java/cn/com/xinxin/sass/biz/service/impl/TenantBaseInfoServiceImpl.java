package cn.com.xinxin.sass.biz.service.impl;

import cn.com.xinxin.sass.biz.service.TenantBaseInfoService;
import cn.com.xinxin.sass.common.enums.SassBizResultCodeEnum;
import cn.com.xinxin.sass.common.model.PageResultVO;
import cn.com.xinxin.sass.repository.dao.TenantBaseInfoDOMapper;
import cn.com.xinxin.sass.repository.model.RoleDO;
import cn.com.xinxin.sass.repository.model.TenantBaseInfoDO;
import com.github.pagehelper.PageHelper;
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
public class TenantBaseInfoServiceImpl implements TenantBaseInfoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TenantBaseInfoServiceImpl.class);

    private final TenantBaseInfoDOMapper tenantBaseInfoDOMapper;

    public TenantBaseInfoServiceImpl(final TenantBaseInfoDOMapper tenantBaseInfoDOMapper) {
        this.tenantBaseInfoDOMapper = tenantBaseInfoDOMapper;
    }

    /**
     * 通过机构id查询
     * @param orgId 机构id
     * @return 机构基础信息
     */
    @Override
    public TenantBaseInfoDO selectByOrgId(String orgId) {
        if (StringUtils.isBlank(orgId)) {
            LOGGER.error("通过机构id查询机构基础信息，orgId不能为空");
            throw new BusinessException(SassBizResultCodeEnum.ILLEGAL_PARAMETER,
                    "通过机构id查询机构基础信息，orgId不能为空");
        }

        TenantBaseInfoDO tenantBaseInfoDO = tenantBaseInfoDOMapper.selectByOrgId(orgId);

        if (null == tenantBaseInfoDO) {
            LOGGER.error("无法通过机构id[{}]找到机构信息", orgId);
            throw new BusinessException(SassBizResultCodeEnum.DATA_NOT_EXIST, "找不到机构信息");
        }

        return tenantBaseInfoDO;
    }

    @Override
    public boolean createOrgBaseInfo(TenantBaseInfoDO tenantBaseInfoDO) {
        return tenantBaseInfoDOMapper.insertSelective(tenantBaseInfoDO) == 1;
    }

    @Override
    public boolean updateByOrgId(TenantBaseInfoDO tenantBaseInfoDO) {
        return tenantBaseInfoDOMapper.updateByOrgIdSelective(tenantBaseInfoDO) == 1;
    }

    @Override
    public int deleteByCodes(List<String> codes) {
        return tenantBaseInfoDOMapper.deleteByCodes(codes);
    }


    @Override
    public PageResultVO<TenantBaseInfoDO> listAllTenants(PageResultVO page) {

        com.github.pagehelper.Page helper = PageHelper.startPage(page.getPageNumber(),page.getPageSize());
        List<TenantBaseInfoDO> tenantBaseInfoDOList = tenantBaseInfoDOMapper.listAlltenants();

        PageResultVO<TenantBaseInfoDO> result = new PageResultVO<>();
        result.setPageNumber(page.getPageNumber());
        result.setPageSize(page.getPageSize());
        result.setItems(tenantBaseInfoDOList);
        result.setTotal(helper.getTotal());
        return result;

    }
}
