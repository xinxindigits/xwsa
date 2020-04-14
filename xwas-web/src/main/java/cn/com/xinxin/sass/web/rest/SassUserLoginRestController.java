package cn.com.xinxin.sass.web.rest;

import cn.com.xinxin.sass.session.utils.JWTUtil;
import cn.com.xinxin.sass.web.controller.UserController;
import cn.com.xinxin.sass.web.convert.PortalFormConvert;
import cn.com.xinxin.sass.web.form.UserForm;
import cn.com.xinxin.sass.web.form.UserLoginForm;
import cn.com.xinxin.sass.biz.service.UserService;
import cn.com.xinxin.sass.biz.util.PasswordUtils;
import cn.com.xinxin.sass.repository.model.UserDO;
import com.xinxinfinance.commons.exception.BusinessException;
import com.xinxinfinance.commons.result.CommonResultCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: zhouyang
 * @created: 14/04/2020.
 * @updater:
 * @description:
 */

@RestController
public class SassUserLoginRestController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;


    @PostMapping("/register")
    @ResponseBody
    public Object register(HttpServletRequest request, @RequestBody UserForm userForm){

        log.info("UserController.create, userForm = {}", userForm);

        UserDO userDO = PortalFormConvert.convertUserForm2UserDO(userForm);

        int result = userService.createUser(userDO);

        return result;

    }

    @PostMapping("/auth")
    public Object login(@RequestBody UserLoginForm userLoginForm){

        String userName = userLoginForm.getUserName();

        String password = userLoginForm.getPassword();

        UserDO userDO = userService.findByUserName(userName);

        String ecnryptPassword = PasswordUtils.encryptPassword(userDO.getAccount(),
                userDO.getSalt(), password);

        if(ecnryptPassword.equals(userDO.getPassword())){
            // 登陆成功
            String token = getToken(userName, userDO.getPassword());

            return token;

        }else{
            // 登陆失败
            throw new BusinessException(CommonResultCode.ILLEGAL_ARGUMENT,"登陆失败","登陆失败");
        }
    }

    @GetMapping("/unauthorized")
    public Object unauthorized(){

        return CommonResultCode.ILLEGAL_ARGUMENT;
    }

    private String  getToken(String userName, String userPasswd){

         //TODO: 缓存设置tokent
         return JWTUtil.sign(userName,userPasswd);
    }


}
