package cn.com.xinxin.sass.biz.service;

import cn.com.xinxin.sass.common.model.PageResultVO;
import cn.com.xinxin.sass.repository.model.TenantBaseInfoDO;

import java.util.List;

/**
 * @author: liuhangzhou
 * @created: 2020/4/21.
 * @updater:
 * @description: 机构基础信息配置
 */
public interface TenantBaseInfoService {

    /**
     * 通过机构id查询
     * @param tenantId 机构id
     * @return 机构基础信息
     */
    TenantBaseInfoDO selectByTenantId(String tenantId);

    boolean createOrgBaseInfo(TenantBaseInfoDO tenantBaseInfoDO);

    boolean updateByOrgId(TenantBaseInfoDO tenantBaseInfoDO);

    int deleteByCodes(List<String> codes);

    /**
     *
     * @return
     */
    PageResultVO<TenantBaseInfoDO> findByCondition(PageResultVO page, TenantBaseInfoDO condition);

}
