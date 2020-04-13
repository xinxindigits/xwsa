package cn.com.xinxin.portal.biz.service;

import cn.com.xinxin.portal.common.Page;
import cn.com.xinxin.portal.repository.model.ResourceDO;
import cn.com.xinxin.portal.repository.model.RoleResourceDO;

import java.util.List;

/**
 * Created by dengyunhui on 2018/5/1
 **/
public interface RoleResourceService {

    RoleResourceDO createRoleResource(RoleResourceDO roleResourceDO);

    boolean createRoleResources(List<RoleResourceDO> roleResources);

   boolean updateRoleResource(RoleResourceDO roleResourceDO);

    boolean deleteRoleResource(long id);

    List<ResourceDO> findResources(String roleCode);

    List<ResourceDO> findResources(List<String> roleCodes);

    Page<RoleResourceDO> findByConditionPage(Page page,RoleResourceDO condition);

    RoleResourceDO findById(Long id);

    Boolean delete(String roleCode,List<String> resourceCodes);

}
