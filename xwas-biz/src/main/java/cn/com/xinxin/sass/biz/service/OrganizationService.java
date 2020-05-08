package cn.com.xinxin.sass.biz.service;


import cn.com.xinxin.sass.common.model.PageResultVO;
import cn.com.xinxin.sass.repository.model.OrganizationDO;

import java.util.Date;
import java.util.List;

/**
 * Created by dengyunhui on 2018/5/1
 **/
public interface OrganizationService {

    int createOrganization(OrganizationDO organizationDO);

    int updateOrganization(OrganizationDO organizationDO);

    OrganizationDO findById(Long id);

    PageResultVO<OrganizationDO> findByCondition(PageResultVO page, OrganizationDO condition);

    Boolean deleteById(Long id);

    int deleteByCodes(List<String> codes);

    List<OrganizationDO> queryOrgList();

    OrganizationDO findByCode(String code);

    List<OrganizationDO> findChildren(List<Long> parentIds);

}
