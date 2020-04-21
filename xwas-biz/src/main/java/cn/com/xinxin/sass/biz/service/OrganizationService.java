package cn.com.xinxin.sass.biz.service;


import cn.com.xinxin.sass.common.model.PageResultVO;
import cn.com.xinxin.sass.repository.model.OrganizationDO;

/**
 * Created by dengyunhui on 2018/5/1
 **/
public interface OrganizationService {

    OrganizationDO createOrganization(OrganizationDO organizationDO);

    OrganizationDO updateOrganization(OrganizationDO organizationDO);

    OrganizationDO findById(Long id);

    PageResultVO<OrganizationDO> findByCondition(PageResultVO page, OrganizationDO condition);

    Boolean deleteById(Long id);

}
