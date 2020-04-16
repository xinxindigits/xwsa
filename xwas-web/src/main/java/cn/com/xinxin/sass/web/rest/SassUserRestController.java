package cn.com.xinxin.sass.web.rest;

import cn.com.xinxin.sass.auth.annotation.RequirePermission;
import cn.com.xinxin.sass.auth.model.SassUserInfo;
import cn.com.xinxin.sass.biz.service.UserService;
import cn.com.xinxin.sass.common.enums.SassBizResultCodeEnum;
import cn.com.xinxin.sass.repository.model.UserDO;
import cn.com.xinxin.sass.auth.web.AclController;
import cn.com.xinxin.sass.web.controller.UserController;
import cn.com.xinxin.sass.web.form.UserForm;
import com.xinxinfinance.commons.exception.BusinessException;
import com.xinxinfinance.commons.util.BaseConvert;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
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
    @RequiresPermissions("/user/query")
    public Object queryUserByAccount(HttpServletRequest request,@PathVariable String account){

        SassUserInfo sassUserInfo = this.getSassUser(request);

        log.info("queryUserByAccount, account = {}",account);

        UserDO userDO = this.userService.findByUserAccount(account);

        UserForm result = BaseConvert.convert(userDO,UserForm.class);

        return result;

    }

    @RequestMapping(value = "/update",method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions("/user/update")
    public Object updateUserInfo(HttpServletRequest request, @RequestBody UserForm userForm){

        if(null == userForm){
            throw new BusinessException(SassBizResultCodeEnum.PARAMETER_NULL,"用户信息不能为空","用户信息不能为空");
        }

        // 更新用户信息不能更新用户密码以及账号信息，如果需要更新密码，走密码重置的方法即可
        String userAccount = userForm.getAccount();
        // 查询已经存在的用户信息
        UserDO userDO = this.userService.findByUserAccount(userAccount);

        if(StringUtils.isNotEmpty(userForm.getName())){
            userDO.setName(userForm.getName());
        }
        userDO.setGender(Byte.valueOf(String.valueOf(userForm.getGender())));

        boolean result = this.userService.updateUser(userDO);
        return result;
    }

}
