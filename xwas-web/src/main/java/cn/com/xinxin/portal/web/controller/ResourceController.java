package cn.com.xinxin.portal.web.controller;

import cn.com.xinxin.oplog.client.enums.AppProductEnum;
import cn.com.xinxin.oplog.client.enums.OperationTypeEnum;
import cn.com.xinxin.sass.biz.service.ResourceService;
import cn.com.xinxin.sass.biz.service.RoleResourceService;
import cn.com.xinxin.sass.common.Page;
import cn.com.xinxin.sass.repository.model.ResourceDO;
import cn.com.xinxin.portal.session.annotation.RequirePermission;
import cn.com.xinxin.portal.session.controller.AclController;
import cn.com.xinxin.portal.session.model.PortalUser;
import cn.com.xinxin.portal.web.convert.PortalFormConvert;
import cn.com.xinxin.portal.web.form.ResourceForm;
import cn.com.xinxin.portal.web.utils.PortalOplogUtil;
import cn.com.xinxin.portal.web.utils.TreeUtil;
import cn.com.xinxin.portal.web.vo.TreeVO;
import com.xinxinfinance.commons.portal.view.result.PortalPageViewResultVO;
import com.xinxinfinance.commons.portal.view.result.PortalSingleViewResultVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by dengyunhui on 2018/5/4
 **/
@Controller
@RequestMapping("/permission/resource")
public class ResourceController extends AclController {

    private static final Logger log = LoggerFactory.getLogger(ResourceController.class);

    @Autowired
    private ResourceService resourceService;

    @Autowired
    private RoleResourceService roleResourceService;

    @RequirePermission("/permission/resource")
    @RequestMapping(method = RequestMethod.GET)
    public String index(HttpServletRequest request, Model model){
        return "resource/resource";
    }

    @RequestMapping(method = RequestMethod.POST,value = "list")
    @ResponseBody
    @RequirePermission("/permission/resource/list")
    public PortalPageViewResultVO list(HttpServletRequest request,
                                       @RequestParam Integer limit,
                                       @RequestParam Integer pageIndex,
                                       @RequestParam String code,
                                       @RequestParam String resourceType,
                                       @RequestParam String name,
                                       @RequestParam String url,
                                       @RequestParam Boolean root){
        log.info("ResourceController.list,limit:{},pageIndex:{},code:{}," +
            "resourceType:{},name:{},url:{},root:{}",
            limit,pageIndex,code,resourceType,name,url,root);

        Page page = new Page();
        page.setPageSize(limit);
        page.setPageNumber(pageIndex);

        ResourceDO condition = new ResourceDO();
        condition.setResourceType(resourceType);
        condition.setCode(code);
        condition.setName(name);
        condition.setUrl(url);
        if (root != null){
            condition.setRoot(root);
        }

        Page result = resourceService.findByConditionPage(page,condition);
        PortalPageViewResultVO portalPageViewResultVO = new PortalPageViewResultVO();
        portalPageViewResultVO.setPageNumber(result.getPageNumber());
        portalPageViewResultVO.setPageSize(result.getPageSize());
        portalPageViewResultVO.setRows(result.getRows());
        portalPageViewResultVO.setTotal(result.getTotal());
        log.info("result:{}",result);
        return portalPageViewResultVO;
    }

    @RequirePermission("/permission/resource/create")
    @RequestMapping(value = "/create",method = RequestMethod.POST)
    @ResponseBody
    public PortalSingleViewResultVO create(HttpServletRequest request, @RequestBody ResourceForm resourceForm){

        log.info("ResourceController.create,resourceForm:{}",resourceForm);

        ResourceDO exist = resourceService.findByResourceCode(resourceForm.getCode());
        if (exist != null){
            return PortalSingleViewResultVO.result(null,true,"资源编码已经存在");
        }

        PortalUser portalUser = this.getPortalUser(request);

        ResourceDO resourceDO = PortalFormConvert.convertResourceForm2ResourceDO(resourceForm);
        resourceDO.setGmtCreator(portalUser.getAccount());
        resourceDO.setGmtUpdater(portalUser.getAccount());
        ResourceDO resultDO = resourceService.createResource(resourceDO);


        PortalOplogUtil.logReq(
            AppProductEnum.XPORTAL_ADD_RESOURCE_INFO,
            OperationTypeEnum.ADD,
            portalUser.getAccount(),
            portalUser.getNo(),
            resourceForm.getCode(),
            portalUser.getIp(),
            portalUser.getDevice(),
            resourceForm);

        return PortalSingleViewResultVO.result(null,!(resultDO != null),"操作失败");
    }

