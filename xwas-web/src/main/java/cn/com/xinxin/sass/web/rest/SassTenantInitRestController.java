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

import cn.com.xinxin.sass.repository.model.AuthsDO;
import com.xinxinfinance.commons.exception.BusinessException;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
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


    @RequestMapping(value = "/init",method = RequestMethod.GET)
    @ResponseBody
    //@RequiresPermissions(value = {"SASS_TENANT_MNG", "SASS_TENANT_QUERY_LIST"},logical= Logical.OR)
    public Object tenantDataInit(HttpServletRequest request, @Param("tenantId") String tenantId){

        loger.info("SassTenantRestController,tenantDataInit,tenantId={}", tenantId);

        SassUserInfo sassUserInfo = this.getSassUser(request);

        if(StringUtils.isNotEmpty(tenantId)){
            tenantId = sassUserInfo.getTenantId();
        }
        // 参数转换设置
        if(!sassUserInfo.getTenantId().equals(tenantId)){
            throw new BusinessException(SassBizResultCodeEnum.ILLEGAL_PARAMETER,"不能初始化非自己租户的数据");
        }
        // 初始化之前,务必先添加租户信息。初始化数据只能初始化admin的类型账号
        // 1.初始化权限资源
        // 2.初始化角色信息
        // 3.初始化用户信息
        // 4.初始化组织机构
        List<AuthsDO> authsDOList = this.authsService.selectAllAuths();


        return null;
    }

}
