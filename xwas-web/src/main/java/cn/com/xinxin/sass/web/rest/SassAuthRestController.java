package cn.com.xinxin.sass.web.rest;


import cn.com.xinxin.sass.auth.model.SassUserInfo;
import cn.com.xinxin.sass.auth.repository.UserAclTokenRepository;
import cn.com.xinxin.sass.auth.utils.HttpRequestUtil;
import cn.com.xinxin.sass.common.enums.SassBizResultCodeEnum;
import cn.com.xinxin.sass.auth.utils.JWTUtil;
import cn.com.xinxin.sass.repository.model.ResourceDO;
import cn.com.xinxin.sass.repository.model.RoleDO;
import cn.com.xinxin.sass.web.controller.UserController;
import cn.com.xinxin.sass.web.convert.SassFormConvert;
import cn.com.xinxin.sass.web.form.UserForm;
import cn.com.xinxin.sass.web.form.UserLoginForm;
import cn.com.xinxin.sass.biz.service.UserService;
import cn.com.xinxin.sass.biz.util.PasswordUtils;
import cn.com.xinxin.sass.repository.model.UserDO;
import cn.com.xinxin.sass.web.vo.UserTokenVO;
import com.xinxinfinance.commons.exception.BusinessException;
import com.xinxinfinance.commons.result.CommonResultCode;
import com.xinxinfinance.commons.util.BaseConvert;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private UserAclTokenRepository userAclTokenRepository;


    @RequestMapping(value = "/register",method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public Object register(HttpServletRequest request, @RequestBody UserForm userForm){

        log.info("UserController.create, userForm = {}", userForm);

        UserDO userDO = SassFormConvert.convertUserForm2UserDO(userForm);

        int result = userService.createUser(userDO);

        return result;

    }

    @RequestMapping(value = "/auth",method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
    public Object login(HttpServletRequest request, @RequestBody UserLoginForm userLoginForm){

        String userAccount = userLoginForm.getAccount();

        String password = userLoginForm.getPassword();

        UserDO userDO = userService.findByUserAccount(userAccount);

        if (userDO == null) {
            throw new BusinessException(SassBizResultCodeEnum.DATA_NOT_EXIST, "用户信息不存在","用户信息不存在");

        }

        String ecnryptPassword = PasswordUtils.encryptPassword(userDO.getAccount(),
                userDO.getSalt(), password);

        if(ecnryptPassword.equals(userDO.getPassword())){
            // 登陆成功, 返回token
            // TODO: 登陆成功之后需要将用户的信息缓存起来，方便查询读写
            String token = getToken(userAccount, userDO.getPassword());
            UserTokenVO userTokenVO = new UserTokenVO();
            userTokenVO.setAccount(userAccount);
            userTokenVO.setToken(token);

            // 获取必要的用户信息,缓存到redis
            SassUserInfo sassUserInfo = BaseConvert.convert(userDO,SassUserInfo.class);
            sassUserInfo.setDevice(HttpRequestUtil.getRequestDevice(request));
            sassUserInfo.setIp(HttpRequestUtil.getIpAddress(request));

            List<RoleDO> roleDOList = userService.findRolesByAccount(userAccount);

            if (!CollectionUtils.isEmpty(roleDOList)){
                Set<String> roleCodes = new HashSet<>(roleDOList.size());
                roleDOList.forEach(roleDO -> roleCodes.add(roleDO.getCode()));
                sassUserInfo.setRoles(roleCodes);
            }

            List<ResourceDO> resourceDOS = userService.findResourcesByAccount(userAccount);
            if (!CollectionUtils.isEmpty(resourceDOS)){
                Set<String> permissionUrls = new HashSet<>(resourceDOS.size());
                resourceDOS.forEach(resourceDO -> permissionUrls.add(resourceDO.getUrl()));
                sassUserInfo.setStringPermissions(permissionUrls);
            }
            // 设置用户的token以及角色，权限等信息缓存
            userAclTokenRepository.setSassUserByUserAccount(userAccount,sassUserInfo);
            userAclTokenRepository.setSassUserTokenCache(userAccount,token);

            return userTokenVO;
        }else{
            // 登陆失败
            throw new BusinessException(SassBizResultCodeEnum.INVALID_TOKEN, "登陆失败","登陆失败");
        }
    }


    private String  getToken(String userName, String userPasswd){
         //TODO: 缓存设置tokent
         return JWTUtil.sign(userName,userPasswd);
    }


    @RequestMapping(value = "/unauthorized",method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
    public Object unauthorized(HttpServletRequest request){
        log.info("无效登陆口令，请重新登陆");
        throw new BusinessException(CommonResultCode.REMOTE_ERROR,"无效登陆口令","无效登陆口令，请重新登陆");

    }


}
