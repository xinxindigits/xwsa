package cn.com.xinxin.sass.biz.service.impl;

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

import cn.com.xinxin.sass.biz.service.UserRoleService;
import cn.com.xinxin.sass.common.model.PageResultVO;
import cn.com.xinxin.sass.repository.dao.UserRoleMapper;
import cn.com.xinxin.sass.repository.model.RoleDO;
import cn.com.xinxin.sass.repository.model.UserRoleDO;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by dengyunhui on 2018/5/1
 **/
@Service
public class UserRoleServiceImpl implements UserRoleService {

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Override
    public UserRoleDO createUserRole(UserRoleDO userRoleDO) {
        userRoleMapper.insertSelective(userRoleDO);
        return userRoleDO;
    }

    @Override
    public boolean createUserRoles(List<UserRoleDO> userRoles) {
        userRoleMapper.batchInsert(userRoles);
        return true;
    }

    @Override
    public boolean updateUserRole(UserRoleDO userRoleDO) {
        int n = userRoleMapper.updateByPrimaryKeySelective(userRoleDO);
        return n == 1;
    }

    @Override
    public List<RoleDO> findRoleByUserAccount(String account) {
        List<RoleDO> roleDOList = userRoleMapper.findRoleByUserAccount(account);
        return roleDOList;
    }

    @Override
    public boolean deleteUserRole(Long id) {
        int n = userRoleMapper.deleteByPrimaryKey(id);
        return true;
    }

    @Override
    public boolean deleteByRoleCode(String roleCode) {
        userRoleMapper.deleteByRoleCode(roleCode);
        return true;
    }

    @Override
    public boolean deleteByAccounts(List<String> accounts) {

        userRoleMapper.deleteByAccounts(accounts);
        return true;
    }

    @Override
    public PageResultVO<UserRoleDO> findByConditionPage(PageResultVO page, UserRoleDO condition) {
       com.github.pagehelper.Page page1 = PageHelper.startPage(page.getPageNumber(),page.getPageSize());
       List<UserRoleDO> userRoleDOS = userRoleMapper.findByCondition(condition);

        PageResultVO<UserRoleDO> result = new PageResultVO<UserRoleDO>();
       result.setPageNumber(page.getPageNumber());
       result.setItems(userRoleDOS);
       result.setPageSize(page.getPageSize());
       result.setTotal(page1.getTotal());

       return result;
    }

    @Override
    public UserRoleDO findById(Long id) {
        return userRoleMapper.selectByPrimaryKey(id);
    }

    @Override
    public boolean saveOrUpdate(UserRoleDO userRoleDO) {
        return userRoleMapper.saveOrUpdate(userRoleDO) == 1;
    }

    @Override
    public int updateByUserAccount(UserRoleDO userRoleDO) {
        return userRoleMapper.updateByUserAccount(userRoleDO);
    }

    @Override
    public int updateByRoleCode(UserRoleDO userRoleDO) {
        return userRoleMapper.updateByRoleCode(userRoleDO);
    }

    public UserRoleMapper getUserRoleMapper() {
        return userRoleMapper;
    }

    public void setUserRoleMapper(UserRoleMapper userRoleMapper) {
        this.userRoleMapper = userRoleMapper;
    }

    @Override
    public int countByRoleCodes(List<String> roleCodes) {
        return userRoleMapper.countByRoleCode(roleCodes);
    }
    @Override
    public boolean deleteByRoleCodes(List<String> roleCodes){
        return userRoleMapper.deleteByRoleCodes(roleCodes);
    }

    @Override
    public List<UserRoleDO> findByRoleCode(String roleCode) {
        UserRoleDO userRoleDO = new UserRoleDO();
        userRoleDO.setRoleCode(roleCode);
        List<UserRoleDO> userRoleDOS = userRoleMapper.findByCondition(userRoleDO);
        return userRoleDOS;
    }

    @Override
    public boolean deleteByAccountsAndRoleCode(String roleCode, List<String> accounts) {
        return userRoleMapper.deleteByAccountsAndRoleCode(roleCode,accounts);
    }
}
