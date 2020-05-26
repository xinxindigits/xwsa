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

import cn.com.xinxin.sass.auth.model.SassUserInfo;
import cn.com.xinxin.sass.biz.service.RoleResourceService;
import cn.com.xinxin.sass.biz.service.UserRoleService;
import cn.com.xinxin.sass.biz.service.UserService;
import cn.com.xinxin.sass.biz.util.PasswordUtils;
import cn.com.xinxin.sass.biz.vo.QueryUserConditionVO;
import cn.com.xinxin.sass.biz.vo.UserPwdVO;

import cn.com.xinxin.sass.common.enums.ResourceTypeEnums;
import cn.com.xinxin.sass.common.enums.SassBizResultCodeEnum;
import cn.com.xinxin.sass.common.model.PageResultVO;
import cn.com.xinxin.sass.repository.dao.UserDOMapper;
import cn.com.xinxin.sass.repository.dao.UserOrgDOMapper;
import cn.com.xinxin.sass.repository.model.*;
import cn.com.xinxin.sass.auth.repository.UserAclTokenRepository;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.sun.prism.impl.BaseContext;
import com.xinxinfinance.commons.exception.BusinessException;
import com.xinxinfinance.commons.util.BaseConvert;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.shiro.crypto.hash.Hash;
import org.eclipse.jetty.server.Authentication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.management.relation.Role;
import java.util.*;

import java.util.stream.Collectors;

import static cn.com.xinxin.sass.common.CommonUtils.distinctByKey;

/**
 * Created by dengyunhui on 2018/4/24
 **/
@Service
public class UserServiceImpl implements UserService {

    private final static Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserDOMapper userDOMapper;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private RoleResourceService roleResourceService;


    @Autowired
    private UserAclTokenRepository userAclTokenRepository;

    @Autowired
    private UserOrgDOMapper userOrgDOMapper;



    @Override
    public int createUser(UserDO userDO) {
        UserPwdVO userPwdVO = PasswordUtils.encryptPassword(userDO.getAccount(), userDO.getPassword());
        userDO.setSalt(userPwdVO.getSalt());
        userDO.setPassword(userPwdVO.getPassword());
        userDO.setStatus((byte)0);
        userDO.setExpireDate(DateUtils.addMonths(new Date(),3));
        return userDOMapper.insertSelective(userDO);
    }

    @Override
    public void resetPassword(String account,
                              String newPassword,
                              String updater) {
        UserDO userDO = userDOMapper.selectByAccount(account);
        if (userDO != null){
            userDO.setPassword(newPassword);
            UserPwdVO userPwdVO = PasswordUtils.encryptPassword(userDO.getAccount(),userDO.getPassword());

            UserDO update = new UserDO();
            update.setId(userDO.getId());
            update.setSalt(userPwdVO.getSalt());
            update.setPassword(userPwdVO.getPassword());
            update.setGmtUpdater(updater);
            userDOMapper.updateByPrimaryKeySelective(update);
        }

    }

    @Override
    public void modifyPassword(String account,
                               String originPassword,
                               String newPassword,
                               String updater) {
        UserDO userDO = userDOMapper.selectByAccount(account);

        if (userDO == null){
            throw new BusinessException(SassBizResultCodeEnum.DATA_NOT_EXIST,"根据用户id找不到对应的用户");
        }

        String originEncryptedPassword = userDO.getPassword();
        String inputOriginPassword = PasswordUtils.encryptPassword(userDO.getAccount(), userDO.getSalt(), originPassword);
        if (!Objects.equals(originEncryptedPassword,inputOriginPassword)){
            throw new BusinessException(SassBizResultCodeEnum.ILLEGAL_PARAMETER,"旧密码错误,修改密码失败");
        }

        UserPwdVO userPwdVO = PasswordUtils.encryptPassword(userDO.getAccount(),newPassword);

        UserDO update = new UserDO();
        update.setId(userDO.getId());
        update.setSalt(userPwdVO.getSalt());
        update.setPassword(userPwdVO.getPassword());
        update.setGmtUpdater(updater);
        int result =  userDOMapper.updateByPrimaryKeySelective(update);

    }

    @Override
    public void deleteUserByAccounts(List<String> accounts) {

        // 删除用户信息
        this.userDOMapper.deleteByAccounts(accounts);
        // 删除角色权限相关的信息
        this.userRoleService.deleteByAccounts(accounts);

    }

    @Override
    public List<UserDO> findUserByAccounts(List<String> accounts) {
        return userDOMapper.selectByAccounts(accounts);
    }

    @Override
    public UserDO findByUserAccountAndTenantId(String account, String tenantId) {
        return userDOMapper.selectByAccountAndTenentId(account,tenantId);
    }


    @Override
    public UserDO findByUserAccount(String account) {
        return userDOMapper.selectByAccount(account);
    }

