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

import cn.com.xinxin.sass.auth.enums.AuthEnums;
import cn.com.xinxin.sass.auth.model.SassUserInfo;
import cn.com.xinxin.sass.auth.web.AclController;
import cn.com.xinxin.sass.biz.log.SysLog;
import cn.com.xinxin.sass.biz.service.*;
import cn.com.xinxin.sass.common.enums.ResourceTypeEnums;
import cn.com.xinxin.sass.common.enums.SassBizResultCodeEnum;
import cn.com.xinxin.sass.common.model.PageResultVO;
import cn.com.xinxin.sass.repository.model.AuthsDO;
import cn.com.xinxin.sass.repository.model.ResourceDO;
import cn.com.xinxin.sass.repository.model.RoleResourceDO;
import cn.com.xinxin.sass.web.convert.SassFormConvert;
import cn.com.xinxin.sass.web.form.ResourceForm;
import cn.com.xinxin.sass.web.form.ResourceQueryForm;
import cn.com.xinxin.sass.web.utils.RegexUtils;
import cn.com.xinxin.sass.web.utils.TreeResultUtil;
import cn.com.xinxin.sass.web.vo.GrantsVO;
import cn.com.xinxin.sass.web.vo.MenuTreeVO;
import cn.com.xinxin.sass.web.vo.ResourceVO;
import com.google.common.collect.Lists;
import com.xinxinfinance.commons.exception.BusinessException;
import com.xinxinfinance.commons.util.BaseConvert;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: zhouyang
 * @created: 17/04/2020.
 * @updater:
 * @description: 权限管理以及对应的菜单功能管理, 对于用户的菜单权限，同时也要控制用户的按钮功能权限
 */
@RestController
@RequestMapping(value = "/resource",produces = "application/json; charset=UTF-8")
public class SassResourceRestController extends AclController {


    private static final Logger log = LoggerFactory.getLogger(SassResourceRestController.class);

    @Autowired
    private ResourceService resourceService;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private RoleResourceService roleResourceService;

    @Autowired
    private AuthsService authsService;


    @RequestMapping(value = "/list",method = RequestMethod.POST)
    @ResponseBody
    @RequiresRoles(value = {"sass_admin","admin","sass_mng"},logical= Logical.OR)
    public Object ListResource(HttpServletRequest request, @RequestBody ResourceQueryForm resourceQueryForm){

        log.info("ResourceController.list,resourceQueryForm={}", resourceQueryForm);

        if(null == resourceQueryForm){
            throw new BusinessException(SassBizResultCodeEnum.ILLEGAL_PARAMETER,"参数不能为空 ");
        }

        PageResultVO pageVo = new PageResultVO();
        pageVo.setPageSize(resourceQueryForm.getPageSize());
        pageVo.setPageNumber(resourceQueryForm.getPageIndex());

        ResourceDO condition = new ResourceDO();
        condition.setResourceType(resourceQueryForm.getResourceType());
        condition.setCode(resourceQueryForm.getCode());
        condition.setName(resourceQueryForm.getName());
        condition.setUrl(resourceQueryForm.getUrl());
        if ( resourceQueryForm.getRoot()!= null){
            condition.setRoot(resourceQueryForm.getRoot());
        }
        PageResultVO result = resourceService.findByConditionPage(pageVo,condition);

        return result;
    }


    @RequestMapping(value = "/grants/list",method = RequestMethod.GET)
    @ResponseBody
    public Object listAllGrantsValues(HttpServletRequest request){

        log.info("ResourceController.listAllGrantsValues");

//        List<AuthEnums> authEnumsList = AuthEnums.toLists();
//        List<GrantsVO> allGrants = Lists.newArrayList();
//        authEnumsList.stream().forEach(authEnums -> {
//            GrantsVO grantsVO = new GrantsVO();
//            grantsVO.setCode(authEnums.getCode());
//            grantsVO.setName(authEnums.getName());
//            grantsVO.setDesc(authEnums.getDesc());
//            allGrants.add(grantsVO);
//
//        });

        List<AuthsDO> authsDOList = this.authsService.selectAllAuths();
        List<GrantsVO> allGrants = Lists.newArrayList();
        authsDOList.stream().forEach(authsDO -> {
            GrantsVO grantsVO = new GrantsVO();
            grantsVO.setCode(authsDO.getAuthority());
            grantsVO.setName(authsDO.getName());
            grantsVO.setDesc(authsDO.getName());
            allGrants.add(grantsVO);
        });

        return allGrants;
    }