    @RequestMapping(value = "/findById/{id}",method = RequestMethod.GET)
    @ResponseBody
    public PortalSingleViewResultVO findById(HttpServletRequest request,@PathVariable Long id){
        log.info("ResourceController.findById,id:{}",id);

        ResourceDO resourceDO = resourceService.findById(id);
        return PortalSingleViewResultVO.success(resourceDO);
    }

    @RequirePermission("/permission/resource/update")
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    @ResponseBody
    public PortalSingleViewResultVO update(HttpServletRequest request, @RequestBody ResourceForm resourceForm){

        log.info("ResourceController.update,resourceForm:{}",resourceForm);

        PortalUser portalUser = this.getPortalUser(request);

        ResourceDO resourceDO = PortalFormConvert.convertResourceForm2ResourceDO(resourceForm);

        resourceDO.setGmtUpdater(portalUser.getAccount());

        int resultCount = resourceService.updateResource(resourceDO);

        PortalOplogUtil.logReq(
            AppProductEnum.XPORTAL_MODIFY_RESOURCE_INFO,
            OperationTypeEnum.UPDATE,
            portalUser.getAccount(),
            portalUser.getNo(),
            resourceDO.getCode(),
            portalUser.getIp(),
            portalUser.getDevice(),
            resourceForm);

        return PortalSingleViewResultVO.result(null,!(resultCount == 1),"操作失败");
    }

    @RequirePermission("/permission/resource/delete")
    @RequestMapping(value = "/delete/{id}",method = RequestMethod.GET)
    @ResponseBody
    public PortalSingleViewResultVO delete(HttpServletRequest request,@PathVariable Long id){

        log.info("ResourceController.delete,id:{}",id);

        PortalUser portalUser = this.getPortalUser(request);

        Boolean result = resourceService.deleteById(id);

        PortalOplogUtil.logReq(
            AppProductEnum.XPORTAL_DELETE_RESOURCE_INFO,
            OperationTypeEnum.DELETE,
            portalUser.getAccount(),
            portalUser.getNo(),
            Long.toString(id),
            portalUser.getIp(),
            portalUser.getDevice(),
            id);


        return PortalSingleViewResultVO.result(null,!result,"操作失败");
    }

    @RequestMapping(value = "/checkedTree/{code}",method = RequestMethod.GET)
    @ResponseBody
    public PortalSingleViewResultVO checkedTree(@PathVariable String code){
        log.info("ResourceController.checkedTree,code:{}",code);

        List<ResourceDO> resourceDOS = roleResourceService.findResources(code);
        List<String> resourceCodes = null;
        if (!CollectionUtils.isEmpty(resourceDOS)){
            resourceCodes = resourceDOS.stream().map(ResourceDO::getCode).collect(Collectors.toList());
        }

        List<ResourceDO> resourceDOList = resourceService.findAllResources();
        List<TreeVO> trees = new ArrayList<>();
        List<TreeVO> treeVOS = new ArrayList<>();

        if (!CollectionUtils.isEmpty(resourceDOList)){
            for (ResourceDO  resourceDO : resourceDOList) {
                TreeVO treeVO = new TreeVO();
                treeVO.setText(resourceDO.getName());
                treeVO.setParentId(resourceDO.getParentId() + "");
                treeVO.setId(resourceDO.getId() + "");
                treeVO.setCode(resourceDO.getCode());
                treeVO.setUrl(resourceDO.getUrl());
                treeVO.setChecked(false);

                if (!CollectionUtils.isEmpty(resourceCodes) &&
                        resourceCodes.contains(resourceDO.getCode())){
                    treeVO.setChecked(true);
                }

                trees.add(treeVO);
            }

            treeVOS = TreeUtil.build2(trees);
        }

        log.info("ResourceController.checkedTree,tree:{}",treeVOS);
        return PortalSingleViewResultVO.success(treeVOS);
    }

    @RequestMapping(value = "/plainTree",method = RequestMethod.GET)
    @ResponseBody
    public PortalSingleViewResultVO plainTree(){
        List<ResourceDO> resourceDOList = resourceService.findAllResources();
        List<TreeVO> trees = new ArrayList<>();
        List<TreeVO> treeVOS = new ArrayList<>();

        if (!CollectionUtils.isEmpty(resourceDOList)){
            for (ResourceDO  resourceDO : resourceDOList) {
                TreeVO treeVO = new TreeVO();
                treeVO.setText(resourceDO.getName());
                treeVO.setParentId(resourceDO.getParentId() + "");
                treeVO.setId(resourceDO.getId() + "");
                treeVO.setCode(resourceDO.getCode());
                treeVO.setUrl(resourceDO.getUrl());
                treeVO.setChecked(false);
                trees.add(treeVO);
            }

            treeVOS = TreeUtil.build2(trees);
        }

        log.info("ResourceController.plainTree,tree:{}",treeVOS);
        return PortalSingleViewResultVO.success(treeVOS);
    }

}