    @Override
    public List<RoleDO> findRolesByAccount(String account) {

        UserDO userDO = findByUserAccount(account);

        if (userDO != null){
            return userRoleService.findRoleByUserAccount(userDO.getAccount());
        }

        return null;
    }

    @Override
    public List<ResourceDO> findResourcesByAccount(String account) {

        List<RoleDO> roleDOS = userRoleService.findRoleByUserAccount(account);


        if (!CollectionUtils.isEmpty(roleDOS)){

            List<String> roleCodes = roleDOS.stream().map(RoleDO::getCode).collect(Collectors.toList());

            List<ResourceDO>  resourceDOList = roleResourceService.findResourcesByRoleCode(roleCodes);

            List<ResourceDO> result = resourceDOList.stream()
                    .filter(distinctByKey(x->x.getCode())).collect(Collectors.toList());
            return result;
        }

        return null;
    }


    @Override
    public List<ResourceDO> findPermissionsByAccount(String account) {
        List<ResourceDO> resourceDOS = findResourcesByAccount(account);

        if (!CollectionUtils.isEmpty(resourceDOS)){
            return resourceDOS
                    .stream()
                    .filter(resourceDO -> resourceDO.getResourceType().equals(ResourceTypeEnums.FUNCTION_TYPE.getCode()))
                    .collect(Collectors.toList());
        }

        return null;
    }

    @Override
    public List<ResourceDO> findRootMenusByAccount(String userNo) {
        List<ResourceDO> resourceDOS = findResourcesByAccount(userNo);

        if (!CollectionUtils.isEmpty(resourceDOS)){
            return resourceDOS.stream().filter(ResourceDO::getRoot).collect(Collectors.toList());
        }

        return null;
    }

    @Override
    public List<ResourceDO> findMenusByAccount(String account) {

        List<ResourceDO> resourceDOS = findResourcesByAccount(account);
        if (CollectionUtils.isEmpty(resourceDOS)){
           return null;
        }

        List<ResourceDO> resourceDOList = resourceDOS.stream().distinct()
                    .filter(resourceDO -> resourceDO.getResourceType().equals(ResourceTypeEnums.MENU_TYPE.getCode()))
                    .collect(Collectors.toList());
        return resourceDOList;

    }

    @Override
    public List<ResourceDO> findFunctionsByAccount(String account) {
        List<ResourceDO> resourceDOS = findResourcesByAccount(account);
        if (CollectionUtils.isEmpty(resourceDOS)){
            return null;
        }

        List<ResourceDO> resourceDOList = resourceDOS.stream().distinct()
                .filter(resourceDO -> resourceDO.getResourceType().equals(ResourceTypeEnums.FUNCTION_TYPE.getCode()))
                .collect(Collectors.toList());
        return resourceDOList;
    }

    @Override
    public PageResultVO<UserDO> findByConditionPageAndTenantId(PageResultVO page,
                                                               QueryUserConditionVO queryUserConditionVO,
                                                               String tenantId) {
        LOGGER.info("QueryUserConditionVO:{}",JSONObject.toJSONString(queryUserConditionVO));
        com.github.pagehelper.Page doPage = PageHelper.startPage(page.getPageNumber(),page.getPageSize());

        UserDO userDO = BaseConvert.convert(queryUserConditionVO, UserDO.class);
        if(queryUserConditionVO.getGender() != null){
            userDO.setGender(queryUserConditionVO.getGender().byteValue());
        }
        if(queryUserConditionVO.getStatus() != null){
            userDO.setStatus(queryUserConditionVO.getStatus().byteValue());
        }
        List<UserDO> userDOS = userDOMapper.findByConditionAndTenantId(userDO,
                queryUserConditionVO.getStartTime(),
                queryUserConditionVO.getEndTime(),
                tenantId);

        PageResultVO<UserDO> result = new PageResultVO<>();
        result.setPageNumber(page.getPageNumber());
        result.setPageSize(page.getPageSize());
        result.setTotal(doPage.getTotal());
        result.setItems(userDOS);
        return result;
    }

    @Override
    public boolean updateUser(UserDO userDO) {
        int n = userDOMapper.updateByPrimaryKeySelective(userDO);
        return n == 1;
    }

    @Override
    public Boolean delete(Long id) {
        int deleteResult = userDOMapper.deleteByPrimaryKey(id);
        return deleteResult>0?true:false;
    }

    @Override
    public UserDO findById(Long id) {
        return userDOMapper.selectByPrimaryKey(id);
    }

    @Override
    public UserDO getLoginUser(String sessionId) {

        return null;
    }

