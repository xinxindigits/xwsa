package cn.com.xinxin.portal.web.controller;

import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by dengyunhui on 2018/4/26
 **/
@Controller
@RequestMapping("/login")
public class LoginController {

    @RequestMapping(method = RequestMethod.POST)
    public String fail(HttpServletRequest req,
                       @RequestParam(FormAuthenticationFilter.DEFAULT_USERNAME_PARAM)String username,
                       Model model){
        String exceptionClassName = (String) req.getAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);

        String error = null;
        if (UnknownAccountException.class.getName().equals(exceptionClassName) ||
                IncorrectCredentialsException.class.getName().equals(exceptionClassName)){
            error = "用户名或密码错误";
        }else if (exceptionClassName != null){
            error = "其他错误：" + exceptionClassName;
        }

        if(error != null){
            model.addAttribute("error",error);
            model.addAttribute(FormAuthenticationFilter.DEFAULT_USERNAME_PARAM,username);
            return "login";
        }else {
            return "redirect:/dashboard";
        }

    }

    @RequestMapping(method = RequestMethod.GET)
    public String login(){
        return "login";
    }
}
