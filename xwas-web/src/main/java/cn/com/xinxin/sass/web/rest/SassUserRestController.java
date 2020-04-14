package cn.com.xinxin.sass.web.rest;

import cn.com.xinxin.sass.biz.service.UserService;
import cn.com.xinxin.sass.repository.model.UserDO;
import cn.com.xinxin.sass.session.controller.AclController;
import cn.com.xinxin.sass.web.controller.UserController;
import cn.com.xinxin.sass.web.form.UserForm;
import com.xinxinfinance.commons.util.BaseConvert;
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
@RequestMapping(value = "/user",produces = "application/json; charset=UTF-8")
public class SassUserRestController extends AclController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);


    @Autowired
    private UserService userService;

    @RequestMapping(value = "/query/{account}",method = RequestMethod.GET)
    @ResponseBody
    public Object queryUserByAccount(HttpServletRequest request,@PathVariable String account){

        log.info("queryUserByAccount, account = {}",account);

        UserDO userDO = this.userService.findByUserAccount(account);

        UserForm result = BaseConvert.convert(userDO,UserForm.class);

        return result;

    }
}
