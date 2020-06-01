package cn.com.xinxin.sass.web.rest;

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
import cn.com.xinxin.sass.auth.web.AclController;
import cn.com.xinxin.sass.biz.service.*;
import cn.com.xinxin.sass.common.enums.SassBizResultCodeEnum;

import cn.com.xinxin.sass.repository.model.*;
import cn.com.xinxin.sass.web.utils.TreeResultUtil;
import cn.com.xinxin.sass.web.vo.MenuTreeVO;
import cn.com.xinxin.sass.web.vo.ResourceVO;
import com.google.common.collect.Lists;
import com.xinxinfinance.commons.exception.BusinessException;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;


/**
 * @author: zhouyang
 * @created: 17/04/2020.
 * @updater:
 * @description: 租户管理接口
 */
@RestController
@RequestMapping(value = "/tenant",produces = "application/json; charset=UTF-8")
public class SassTenantInitRestController extends AclController {

    private static final Logger loger = LoggerFactory.getLogger(SassTenantInitRestController.class);

    @Autowired
    private ResourceService resourceService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private RoleResourceService roleResourceService;

    @Autowired
    private AuthsService authsService;

    @Autowired
    private OrganizationService organizationService;

    @Autowired
    private TenantBaseInfoService tenantBaseInfoService;


    @RequestMapping(value = "/user/init",method = RequestMethod.GET)
    @ResponseBody
    @RequiresRoles(value = {"sass_admin","admin","sass_mng"},logical= Logical.OR)
    public Object tenantDataInit(HttpServletRequest request,
                                 @Param("tenantId") String tenantId,
                                 @Param("account") String account){

        loger.info("SassTenantRestController,tenantDataInit,tenantId={},account={}", tenantId,account);

        SassUserInfo sassUserInfo = this.getSassUser(request);


        String opsTenantId = this.getOpsTenantId(request);

        if(StringUtils.isBlank(opsTenantId)){
            throw new BusinessException(SassBizResultCodeEnum.ILLEGAL_PARAMETER, "需要运营的租户不能为空");
        }


        if(StringUtils.isEmpty(tenantId)){
            tenantId = opsTenantId;
        }
        // 参数转换设置
//        if(!sassUserInfo.getTenantId().equals(tenantId)){
//            throw new BusinessException(SassBizResultCodeEnum.ILLEGAL_PARAMETER,"不能初始化非自己租户的数据");
//        }
        // 初始化之前,务必先添加租户信息。初始化数据只能初始化admin的类型账号
        // 1.初始化权限资源

        List<AuthsDO> authsDOList = this.authsService.selectAllAuths();
        // 组装必要的参数, 将权限值组装成一个完整的树结构插入数据
        List<MenuTreeVO> authsResourceVOList = Lists.newArrayList();
        authsDOList.stream().forEach(
                authsDO -> {
                    MenuTreeVO menuTreeVO = new MenuTreeVO();
                    menuTreeVO.setText(authsDO.getName());
                    menuTreeVO.setParentId(String.valueOf(authsDO.getParentId()));
                    menuTreeVO.setId(String.valueOf(authsDO.getId()));
                    menuTreeVO.setCode(authsDO.getCode());
                    menuTreeVO.setUrl(authsDO.getUrl());
                    menuTreeVO.setAuthority(authsDO.getAuthority());
                    menuTreeVO.setOrder(0);
                    menuTreeVO.setType(authsDO.getResourceType());
                    authsResourceVOList.add(menuTreeVO);
                }
        );
        List<MenuTreeVO> authsTrees = TreeResultUtil.build(authsResourceVOList);

        List<String> rsCodes = Lists.newArrayList();

        for(MenuTreeVO treeVO: authsTrees){

            ResourceDO resourceDO = new ResourceDO();
            resourceDO.setParentId(Long.valueOf(treeVO.getParentId()));
            String treesCode = tenantId.substring(4).toUpperCase() + treeVO.getCode();
            rsCodes.add(treesCode);
            resourceDO.setCode(treesCode);
            resourceDO.setTenantId(tenantId);
            resourceDO.setResourceType(treeVO.getType());
            resourceDO.setName(treeVO.getText());
            resourceDO.setAuthority(treeVO.getAuthority());
            resourceDO.setUrl(treeVO.getUrl());
            resourceDO.setExtension(treeVO.getText());
            resourceDO.setGmtCreator(sassUserInfo.getAccount());
            resourceDO.setGmtUpdater(sassUserInfo.getAccount());
            // 如果parent=0,那么则是根节点
            resourceDO.setRoot(treeVO.getParentId().equals("0"));
            this.resourceService.createResource(resourceDO);
            ResourceDO inserted = this.resourceService.findByResourceCode(treesCode);
            Long insertId = inserted.getId();
            //int insertId = 100;

            if(CollectionUtils.isNotEmpty(treeVO.getChildren())){
                //处理子节点数据
                List<MenuTreeVO> childrens = treeVO.getChildren();
                for(MenuTreeVO childrenVO: childrens){
                    ResourceDO childresourceDO = new ResourceDO();
                    childresourceDO.setParentId(Long.valueOf(insertId));
                    childresourceDO.setTenantId(tenantId);
                    String childrsCode = sassUserInfo.getTenantId().toUpperCase() + childrenVO.getCode();
                    rsCodes.add(childrsCode);
                    childresourceDO.setCode(childrsCode);
                    childresourceDO.setResourceType(childrenVO.getType());
                    childresourceDO.setName(childrenVO.getText());
                    childresourceDO.setAuthority(childrenVO.getAuthority());
                    childresourceDO.setUrl(childrenVO.getUrl());
                    childresourceDO.setExtension(childrenVO.getText());
                    // 如果parent=0,那么则是根节点
                    childresourceDO.setRoot(childrenVO.getParentId().equals("0"));
                    childresourceDO.setGmtCreator(sassUserInfo.getAccount());
                    childresourceDO.setGmtUpdater(sassUserInfo.getAccount());
                    this.resourceService.createResource(childresourceDO);
                    ResourceDO childinserted = this.resourceService.findByResourceCode(childrsCode);
                    Long childId = childinserted.getId();
                    if(CollectionUtils.isNotEmpty(childrenVO.getChildren())){
                        //处理子节点数据
                        List<MenuTreeVO> subchildrens = childrenVO.getChildren();
                        for(MenuTreeVO subchildrenVO: subchildrens){
                            ResourceDO subchildresourceDO = new ResourceDO();
                            subchildresourceDO.setTenantId(tenantId);
                            subchildresourceDO.setParentId(Long.valueOf(childId));
                            String subrsCode = tenantId.substring(4).toUpperCase() + subchildrenVO.getCode();
                            subchildresourceDO.setCode(subrsCode);
                            rsCodes.add(subrsCode);
                            subchildresourceDO.setResourceType(subchildrenVO.getType());
                            subchildresourceDO.setName(subchildrenVO.getText());
                            subchildresourceDO.setAuthority(subchildrenVO.getAuthority());
                            subchildresourceDO.setUrl(subchildrenVO.getUrl());
                            subchildresourceDO.setExtension(subchildrenVO.getText());
                            // 如果parent=0,那么则是根节点
                            subchildresourceDO.setRoot(subchildrenVO.getParentId().equals("0"));
                            subchildresourceDO.setGmtCreator(sassUserInfo.getAccount());
                            subchildresourceDO.setGmtUpdater(sassUserInfo.getAccount());
                            int subchildId = this.resourceService.createResource(subchildresourceDO);

                        }

                    }
                }
            }

        }

        // 2.初始化角色信息
        RoleDO roleDO = new RoleDO();
        roleDO.setTenantId(tenantId);
        String roleCode = tenantId.substring(4).toUpperCase()+"RO"+ RandomStringUtils.randomNumeric(6);
        roleDO.setCode(roleCode);
        roleDO.setName("租户管理员角色");
        roleDO.setRoleType("admin");
        roleDO.setExtension("租户管理员角色");
        roleDO.setGmtCreator(sassUserInfo.getAccount());
        roleDO.setGmtUpdater(sassUserInfo.getAccount());
        this.roleService.createRole(roleDO,rsCodes);

        // 3.初始化用户信息
        UserRoleDO userRoleDO = new UserRoleDO();
        userRoleDO.setTenantId(tenantId);
        userRoleDO.setRoleName(roleDO.getName());
        userRoleDO.setRoleCode(roleDO.getCode());
        userRoleDO.setUserName(account);
        userRoleDO.setUserAccount(account);
        userRoleDO.setGmtUpdater(sassUserInfo.getAccount());
        userRoleDO.setGmtCreator(sassUserInfo.getAccount());
        this.userRoleService.createUserRole(userRoleDO);

        // 4.初始化组织机构
        TenantBaseInfoDO tenantBaseInfoDO = this.tenantBaseInfoService.selectByTenantId(tenantId);
        OrganizationDO organizationDO = new OrganizationDO();
        String orgCode = tenantId.substring(4).toUpperCase()+"ORG"+ RandomStringUtils.randomNumeric(6);
        organizationDO.setCode(orgCode);
        organizationDO.setTenantId(tenantId);
        organizationDO.setIsLeaf(false);
        organizationDO.setOrgType("COMP");
        organizationDO.setParentId(0L);
        organizationDO.setName(tenantBaseInfoDO.getTenantName());
        organizationDO.setGmtUpdater(sassUserInfo.getAccount());
        organizationDO.setGmtCreator(sassUserInfo.getAccount());
        organizationDO.setState("Y");
        this.organizationService.createOrganization(organizationDO);

        return null;
    }

}
