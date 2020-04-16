package cn.com.xinxin.sass.web.controller;

import cn.com.xinxin.sass.biz.service.ResourceService;
import cn.com.xinxin.sass.biz.service.RoleResourceService;
import cn.com.xinxin.sass.biz.service.RoleService;
import cn.com.xinxin.sass.common.Page;
import cn.com.xinxin.sass.common.enums.SassBizResultCodeEnum;
import cn.com.xinxin.sass.repository.model.ResourceDO;
import cn.com.xinxin.sass.repository.model.RoleDO;
import cn.com.xinxin.sass.repository.model.RoleResourceDO;
import cn.com.xinxin.sass.auth.annotation.RequirePermission;
import cn.com.xinxin.sass.auth.web.AclController;
import cn.com.xinxin.sass.auth.model.SassUserInfo;
import cn.com.xinxin.sass.web.vo.RoleResourceVO;
import com.google.common.collect.Lists;
import com.xinxinfinance.commons.exception.BusinessException;
import com.xinxinfinance.commons.portal.view.result.PortalPageViewResultVO;
import com.xinxinfinance.commons.portal.view.result.PortalSingleViewResultVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * Created by dengyunhui on 2018/5/4
 **/
@Controller
@RequestMapping("/permission/role")
public class RoleController extends AclController {

    private static final Logger log = LoggerFactory.getLogger(RoleController.class);

    @Autowired
    private RoleService roleService;

    @Autowired
    private RoleResourceService roleResourceService;

    @Autowired
    private ResourceService resourceService;

    @RequirePermission("/permission/role")
    @RequestMapping(method = RequestMethod.GET)
    public String index(HttpServletRequest request, Model model) {
        return "role/role";
    }


    @RequirePermission("/permission/role/create")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    @Transactional
    public PortalSingleViewResultVO create(HttpServletRequest request, @RequestBody RoleResourceVO roleResourceVO) {

        log.info("RoleController.create,roleResourceVO = {}",roleResourceVO);


        if(null == roleResourceVO){
            throw new BusinessException(SassBizResultCodeEnum.PARAMETER_NULL, "参数不能为空");
        }

        if(StringUtils.isEmpty(roleResourceVO.getCode())){
            throw new BusinessException(SassBizResultCodeEnum.PARAMETER_NULL, "资源编码不能为空");
        }

        if(StringUtils.isEmpty(roleResourceVO.getName())){
            throw new BusinessException(SassBizResultCodeEnum.PARAMETER_NULL, "资源名称不能为空");
        }

        if(StringUtils.isEmpty(roleResourceVO.getRoleType())){
            throw new BusinessException(SassBizResultCodeEnum.PARAMETER_NULL, "角色类型不能为空");
        }

        RoleDO exist = roleService.findByRoleCode(roleResourceVO.getCode());
        if (exist != null){
            return PortalSingleViewResultVO.result(null, true, "角色编码已经存在");
        }

        SassUserInfo sassUserInfo = this.getSassUser(request);


        // 组装角色参数
        RoleDO roleDO = new RoleDO();
        roleDO.setRoleType(roleResourceVO.getRoleType());
        roleDO.setName(roleResourceVO.getName());
        roleDO.setCode(roleResourceVO.getCode());
        roleDO.setGmtCreator(sassUserInfo.getAccount());

        // 创建用户角色
        RoleDO createRoleResult = roleService.createRole(roleDO);

        String resourceIdsStr = roleResourceVO.getRoleResources();

        List<RoleResourceDO> roleResources = Lists.newArrayList();
        if (!StringUtils.isEmpty(resourceIdsStr)) {
            String[] resourceCodes = resourceIdsStr.split(",");
            List<Long> ids = new ArrayList<>();
            for (String resourceCode : resourceCodes) {
                if (!StringUtils.isEmpty(resourceCode)) {
                    ids.add(Long.parseLong(resourceCode));
                }
            }

            List<ResourceDO> resourceDOS = resourceService.findResourcesByIds(ids);

            for (ResourceDO resourceDO : resourceDOS) {
                RoleResourceDO roleResourceDO = new RoleResourceDO();
                roleResourceDO.setResourceName(resourceDO.getName());
                roleResourceDO.setResourceCode(resourceDO.getCode());
                roleResourceDO.setRoleName(roleResourceVO.getName());
                roleResourceDO.setRoleCode(roleResourceVO.getCode());
                roleResourceDO.setGmtCreator(sassUserInfo.getAccount());

                roleResources.add(roleResourceDO);
            }
        }

        if (!CollectionUtils.isEmpty(roleResources)) {
            roleResourceService.createRoleResources(roleResources);
        }



        return PortalSingleViewResultVO.result(null, createRoleResult == null, null);
    }

