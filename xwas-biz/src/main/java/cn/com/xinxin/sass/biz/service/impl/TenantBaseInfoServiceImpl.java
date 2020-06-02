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
     * @param tenantId 机构id
     * @return 机构基础信息
     */
    @Override
    public TenantBaseInfoDO selectByTenantId(String tenantId) {
        if (StringUtils.isBlank(tenantId)) {
            LOGGER.error("通过机构id查询机构基础信息，orgId不能为空");
            throw new BusinessException(SassBizResultCodeEnum.ILLEGAL_PARAMETER,
                    "通过机构id查询机构基础信息，orgId不能为空");
        }

        TenantBaseInfoDO tenantBaseInfoDO = tenantBaseInfoDOMapper.selectByTenantId(tenantId);

        if (null == tenantBaseInfoDO) {
            LOGGER.error("无法通过机构id[{}]找到机构信息", tenantId);
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
    public PageResultVO<TenantBaseInfoDO> findByCondition(PageResultVO page, TenantBaseInfoDO tenantBaseInfoDO) {

        com.github.pagehelper.Page helper = PageHelper.startPage(page.getPageNumber(),page.getPageSize());
        List<TenantBaseInfoDO> tenantBaseInfoDOList = tenantBaseInfoDOMapper.findByCondition(tenantBaseInfoDO);

        PageResultVO<TenantBaseInfoDO> result = new PageResultVO<>();
        result.setPageNumber(page.getPageNumber());
        result.setPageSize(page.getPageSize());
        result.setItems(tenantBaseInfoDOList);
        result.setTotal(helper.getTotal());
        return result;

    }


    @Override
    public List<TenantBaseInfoDO> queryAllTenants() {

        List<TenantBaseInfoDO> tenantBaseInfoDOList = this.tenantBaseInfoDOMapper.listAlltenants();

        return tenantBaseInfoDOList;
    }
}
