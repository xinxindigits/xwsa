package cn.com.xinxin.sass.web.controller;

import cn.com.xinxin.oplog.client.enums.AppProductEnum;
import cn.com.xinxin.oplog.client.enums.OperationTypeEnum;
import cn.com.xinxin.sass.biz.service.UserService;
import cn.com.xinxin.sass.biz.vo.QueryUserConditionVO;
import cn.com.xinxin.sass.common.Page;
import cn.com.xinxin.sass.common.enums.BizResultCodeEnum;
import cn.com.xinxin.sass.repository.model.UserDO;
import cn.com.xinxin.sass.auth.annotation.RequirePermission;
import cn.com.xinxin.sass.auth.web.AclController;
import cn.com.xinxin.sass.auth.model.SassUserInfo;
import cn.com.xinxin.sass.web.convert.PortalFormConvert;
import cn.com.xinxin.sass.web.form.ModifyPasswordForm;
import cn.com.xinxin.sass.web.form.ResetPasswordForm;
import cn.com.xinxin.sass.web.form.UserForm;
import cn.com.xinxin.sass.web.utils.PortalOplogUtil;
import com.xinxinfinance.commons.exception.BusinessException;
import com.xinxinfinance.commons.portal.view.result.PortalPageViewResultVO;
import com.xinxinfinance.commons.portal.view.result.PortalSingleViewResultVO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by dengyunhui on 2018/4/26
 **/
@Controller
@RequestMapping("/permission/user")
public class UserController extends AclController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @RequirePermission("/permission/user")
    @RequestMapping(method = RequestMethod.GET)
    public String user(HttpServletRequest request, Model model){
        return "user/user";
    }

    @RequirePermission("/permission/user/list")
    @RequestMapping(method = RequestMethod.POST,value = "/list")
    @ResponseBody
    public PortalPageViewResultVO list(HttpServletRequest request, @RequestParam Integer start,
                                       @RequestParam Integer limit, @RequestParam Integer pageIndex,
                                       @RequestParam String no, @RequestParam String name){

        log.info("UserController.list,limit:{},pageIndex:{},no:{},name:{}",limit,pageIndex,no,name);

        Page page = new Page();
        page.setPageSize(limit);
        page.setPageNumber(pageIndex);
        QueryUserConditionVO queryUserConditionVO = new QueryUserConditionVO();
        queryUserConditionVO.setNo(no);
        queryUserConditionVO.setName(name);
        Page result = userService.findByConditionPage(page,queryUserConditionVO);

        PortalPageViewResultVO portalPageViewResultVO = new PortalPageViewResultVO();
        portalPageViewResultVO.setPageNumber(result.getPageNumber());
        portalPageViewResultVO.setPageSize(result.getPageSize());
        portalPageViewResultVO.setRows(result.getRows());
        portalPageViewResultVO.setTotal(result.getTotal());
        return portalPageViewResultVO;
    }

    @RequirePermission("/permission/user/create")
    @RequestMapping(value = "/create",method = RequestMethod.POST)
    @ResponseBody
    public PortalSingleViewResultVO create(HttpServletRequest request, @RequestBody UserForm userForm){

        log.info("UserController.create, userForm = {}", userForm);

        // 必要的参数验证
        this.checkUserFormParameter(userForm);

        SassUserInfo sassUserInfo = this.getSassUser(request);

        UserDO userDO = PortalFormConvert.convertUserForm2UserDO(userForm);

        int result = userService.createUser(userDO);


        return PortalSingleViewResultVO.result(null,result != 1,"操作失败");

    }

    @RequestMapping(value = "/findById/{id}",method = RequestMethod.GET)
    @ResponseBody
    public PortalSingleViewResultVO findById(HttpServletRequest request, @PathVariable Long id){
        log.info("UserController.findById,id:{}" , id);
        UserDO userDO = userService.findById(id);
        return PortalSingleViewResultVO.success(userDO);
    }

    @RequirePermission("/permission/user/modify")
    @RequestMapping(value = "/modify",method = RequestMethod.POST)
    @ResponseBody
    public PortalSingleViewResultVO modify(HttpServletRequest request,@RequestBody UserForm userForm){

        log.info("UserController.modify,userDO:{}",userForm);

        //参数校验
        this.checkUserFormParameter(userForm);

        SassUserInfo sassUserInfo = this.getSassUser(request);

        UserDO updateUserDO = PortalFormConvert.convertUserForm2UserDO(userForm);

        Boolean updateResult = userService.updateUser(updateUserDO);

        return PortalSingleViewResultVO.result(null,!updateResult,"操作失败");
    }

    @RequirePermission("/permission/user/delete")
    @RequestMapping(value = "/delete/{id}",method = RequestMethod.GET)
    @ResponseBody
    public PortalSingleViewResultVO delete(HttpServletRequest request,@PathVariable Long id){

        log.info("UserController.delete,userId = {}", id);

        SassUserInfo sassUserInfo = this.getSassUser(request);

        Boolean rst = userService.delete(id);


        return PortalSingleViewResultVO.result(null,!rst,"操作失败");
    }


    /**
     * 必要的参数校验
     * @param userForm
     */
    private void checkUserFormParameter(final UserForm userForm){

        // 必要的参数验证
        if(null == userForm){
            throw new BusinessException(BizResultCodeEnum.PARAMETER_NULL,"用户信息参数不能为空");
        }

        if(StringUtils.isEmpty(userForm.getAccount())){
            throw new BusinessException(BizResultCodeEnum.PARAMETER_NULL,"用户账户信息参数不能为空");
        }
    }

    @RequestMapping(value = "/resetpasswd",method = RequestMethod.POST)
    @ResponseBody
    public PortalSingleViewResultVO resetPasswd(HttpServletRequest request, @RequestBody ResetPasswordForm resetPasswordForm){

        log.info("UserController.resetPasswd,userId = {}", resetPasswordForm.getId());

        SassUserInfo sassUserInfo = this.getSassUser(request);

        userService.resetPassword(resetPasswordForm.getId(),resetPasswordForm.getNewPasswd(), sassUserInfo.getAccount());


        return PortalSingleViewResultVO.result(null,false,"操作失败");
    }

    @RequestMapping(value = "/modifyPassword",method = RequestMethod.POST)
    @ResponseBody
    public PortalSingleViewResultVO modifyPassword(HttpServletRequest request, @RequestBody ModifyPasswordForm modifyPasswordForm){

        SassUserInfo sassUserInfo = this.getSassUser(request);

        log.info("UserController.modifyPassword,userId = {}", sassUserInfo.getId());

        try {
            userService.modifyPassword(sassUserInfo.getId(),modifyPasswordForm.getOriginPasswd(),modifyPasswordForm.getNewPasswd(), sassUserInfo.getAccount());
        }catch (BusinessException e){
            log.error("UserController.modifyPassword 发生异常",e);
            return PortalSingleViewResultVO.result(null,true,e.getAlertMessage());
        }catch (Exception e){
            log.error("UserController.modifyPassword 发生异常",e);
            return PortalSingleViewResultVO.result(null,true,"操作失败");
        }



        return PortalSingleViewResultVO.result(null,false,"操作失败");
    }

}
