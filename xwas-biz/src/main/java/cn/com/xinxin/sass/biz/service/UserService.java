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

import cn.com.xinxin.sass.biz.vo.QueryUserConditionVO;
import cn.com.xinxin.sass.common.model.PageResultVO;
import cn.com.xinxin.sass.repository.model.ResourceDO;
import cn.com.xinxin.sass.repository.model.RoleDO;
import cn.com.xinxin.sass.repository.model.UserDO;
import cn.com.xinxin.sass.repository.model.UserOrgDO;

import java.util.List;
import java.util.Map;

/**
 * Created by dengyunhui on 2018/4/23
 **/
public interface UserService {

    int createUser(UserDO userDO);

    /**
     * 重置用户密码
     * @param userAccount
     * @param newPassword
     * @param updater
     */
    void resetPassword(String userAccount,
                       String newPassword,
                       String updater);

    /**
     * 修改用户密码
     * @param account
     * @param originPassword
     * @param newPassword
     * @param updater
     */
    void modifyPassword(String account,
                        String originPassword,
                        String newPassword,
                        String updater);

    /**
     * 批量删除用户信息
     * @param accounts
     */
    void deleteUserByAccounts(List<String> accounts);

    List<UserDO> findUserByAccounts(List<String> accounts);

    UserDO findByUserAccount(String account);

    List<RoleDO> findRolesByAccount(String account);

    List<ResourceDO> findResourcesByAccount(String account);

    List<ResourceDO> findPermissionsByAccount(String account);

    List<ResourceDO> findRootMenusByAccount(String account);

    List<ResourceDO> findMenusByAccount(String userAccount);

    List<ResourceDO> findFunctionsByAccount(String account);

    PageResultVO<UserDO> findByConditionPage(PageResultVO page, QueryUserConditionVO queryUserConditionVO);

    boolean updateUser(UserDO userDO);

    Boolean delete(Long id);

    UserDO findById(Long id);

    UserDO getLoginUser(String sessionId);

    Boolean hasPermission(String sessionId,String url);

    void refreshSassUserInfo(String account);

    /**
     * 创建用户组织关系
     * @param userOrgDO
     * @return
     */
    int createUserOrgRelations(UserOrgDO userOrgDO);


    /**
     * 创建用户组织关系
     * @param userOrgDOList
     * @return
     */
    int createUserOrgRelationsByList(List<UserOrgDO> userOrgDOList);



    /**
     * 查询用户的组织关系
     * @param account
     * @return
     */
    List<UserOrgDO> queryUserOrgsByAccount(String account);


    /**
     * 删除用户与组织关系
     * @param account
     * @return
     */
    int removeUserOrgRelationByAccount(String account);

    /**
     * 删除用户与组织关系
     * @param orgCode
     * @return
     */
    int removeUserOrgRelationByOrgCode(String orgCode);


    /**
     * 删除
     * @param accounts
     * @return
     */
    int removeUserOrgRelationByAccountList(List<String> accounts);


    /**
     * 查询
     * @param accounts
     * @return
     */
    Map<String,  List<UserOrgDO>> queryUserOrgsMapsByAccounts(List<String> accounts);


    List<UserOrgDO> queryUserOrgsByOrgCode(String orgCode);


    List<UserOrgDO> queryUserOrgsByOrgCodeLists(List<String> orgCodes);

}
