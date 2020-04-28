package cn.com.xinxin.sass.web.rest;

import cn.com.xinxin.sass.auth.web.AclController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: zhouyang
 * @created: 27/04/2020.
 * @updater:
 * @description: 微信客户信息相关的功能管理接口
 */
@RestController
@RequestMapping(value = "/wechat/custmomer",produces = "application/json; charset=UTF-8")
public class WechatCustomerRestController extends AclController {


}
