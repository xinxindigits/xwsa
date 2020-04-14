package cn.com.xinxin.sass.web.rest;

import cn.com.xinxin.sass.session.utils.JWTUtil;
import cn.com.xinxin.sass.web.controller.UserController;
import cn.com.xinxin.sass.web.convert.PortalFormConvert;
import cn.com.xinxin.sass.web.form.UserForm;
import cn.com.xinxin.sass.web.form.UserLoginForm;
import cn.com.xinxin.sass.biz.service.UserService;
import cn.com.xinxin.sass.biz.util.PasswordUtils;
import cn.com.xinxin.sass.repository.model.UserDO;
import cn.com.xinxin.sass.web.vo.UserTokenVO;
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
@RequestMapping(produces = "application/json; charset=UTF-8")
public class SassUserLoginRestController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;


    @RequestMapping(value = "/register",method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public Object register(HttpServletRequest request, @RequestBody UserForm userForm){

        log.info("UserController.create, userForm = {}", userForm);

        UserDO userDO = PortalFormConvert.convertUserForm2UserDO(userForm);

        int result = userService.createUser(userDO);

        return result;

    }

    @RequestMapping(value = "/auth",method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
    public Object login(HttpServletRequest request, @RequestBody UserLoginForm userLoginForm){

        String userAccount = userLoginForm.getAccount();

        String password = userLoginForm.getPassword();

        UserDO userDO = userService.findByUserAccount(userAccount);

        String ecnryptPassword = PasswordUtils.encryptPassword(userDO.getAccount(),
                userDO.getSalt(), password);

        if(ecnryptPassword.equals(userDO.getPassword())){
            // 登陆成功, 返回token
            String token = getToken(userAccount, userDO.getPassword());
            UserTokenVO userTokenVO = new UserTokenVO();
            userTokenVO.setAccount(userAccount);
            userTokenVO.setToken(token);
            return userTokenVO;
        }else{
            // 登陆失败
            throw new BusinessException(CommonResultCode.ILLEGAL_ARGUMENT,"登陆失败","登陆失败");
        }
    }

    @RequestMapping(value = "/unauthorized",method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
    public Object unauthorized(HttpServletRequest request){

        return CommonResultCode.ILLEGAL_ARGUMENT;
    }



    private String  getToken(String userName, String userPasswd){
         //TODO: 缓存设置tokent
         return JWTUtil.sign(userName,userPasswd);
    }

}
