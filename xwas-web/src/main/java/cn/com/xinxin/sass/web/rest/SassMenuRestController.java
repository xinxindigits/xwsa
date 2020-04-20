package cn.com.xinxin.sass.web.rest;

import cn.com.xinxin.sass.api.enums.SassResultCodeEnum;
import cn.com.xinxin.sass.auth.model.SassUserInfo;
import cn.com.xinxin.sass.auth.web.AclController;
import cn.com.xinxin.sass.biz.service.UserService;
import cn.com.xinxin.sass.common.enums.SassBizResultCodeEnum;
import cn.com.xinxin.sass.repository.model.ResourceDO;
import cn.com.xinxin.sass.web.form.OrganizationForm;
import cn.com.xinxin.sass.web.form.ResourceForm;
import cn.com.xinxin.sass.web.utils.TreeResultUtil;
import cn.com.xinxin.sass.web.vo.MenuTreeVO;
import com.google.common.collect.Lists;
import com.xinxinfinance.commons.exception.BusinessException;
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
@RequestMapping(value = "/menu",produces = "application/json; charset=UTF-8")
public class SassMenuRestController extends AclController {


    private static final Logger log = LoggerFactory.getLogger(SassMenuRestController.class);


    @Autowired
    private UserService userService;


    /**
     * 用户可以看到的菜单列表
     * @param request
     * @return
     */
    @RequestMapping(value = "/routes",method = RequestMethod.GET)
    @ResponseBody
    public Object listAllUserMenus(HttpServletRequest request){

        SassUserInfo sassUserInfo = this.getSassUser(request);

        String userAccount = sassUserInfo.getAccount();

        // 获取到用户的菜单资源权限等
        List<ResourceDO> resourceDOList = this.userService.findMenusByAccount(userAccount);

        if(CollectionUtils.isEmpty(resourceDOList)){
            throw new BusinessException(SassBizResultCodeEnum.DATA_NOT_EXIST,"权限不对，无菜单数据清设置权限"
                    ,"权限不对，无菜单数据清设置权限");
        }

        // 组装必要的参数
        List<MenuTreeVO> menuResourceVOList = Lists.newArrayList();
        resourceDOList.stream().forEach(
                resourceDO -> {
                    MenuTreeVO menuTreeVO = new MenuTreeVO();
                    menuTreeVO.setText(resourceDO.getName());
                    menuTreeVO.setParentId(String.valueOf(resourceDO.getParentId()));
                    menuTreeVO.setId(String.valueOf(resourceDO.getId()));
                    menuTreeVO.setCode(resourceDO.getCode());
                    menuTreeVO.setUrl(resourceDO.getUrl());
                    menuTreeVO.setHref(resourceDO.getUrl());
                    menuTreeVO.setOrder(0);
                    menuResourceVOList.add(menuTreeVO);
                }
        );


        List<MenuTreeVO> menus = TreeResultUtil.build(menuResourceVOList);
        // 返回数据
        return menus;
    }


}