    /**
     * 资源权限查询接口
     * 具体功能包括： 1。根据权限Code查询某个权限值以及其子集
     * @param request
     * @return
     */
    @RequestMapping(value = "/query/tree",method = RequestMethod.POST)
    @ResponseBody
    public Object queryResourceTrees(HttpServletRequest request,
                                     @RequestBody ResourceQueryForm resourceQueryForm){

        log.info("ResourceController.queryResourceTrees,resourceQueryForm={}", resourceQueryForm);

        String rsCode = "";
        String rsType  = resourceQueryForm.getResourceType();
        String rsParentId = resourceQueryForm.getParentId();

        List<ResourceDO> resourceDOSList = this.resourceService.queryResourceTrees(rsCode,rsType,rsParentId);

        if(CollectionUtils.isEmpty(resourceDOSList)){
            throw new BusinessException(SassBizResultCodeEnum.DATA_NOT_EXIST,"无法查询权限值列表");
        }

        List<ResourceVO> resourceVOList = SassFormConvert.convertResourceDO2VO(resourceDOSList);

        return resourceVOList;

    }


    /**
     * 仅读取menu菜单的权限
     * 如果传递rolecode过来就表示同时查询某个角色下面已经有的权限值
     * @param request
     * @return
     */
    @RequestMapping(value = "/menu/tree",method = RequestMethod.GET)
    @ResponseBody
    public Object treeMenuResource(HttpServletRequest request){

        log.info("ResourceController.treeMenuResource,roleCode={}");

        List<ResourceDO> resourceDOSList = this.resourceService.findAllResources();

        if(CollectionUtils.isEmpty(resourceDOSList)){
            throw new BusinessException(SassBizResultCodeEnum.DATA_NOT_EXIST,"无法查询权限值列表");
        }

        List<ResourceDO> menuResourceLists = resourceDOSList
                .stream()
                .filter(resourceDO -> StringUtils.isNotEmpty(resourceDO.getResourceType())
                        &&resourceDO.getResourceType().equals(ResourceTypeEnums.MENU_TYPE.getCode()))
                .collect(Collectors.toList());

        List<ResourceVO> resourceVOList = SassFormConvert.convertResourceDO2VO(menuResourceLists);

        // 组装必要的参数
        List<MenuTreeVO> resourceTreeVOList = Lists.newArrayList();
        resourceVOList.stream().forEach(
                resourceVO -> {
                    MenuTreeVO menuTreeVO = new MenuTreeVO();
                    menuTreeVO.setText(resourceVO.getName());
                    menuTreeVO.setParentId(String.valueOf(resourceVO.getParentId()));
                    menuTreeVO.setId(String.valueOf(resourceVO.getId()));
                    menuTreeVO.setCode(resourceVO.getCode());
                    menuTreeVO.setUrl(resourceVO.getUrl());
                    menuTreeVO.setAuthority(resourceVO.getAuthority());
                    menuTreeVO.setExtension(resourceVO.getExtension());
                    menuTreeVO.setChecked(false);
                    menuTreeVO.setOrder(0);
                    resourceTreeVOList.add(menuTreeVO);
                }
        );

        List<MenuTreeVO> results = TreeResultUtil.build(resourceTreeVOList);
        // 返回权限树
        return results;

    }