    @Override
    public Boolean hasPermission(String sessionId,String url) {

        UserDO userDO = getLoginUser(sessionId);
        // 判断user是否存在
        if(null == userDO){
            return false;
        }
        // 判断资源是否存在
        List<ResourceDO> resourceDOS = findResourcesByAccount(userDO.getAccount());
        if (CollectionUtils.isEmpty(resourceDOS)) {
            return false;
        }

        // 判断资源地址是否存在
        List<String> urls = resourceDOS.stream().map(ResourceDO::getUrl).collect(Collectors.toList());
        if (urls.contains(url)){
            return true;
        }else {
            return false;
        }

    }

    @Override
    public void refreshSassUserInfo(String account) {
        LOGGER.info("refreshSassUserInfo:{}",account);
        SassUserInfo grantedUserInfo = userAclTokenRepository.getSassUserByUserAccount(account);
        if(null != grantedUserInfo){
            // 缓存信息以及存在用户登录，在需要更新用户权限值
            //跟新用户缓存的角色以及权限值

            List<RoleDO> roleDOS = this.findRolesByAccount(account);
            if (!org.apache.commons.collections4.CollectionUtils.isEmpty(roleDOS)){
                LOGGER.info("account:{},roleList:{}",account,JSONObject.toJSONString(roleDOS));
                Set<String> roleCodes = new HashSet<>(roleDOS.size());
                roleDOS.forEach(roleDO -> roleCodes.add(roleDO.getCode()));
                grantedUserInfo.setRoles(roleCodes);
            }else{
                grantedUserInfo.setRoles(new HashSet<>());
            }

            List<ResourceDO> resourceDOS = this.findResourcesByAccount(account);
            if (!org.apache.commons.collections4.CollectionUtils.isEmpty(resourceDOS)){
                LOGGER.info("account:{},resourceList:{}",account,JSONObject.toJSONString(resourceDOS));
                Set<String> permissionUrls = new HashSet<>(resourceDOS.size());
                resourceDOS.forEach(resourceDO -> permissionUrls.add(resourceDO.getAuthority()));
                grantedUserInfo.setStringPermissions(permissionUrls);
            }else{
                grantedUserInfo.setStringPermissions(new HashSet<>());
            }
            // 设置用户的token以及角色，权限等信息缓存
            LOGGER.info("account:{},grantedUserInfo:{}",account,JSONObject.toJSONString(grantedUserInfo));
            userAclTokenRepository.setSassUserByUserAccount(account,grantedUserInfo);
        }else{
            LOGGER.info("grantRoleUserInfo, 无需更新用户权限值");
        }
    }

    @Override
    public int createUserOrgRelations(UserOrgDO userOrgDO) {
        return this.userOrgDOMapper.insertSelective(userOrgDO);
    }

    @Override
    public List<UserOrgDO> queryUserOrgsByAccount(String account) {

        List<UserOrgDO> userOrgDOList = this.userOrgDOMapper.queryUserOrgsByAccount(account);

        return userOrgDOList;
    }




    @Override
    public int removeUserOrgRelationByAccount(String account) {
        return this.userOrgDOMapper.removeUserOrgRelationByAccount(account);
    }

    @Override
    public int removeUserOrgRelationByOrgCode(String orgCode) {
        return this.userOrgDOMapper.removeUserOrgRelationByOrgCode(orgCode);
    }

    @Override
    public int createUserOrgRelationsByList(List<UserOrgDO> userOrgDOList) {
        return this.userOrgDOMapper.insertByBatch(userOrgDOList);
    }

    @Override
    public int removeUserOrgRelationByAccountList(List<String> accounts) {
        return this.userOrgDOMapper.removeUserOrgRelationByAccountList(accounts);
    }


    @Override
    public Map<String, List<UserOrgDO>> queryUserOrgsMapsByAccounts(List<String> accounts) {

        if(org.apache.commons.collections4.CollectionUtils.isEmpty(accounts)){
            throw new BusinessException(SassBizResultCodeEnum.PARAMETER_NULL,"查询参数不能为空");
        }

        List<UserOrgDO> userOrgDOList = this.userOrgDOMapper.queryUserOrgsByAccountList(accounts);

        Map<String, List<UserOrgDO>> resultMaps = Maps.newHashMap();

        for(String account: accounts){
            List<UserOrgDO> mapDOList = userOrgDOList.stream()
                    .filter(x-> StringUtils.equals(account,x.getUserAccount()))
                    .collect(Collectors.toList());
            resultMaps.put(account,mapDOList);
        }

        return resultMaps;
    }

    @Override
    public List<UserOrgDO> queryUserOrgsByOrgCode(String orgCode) {

        List<UserOrgDO> userOrgDOList = this.userOrgDOMapper.queryUserOrgsByOrgCode(orgCode);

        return userOrgDOList;
    }

    @Override
    public List<UserOrgDO> queryUserOrgsByOrgCodeLists(List<String> orgCodes) {

        List<UserOrgDO> userOrgDOList = this.userOrgDOMapper.queryUserOrgsByOrgCodeLists(orgCodes);
        return userOrgDOList;
    }
}
