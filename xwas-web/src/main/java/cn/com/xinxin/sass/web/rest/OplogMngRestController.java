package cn.com.xinxin.sass.web.rest;

import cn.com.xinxin.sass.auth.web.AclController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: zhouyang
 * @created: 27/04/2020.
 * @updater:
 * @description: 操作日志记录相关的查询接口
 */

@RestController
@RequestMapping(value = "/user",produces = "application/json; charset=UTF-8")
public class OplogMngRestController extends AclController {


}
