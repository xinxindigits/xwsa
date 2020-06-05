package cn.com.xinxin.sass.biz.service;

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

import cn.com.xinxin.sass.common.model.PageResultVO;
import cn.com.xinxin.sass.repository.model.ResourceDO;
import cn.com.xinxin.sass.repository.model.RoleDO;
import cn.com.xinxin.sass.repository.model.RoleResourceDO;

import java.util.List;

/**
 * Created by dengyunhui on 2018/5/1
 **/
public interface RoleResourceService {

    RoleResourceDO createRoleResource(RoleResourceDO roleResourceDO);

    boolean createRoleResources(List<RoleResourceDO> roleResources);

    boolean createRoleResources(RoleDO role, List<ResourceDO> resources);

    boolean updateRoleResource(RoleResourceDO roleResourceDO);

    boolean deleteRoleResource(long id);

    List<ResourceDO> findResourcesByRoleCode(String roleCode);

    List<ResourceDO> findResourcesByRoleCode(List<String> roleCodes);

    PageResultVO<RoleResourceDO> findByConditionPage(PageResultVO page, RoleResourceDO condition);

    RoleResourceDO findById(Long id);

    Boolean delete(String roleCode,List<String> resourceCodes);


    List<RoleResourceDO> queryRolesAndResourcesByRSCode(String rsCode);


    List<RoleResourceDO> queryRolesAndResourcesByRSCodeList(List<String> rsCodes);

    boolean updateByRoleCode(RoleResourceDO roleResourceDO);

    boolean updateByResourceCode(RoleResourceDO roleResourceDO);

    boolean deleteByRoleCodes(List<String> roleCode);
}
