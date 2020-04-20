package cn.com.xinxin.sass.web.rest;

import cn.com.xinxin.sass.auth.model.SassUserInfo;
import cn.com.xinxin.sass.auth.web.AclController;
import cn.com.xinxin.sass.biz.service.ResourceService;
import cn.com.xinxin.sass.biz.service.UserService;
import cn.com.xinxin.sass.common.enums.SassBizResultCodeEnum;
import cn.com.xinxin.sass.common.model.PageResultVO;
import cn.com.xinxin.sass.repository.model.ResourceDO;
import cn.com.xinxin.sass.web.convert.SassFormConvert;
import cn.com.xinxin.sass.web.form.ResourceForm;
import cn.com.xinxin.sass.web.utils.TreeResultUtil;
import cn.com.xinxin.sass.web.vo.MenuTreeVO;
import com.google.common.collect.Lists;
import com.xinxinfinance.commons.exception.BusinessException;
import com.xinxinfinance.commons.portal.view.result.PortalPageViewResultVO;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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


    @RequestMapping(value = "/list",method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions("/resource/list")
    public Object ListResource(HttpServletRequest request,
                               @RequestParam Integer pageSize,
                               @RequestParam Integer pageIndex,
                               @RequestParam String code,
                               @RequestParam String resourceType,
                               @RequestParam String name,
                               @RequestParam String url,
                               @RequestParam Boolean root){

        //SassUserInfo sassUserInfo = this.getSassUser(request);
        //String userAccount = sassUserInfo.getAccount();

        log.info("ResourceController.list,pageSize={},pageIndex={},code={},resourceType={},name={},url={},root={}",
                pageSize,pageIndex,code,resourceType,name,url,root);

        PageResultVO pageVo = new PageResultVO();
        pageVo.setPageSize(pageSize);
        pageVo.setPageNumber(pageIndex);

        ResourceDO condition = new ResourceDO();
        condition.setResourceType(resourceType);
        condition.setCode(code);
        condition.setName(name);
        condition.setUrl(url);
        if (root != null){
            condition.setRoot(root);
        }

        PageResultVO result = resourceService.findByConditionPage(pageVo,condition);

        return request;
    }

    @RequestMapping(value = "/create",method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions("/resource/create")
    public Object createResource(HttpServletRequest request,
                                   @RequestBody ResourceForm resourceForm){

        SassUserInfo sassUserInfo = this.getSassUser(request);
        String userAccount = sassUserInfo.getAccount();

        // FIXME: 看是否需要判断资源以及存在
        ResourceDO resourceDO = SassFormConvert.convertResourceForm2ResourceDO(resourceForm);
        resourceDO.setGmtCreator(userAccount);
        resourceDO.setGmtUpdater(userAccount);
        ResourceDO resultDO = resourceService.createResource(resourceDO);

        if(null == resultDO){
            return SassBizResultCodeEnum.SUCCESS.getAlertMessage();
        }else{
            throw new BusinessException(SassBizResultCodeEnum.FAIL,"创建资源出错","清检查参数是否正确");
        }
    }

    @RequestMapping(value = "/update",method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions("/resource/update")
    public Object updateResource(HttpServletRequest request,
                             @RequestBody ResourceForm resourceForm){

        SassUserInfo sassUserInfo = this.getSassUser(request);

        String userAccount = sassUserInfo.getAccount();

        ResourceDO resourceDO = SassFormConvert.convertResourceForm2ResourceDO(resourceForm);

        resourceDO.setGmtUpdater(userAccount);

        int resultCount = resourceService.updateResource(resourceDO);

        return resultCount;

    }


    @RequestMapping(value = "/delete",method = RequestMethod.DELETE)
    @ResponseBody
    @RequiresPermissions("/resource/delete")
    public Object deleteResource(HttpServletRequest request,
                                 @RequestParam String id){

        SassUserInfo sassUserInfo = this.getSassUser(request);

        String userAccount = sassUserInfo.getAccount();

        boolean result = resourceService.deleteById(Long.valueOf(id));

        return result;

    }



}