    /**
     * 获取所有权限资源的列表，用于在创建角色或者角色赋值的时候拉去权限值
     * 如果传递rolecode过来就表示同时查询某个角色下面已经有的权限值
     * @param request
     * @return
     */
    @RequestMapping(value = "/tree",method = RequestMethod.GET)
    @ResponseBody
    public Object treeResource(HttpServletRequest request,
                               @Param("roleCode") String roleCode){

        log.info("ResourceController.treeResource,roleCode={}", roleCode);

        List<ResourceDO> resourceDOSList = this.resourceService.findAllResources();

        if(CollectionUtils.isEmpty(resourceDOSList)){
            throw new BusinessException(SassBizResultCodeEnum.DATA_NOT_EXIST,"无法查询权限值列表");
        }

        List<ResourceVO> resourceVOList = SassFormConvert.convertResourceDO2VO(resourceDOSList);

        List<String> resourceCodeList = Lists.newArrayList();

        if(StringUtils.isNotEmpty(roleCode)){
            // 如果roleCode不为空，则表示需要查询roleCode的权限值
            List<ResourceDO> roleResourceDOS = this.roleResourceService.findResourcesByRoleCode(roleCode);
            resourceCodeList = roleResourceDOS
                    .stream()
                    .map(resourceDO -> resourceDO.getCode())
                    .collect(Collectors.toList());
        }

        // 组装必要的参数
        List<MenuTreeVO> resourceTreeVOList = Lists.newArrayList();
        resourceVOList.stream().forEach(
                resourceVO -> {
                    MenuTreeVO menuTreeVO = new MenuTreeVO();
                    menuTreeVO.setText(resourceVO.getName());
                    menuTreeVO.setParentId(String.valueOf(resourceVO.getParentId()));
                    menuTreeVO.setId(String.valueOf(resourceVO.getId()));
                    menuTreeVO.setCode(resourceVO.getCode());
                    menuTreeVO.setUrl(resourceVO.getUrl());
                    menuTreeVO.setAuthority(resourceVO.getAuthority());
                    menuTreeVO.setExtension(resourceVO.getExtension());
                    menuTreeVO.setChecked(false);
                    menuTreeVO.setOrder(0);
                    resourceTreeVOList.add(menuTreeVO);
                }
        );

        List<MenuTreeVO> results = TreeResultUtil.buildCheckedTree(resourceTreeVOList,resourceCodeList);
        // 返回权限树
        return results;

    }


    @RequestMapping(value = "/create",method = RequestMethod.POST)
    @ResponseBody
    @RequiresRoles(value = {"sass_admin","admin","sass_mng"},logical= Logical.OR)
    @SysLog("创建资源权限")
    public Object createResource(HttpServletRequest request,
                                   @RequestBody ResourceForm resourceForm){

        if(!RegexUtils.isDataCode(resourceForm.getCode())){
            // 如果匹配不是useraccount格式
            throw new BusinessException(SassBizResultCodeEnum.ILLEGAL_PARAMETER,
                    "编码不能包含特殊字符或者长度超过16","编码不能包含特殊字符或者长度超过16");
        }

        SassUserInfo sassUserInfo = this.getSassUser(request);
        String userAccount = sassUserInfo.getAccount();

        // FIXME: 看是否需要判断资源以及存在
        ResourceDO resourceDO = SassFormConvert.convertResourceForm2ResourceDO(resourceForm);
        resourceDO.setGmtCreator(userAccount);
        resourceDO.setGmtUpdater(userAccount);

        // FIXME: 先默认设置为xinxin租户
        resourceDO.setTenantId("xinxin");


        try {
            int result = resourceService.createResource(resourceDO);

            if(result==0){
                throw new BusinessException(SassBizResultCodeEnum.FAIL,"创建资源出错","清检查参数是否正确");
            }

        }catch (DuplicateKeyException dex){

            throw new BusinessException(SassBizResultCodeEnum.ILLEGAL_PARAMETER, "编码不能重复","编码不能重复");

        }catch (Exception ex){

            throw new BusinessException(SassBizResultCodeEnum.FAIL, "处理异常，请稍后重试","处理异常，请稍后重试");

        }

        ResourceDO createdResourceDO = this.resourceService.findByResourceCode(resourceForm.getCode());

        ResourceVO createResVO = BaseConvert.convert(createdResourceDO, ResourceVO.class);

        return createResVO;

    }

    @SysLog("更新资源权限")
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    @ResponseBody
    @RequiresRoles(value = {"sass_admin","admin","sass_mng"},logical= Logical.OR)
    public Object updateResource(HttpServletRequest request,
                             @RequestBody ResourceForm resourceForm){

        SassUserInfo sassUserInfo = this.getSassUser(request);

        String userAccount = sassUserInfo.getAccount();

        ResourceDO resourceDO = SassFormConvert.convertResourceForm2ResourceDO(resourceForm);

        resourceDO.setGmtUpdater(userAccount);

        int resultCount = resourceService.updateResource(resourceDO);

        return resultCount;

    }

