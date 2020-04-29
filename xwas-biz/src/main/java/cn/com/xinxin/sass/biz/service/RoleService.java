package cn.com.xinxin.sass.biz.service;


import cn.com.xinxin.sass.common.model.PageResultVO;
import cn.com.xinxin.sass.repository.model.RoleDO;

import java.util.List;

/**
 * Created by dengyunhui on 2018/5/1
 **/
public interface RoleService {

    RoleDO createRole(RoleDO roleDO, List<String> resourceList);

    boolean updateRole(RoleDO roleDO);

    boolean deleteRoles(List<String> roleCode);

    RoleDO findOne(long roleId);

    PageResultVO<RoleDO> findByConditionPage(PageResultVO page, RoleDO condition);

    /**
     * 根据角色编号得到角色标识符列表
     * @return
     */
    List<RoleDO> findRoles(List<Long> roleIds);

    /**
     * @param roleCodes
     * @return
     */
    List<RoleDO> queryByRoleCodes(List<String> roleCodes);

    RoleDO findByRoleCode(String code);

}
