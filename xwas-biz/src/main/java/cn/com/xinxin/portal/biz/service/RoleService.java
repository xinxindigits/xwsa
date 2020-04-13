package cn.com.xinxin.portal.biz.service;

import cn.com.xinxin.portal.common.Page;
import cn.com.xinxin.portal.repository.model.RoleDO;

import java.util.List;

/**
 * Created by dengyunhui on 2018/5/1
 **/
public interface RoleService {

    RoleDO createRole(RoleDO roleDO);

    int updateRole(RoleDO roleDO);

    int deleteRole(Long roleId);

    RoleDO findOne(long roleId);

    Page<RoleDO> findByConditionPage(Page page,RoleDO condition);

    /**
     * 根据角色编号得到角色标识符列表
     * @return
     */
    List<RoleDO> findRoles(List<Long> roleIds);

    RoleDO findByRoleCode(String code);

}