    @SysLog("删除资源权限")
    @RequestMapping(value = "/delete",method = RequestMethod.DELETE)
    @ResponseBody
    @RequiresRoles(value = {"sass_admin","admin","sass_mng"},logical= Logical.OR)
    public Object deleteResource(HttpServletRequest request,
                                 @RequestParam String id){

        SassUserInfo sassUserInfo = this.getSassUser(request);

        String userAccount = sassUserInfo.getAccount();

        log.info("ResourceController.delete,account={}, id={}", userAccount, id);

        ResourceDO resourceDO = this.resourceService.findById(Long.valueOf(id));

        List<RoleResourceDO> RoRslists = this.roleResourceService.queryRolesAndResourcesByRSCode(resourceDO.getCode());

        if(CollectionUtils.isNotEmpty(RoRslists)){
            // 如果某个资源关联角色，则不能删除该权限资源
            throw new BusinessException(SassBizResultCodeEnum.FAIL,
                    "该资源权限已经使用，删除需要移除关联角色","该资源权限已经使用，删除需要移除关联角色");
        }

        List<ResourceDO> subResourceDOList =
                this.resourceService.findChildrenByParentIds(Lists.newArrayList(Long.valueOf(id)));

        if(CollectionUtils.isNotEmpty(subResourceDOList)){
            throw new BusinessException(SassBizResultCodeEnum.FAIL,
                    "该资源权限已经使用，并且关联其他资源类型无法删除","该资源权限已经使用，并且关联其他资源类型无法删除");
        }

        boolean result = resourceService.deleteById(Long.valueOf(id));

        return result;
    }


    @SysLog("增加全局资源权限")
    @RequestMapping(value = "/auth/create",method = RequestMethod.POST)
    @ResponseBody
    @RequiresRoles(value = {"sass_admin","admin","sass_mng"},logical= Logical.OR)
    public Object createGlobalAuth(HttpServletRequest request, @RequestBody ResourceForm resourceForm){


        SassUserInfo sassUserInfo = this.getSassUser(request);

        String userAccount = sassUserInfo.getAccount();

        if(!RegexUtils.isDataCode(resourceForm.getCode())){
            // 如果匹配不是useraccount格式
            throw new BusinessException(SassBizResultCodeEnum.ILLEGAL_PARAMETER,
                    "编码不能包含特殊字符或者长度超过16","编码不能包含特殊字符或者长度超过16");
        }

        AuthsDO authsDO = BaseConvert.convert(resourceForm,AuthsDO.class);

        authsDO.setGmtCreator(userAccount);
        authsDO.setGmtUpdater(userAccount);

        int result = this.authsService.createAuths(authsDO);

        return result;

    }

    @SysLog("修改全局资源权限")
    @RequestMapping(value = "/auth/update",method = RequestMethod.POST)
    @ResponseBody
    @RequiresRoles(value = {"sass_admin","admin","sass_mng"},logical= Logical.OR)
    public Object updateGlobalAuth(HttpServletRequest request, @RequestBody ResourceForm resourceForm){

        if(!RegexUtils.isDataCode(resourceForm.getCode())){
            // 如果匹配不是useraccount格式
            throw new BusinessException(SassBizResultCodeEnum.ILLEGAL_PARAMETER,
                    "编码不能包含特殊字符或者长度超过16","编码不能包含特殊字符或者长度超过16");
        }

        AuthsDO authsDO = BaseConvert.convert(resourceForm,AuthsDO.class);

        SassUserInfo sassUserInfo = this.getSassUser(request);

        String userAccount = sassUserInfo.getAccount();

        authsDO.setGmtUpdater(userAccount);

        int result = this.authsService.updateAuths(authsDO);

        return result;

    }


    @SysLog("删除全局资源权限")
    @RequestMapping(value = "/auth/delete",method = RequestMethod.DELETE)
    @ResponseBody
    @RequiresRoles(value = {"sass_admin","admin","sass_mng"},logical= Logical.OR)
    public Object deleteGlobalAuth(HttpServletRequest request,  @RequestParam String id){

        SassUserInfo sassUserInfo = this.getSassUser(request);

        String userAccount = sassUserInfo.getAccount();

        log.info("ResourceController.delete,account={}, id={}", userAccount, id);

        int result = this.authsService.deleteById(Long.valueOf(id));

        return result;

    }


}
