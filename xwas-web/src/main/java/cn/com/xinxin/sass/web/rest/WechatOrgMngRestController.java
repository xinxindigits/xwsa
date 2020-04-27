package cn.com.xinxin.sass.web.rest;

import cn.com.xinxin.sass.auth.web.AclController;
import cn.com.xinxin.sass.web.form.UserForm;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: zhouyang
 * @created: 27/04/2020.
 * @updater:
 * @description: 微信部门相关的功能管理接口
 */

@RestController
@RequestMapping(value = "/wechat/dept/",produces = "application/json; charset=UTF-8")
public class WechatOrgMngRestController extends AclController {



    @RequestMapping(value = "/list",method = RequestMethod.POST)
    @ResponseBody
    public Object listAllDeptsTree(@RequestBody UserForm userForm, HttpServletRequest request){

        return null;
    }

}