    @RequirePermission("/permission/role/list")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public PortalPageViewResultVO list(HttpServletRequest request,
                       @RequestParam Integer limit,
                       @RequestParam Integer pageIndex,
                       @RequestParam String name,
                       @RequestParam String code,
                       @RequestParam String roleType) {
        log.info("RoleController.list,limit:{},pageIndex:{},name:{},code:{},roleType:{}",
            limit,pageIndex,name,code,roleType);

        Page page = new Page();
        page.setPageSize(limit);
        page.setPageNumber(pageIndex);

        RoleDO condition = new RoleDO();
        condition.setCode(code);
        condition.setName(name);
        condition.setRoleType(roleType);
        Page<RoleDO> roleDOS = roleService.findByConditionPage(page, condition);

        PortalPageViewResultVO portalPageViewResultVO = new PortalPageViewResultVO();
        portalPageViewResultVO.setPageNumber(roleDOS.getPageNumber());
        portalPageViewResultVO.setPageSize(roleDOS.getPageSize());
        portalPageViewResultVO.setRows(roleDOS.getRows());
        portalPageViewResultVO.setTotal(roleDOS.getTotal());

        return portalPageViewResultVO;
    }


    @RequestMapping(value = "/findById/{id}", method = RequestMethod.GET)
    @ResponseBody
    public PortalSingleViewResultVO findById(HttpServletRequest request, @PathVariable Long id) {
        log.info("RoleController.findById,id:{}",id);

        RoleDO roleDO = roleService.findOne(id);
        return PortalSingleViewResultVO.result(roleDO, roleDO == null, "操作失败");
    }

    @RequirePermission("/permission/role/update")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    @Transactional
    public PortalSingleViewResultVO update(HttpServletRequest request, @RequestBody RoleResourceVO roleResourceVO) {
        log.info("RoleController.update,roleResourceVO:{}",roleResourceVO);


        SassUserInfo sassUserInfo = this.getSassUser(request);

        RoleDO roleDO = new RoleDO();
        roleDO.setId(roleResourceVO.getId());
        roleDO.setCode(roleResourceVO.getCode());
        roleDO.setName(roleResourceVO.getName());
        roleDO.setRoleType(roleResourceVO.getRoleType());

        roleService.updateRole(roleDO);

        if (!StringUtils.isEmpty(roleResourceVO.getRoleResources())) {
            List<ResourceDO> resourceDOS = roleResourceService.findResources(roleResourceVO.getCode());
            List<String> oldResourceCodes = new ArrayList<>();
            if (!CollectionUtils.isEmpty(resourceDOS)) {
                oldResourceCodes = resourceDOS.stream().map(ResourceDO::getCode).collect(toList());
            }

            String resourceIds = roleResourceVO.getRoleResources();
            String[] resourceIdArray = resourceIds.split(",");
            List<Long> resourceIdList = new ArrayList<>();
            for (String resourceId : resourceIdArray) {
                if (!StringUtils.isEmpty(resourceId)) {
                    resourceIdList.add(Long.parseLong(resourceId));
                }
            }

            List<ResourceDO> resourceDOS1 = resourceService.findResourcesByIds(resourceIdList);
            List<String> resourceCodesList = resourceDOS1.stream().map(ResourceDO::getCode).collect(toList());

            List<String> newRecourceCodes = new ArrayList<>();
            for (String newResourceCode : resourceCodesList) {
                if (!oldResourceCodes.contains(newResourceCode)) {
                    newRecourceCodes.add(newResourceCode);
                }
            }

            List<String> deleteRecourceCodes = new ArrayList<>();

            for (String oldResourceCode : oldResourceCodes) {
                if (!resourceCodesList.contains(oldResourceCode)) {
                    deleteRecourceCodes.add(oldResourceCode);
                }
            }

            if (!CollectionUtils.isEmpty(newRecourceCodes)) {
                RoleDO roleDO1 = roleService.findOne(roleResourceVO.getId());
                List<RoleResourceDO> roleResourceDOS = new ArrayList<>();
                for (String resourceCode : newRecourceCodes) {
                    RoleResourceDO roleResourceDO = new RoleResourceDO();
                    roleResourceDO.setRoleCode(roleResourceVO.getCode());
                    roleResourceDO.setRoleName(roleDO1.getName());
                    roleResourceDO.setResourceCode(resourceCode);

                    List<ResourceDO> resourceDOS2 = resourceDOS1.stream().filter(resourceDO -> !resourceDO.getCode().equals(resourceCode)).collect(toList());
                    if (!CollectionUtils.isEmpty(resourceDOS2)){
                        roleResourceDO.setResourceName(resourceDOS2.get(0).getName());
                    }
                    roleResourceDO.setGmtCreator(sassUserInfo.getAccount());
                    roleResourceDOS.add(roleResourceDO);
                }
                roleResourceService.createRoleResources(roleResourceDOS);

            }

            if (!CollectionUtils.isEmpty(deleteRecourceCodes)) {
                roleResourceService.delete(roleResourceVO.getCode(), deleteRecourceCodes);
            }
        }


        return PortalSingleViewResultVO.success(null);
    }

    @RequirePermission("/permission/role/delete")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    @ResponseBody
    public PortalSingleViewResultVO delete(HttpServletRequest request, @PathVariable Long id) {

        log.info("RoleController.delete,id=={}",id);

        SassUserInfo sassUserInfo = this.getSassUser(request);

        int n = roleService.deleteRole(id);


        return PortalSingleViewResultVO.result(null, n != 1, "操作失败");
    }


}
