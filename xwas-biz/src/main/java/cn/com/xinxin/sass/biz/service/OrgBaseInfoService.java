package cn.com.xinxin.sass.biz.service;

import cn.com.xinxin.sass.repository.model.OrgBaseInfoDO;

/**
 * @author: liuhangzhou
 * @created: 2020/4/21.
 * @updater:
 * @description: 机构基础信息配置
 */
public interface OrgBaseInfoService {

    /**
     * 通过机构id查询
     * @param orgId 机构id
     * @return 机构基础信息
     */
    OrgBaseInfoDO selectByOrgId(String orgId);

}
