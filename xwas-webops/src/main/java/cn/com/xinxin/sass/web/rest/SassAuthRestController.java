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
import cn.com.xinxin.sass.auth.repository.UserAclTokenRepository;
import cn.com.xinxin.sass.auth.utils.HttpRequestUtil;
import cn.com.xinxin.sass.biz.service.OplogService;
import cn.com.xinxin.sass.common.enums.SassBizResultCodeEnum;
import cn.com.xinxin.sass.auth.utils.JWTUtil;
import cn.com.xinxin.sass.auth.utils.CommonHttpRequestUtil;
import cn.com.xinxin.sass.repository.model.OplogDOWithBLOBs;
import cn.com.xinxin.sass.repository.model.ResourceDO;
import cn.com.xinxin.sass.repository.model.RoleDO;
import cn.com.xinxin.sass.tenant.TenantIdContext;
import cn.com.xinxin.sass.web.convert.SassFormConvert;
import cn.com.xinxin.sass.web.form.UserForm;
import cn.com.xinxin.sass.web.form.UserLoginForm;
import cn.com.xinxin.sass.biz.service.UserService;
import cn.com.xinxin.sass.biz.util.PasswordUtils;
import cn.com.xinxin.sass.repository.model.UserDO;
import cn.com.xinxin.sass.web.utils.KaptchaUtils;
import cn.com.xinxin.sass.web.vo.UserTokenVO;
import com.alibaba.fastjson.JSONObject;
import com.xinxinfinance.commons.exception.BusinessException;
import com.xinxinfinance.commons.util.BaseConvert;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author: zhouyang
 * @created: 14/04/2020.
 * @updater:
 * @description:
 */

@RestController
@RequestMapping(produces = "application/json; charset=UTF-8")
public class SassAuthRestController {

    private static final Logger log = LoggerFactory.getLogger(SassAuthRestController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private UserAclTokenRepository userAclTokenRepository;


    @Autowired
    private OplogService oplogService;


    @RequestMapping(value = "/register",method = RequestMethod.POST)
    @ResponseBody
    public Object register(HttpServletRequest request, @RequestBody UserForm userForm){

        log.info("UserController.create, userForm = {}", userForm);

        UserDO userDO = SassFormConvert.convertUserForm2UserDO(userForm);

        int result = userService.createUser(userDO);

        return result;

    }

    //@SysLog("用户登录操作")
    @RequestMapping(value = "/auth",method = RequestMethod.POST)
    public Object login(HttpServletRequest request,
                        HttpServletResponse response,
                        @RequestBody UserLoginForm userLoginForm){


        if (!KaptchaUtils.checkVerifyCode(request)) {
            throw new BusinessException(SassBizResultCodeEnum.ILLEGAL_PARAMETER,"验证码有误");
        }

        String userAccount = userLoginForm.getAccount();

        String password = userLoginForm.getPassword();

        // 执行登陆之前先清除掉用户登陆缓存信息

        UserDO userDO = userService.findByUserAccount(userAccount);

        if (userDO == null) {
            throw new BusinessException(SassBizResultCodeEnum.DATA_NOT_EXIST, "用户信息不存在","用户信息不存在");

        }

        //设置租户ID
        TenantIdContext.set(userDO.getTenantId());

        String ecnryptPassword = PasswordUtils.encryptPassword(userDO.getAccount(),
                userDO.getSalt(), password);

        if(ecnryptPassword.equals(userDO.getPassword())){
            // 登录成功, 返回token
            // TODO: 登录成功之后需要将用户的信息缓存起来，方便查询读写
            String token = getToken(userAccount, userDO.getPassword(),userDO.getTenantId());
            UserTokenVO userTokenVO = new UserTokenVO();
            userTokenVO.setAccount(userAccount);
            userTokenVO.setToken(token);
            userTokenVO.setName(userDO.getName());
            userTokenVO.setTenantId(userDO.getTenantId());

            // 获取必要的用户信息,缓存到redis
            SassUserInfo sassUserInfo = BaseConvert.convert(userDO,SassUserInfo.class);
            sassUserInfo.setDevice(HttpRequestUtil.getRequestDevice(request));
            sassUserInfo.setIp(HttpRequestUtil.getIpAddress(request));

            //设置用户的租户ID
            sassUserInfo.setTenantId(userDO.getTenantId());

            List<RoleDO> roleDOList = userService.findRolesByAccount(userAccount);

            if (!CollectionUtils.isEmpty(roleDOList)){
                Set<String> roleCodes = new HashSet<>(roleDOList.size());
                roleDOList.forEach(roleDO -> roleCodes.add(roleDO.getCode()));
                sassUserInfo.setRoles(roleCodes);
            }

            List<ResourceDO> resourceDOS = userService.findResourcesByAccount(userAccount);
            if (!CollectionUtils.isEmpty(resourceDOS)){
                Set<String> permissionUrls = new HashSet<>(resourceDOS.size());
                resourceDOS.forEach(resourceDO -> permissionUrls.add(resourceDO.getAuthority()));
                sassUserInfo.setStringPermissions(permissionUrls);
            }
            // 设置用户的token以及角色，权限等信息缓存
            userAclTokenRepository.setSassUserByUserAccount(userAccount,sassUserInfo);
            userAclTokenRepository.setSassUserTokenCache(userAccount,token);
            response.setHeader(JWTUtil.TOKEN_NAME,token);
            response.setHeader("Access-control-Allow-Origin", request.getHeader("Origin"));
            response.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
            response.setHeader("Access-Control-Allow-Headers", request.getHeader("Access-Control-Request-Headers"));
            response.setHeader("access-control-expose-headers", "XToken");



            // 记录登陆日志
            //保存日志
            OplogDOWithBLOBs oplog = new OplogDOWithBLOBs();
            if (request != null) {
                //获取用户ip地址
                oplog.setOperation("用户登录操作");
                oplog.setExtension("cn.com.xinxin.sass.web.rest.SassAuthRestController.login");
                oplog.setMethod("cn.com.xinxin.sass.web.rest.SassAuthRestController.login");
                oplog.setParams(JSONObject.toJSONString(userLoginForm));
                oplog.setIp(CommonHttpRequestUtil.getIpAddress(request));
                oplog.setUa(CommonHttpRequestUtil.getRequestDevice(request));
                oplog.setUri(CommonHttpRequestUtil.getPath(request.getRequestURI()));
                oplog.setHttpMethod(request.getMethod());
                oplog.setUa(StringUtils.substring(request.getHeader("user-agent"), 0, 256));
                oplog.setAccount(userAccount);
                oplog.setTenantId(sassUserInfo.getTenantId());
                oplog.setGmtCreator(userAccount);
                oplog.setGmtUpdater(userAccount);
                //调用service保存SysLog实体类到数据库
                oplogService.createOplog(oplog);
            }

            // 设置基本的content
            // 完成请求移除设置
            TenantIdContext.remove();
            return userTokenVO;

        }else{
            // 登录失败
            throw new BusinessException(SassBizResultCodeEnum.INVALID_TOKEN, "登录失败,用户名或者密码错误","登录失败,用户名或者密码错误");
        }
    }


    private String  getToken(String userName, String userPasswd, String tenantId){
         //TODO: 缓存设置tokent
         return JWTUtil.sign(userName, userPasswd, tenantId);
    }


    @RequestMapping(value = "/unauthorized",method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
    public Object unauthorized(HttpServletRequest request){
        log.info("无效登录口令，请重新登录");
        throw new BusinessException(SassBizResultCodeEnum.INVALID_TOKEN,"无效登录口令","无效登录口令，请重新登录");

    }


}
