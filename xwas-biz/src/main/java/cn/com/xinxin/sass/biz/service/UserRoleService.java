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
import cn.com.xinxin.sass.repository.model.RoleDO;
import cn.com.xinxin.sass.repository.model.UserRoleDO;

import java.util.List;

/**
 * Created by dengyunhui on 2018/5/1
 **/
public interface UserRoleService {

    UserRoleDO createUserRole(UserRoleDO userRoleDO);

    boolean createUserRoles(List<UserRoleDO> userRoles);

    boolean updateUserRole(UserRoleDO userRoleDO);

    List<RoleDO> findRoleByUserAccount(String account);

    boolean deleteUserRole(Long id);

    boolean deleteByRoleCode(String roleCode);

    /**
     * @param accounts
     * @return
     */
    boolean deleteByAccounts(List<String> accounts);

    PageResultVO<UserRoleDO> findByConditionPage(PageResultVO page, UserRoleDO condition);

    UserRoleDO findById(Long id);

    boolean saveOrUpdate(UserRoleDO userRoleDO);

    int updateByUserAccount(UserRoleDO userRoleDO);

    int updateByRoleCode(UserRoleDO userRoleDO);

    int countByRoleCodes(List<String> roleCode);

    List<UserRoleDO> findByRoleCode(String roleCode);

    boolean deleteByRoleCodes(List<String> roleCodes);

    boolean deleteByAccountsAndRoleCode(String roleCode, List<String> accounts);
}
