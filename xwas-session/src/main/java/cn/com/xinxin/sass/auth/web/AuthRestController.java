package cn.com.xinxin.sass.auth.web;

import com.xinxinfinance.commons.exception.BusinessException;
import com.xinxinfinance.commons.result.CommonResultCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: zhouyang
 * @created: 15/04/2020.
 * @updater:
 * @description:
 */
@RestController
@RequestMapping(produces = "application/json; charset=UTF-8")
public class AuthRestController {


    private static final Logger log = LoggerFactory.getLogger(AuthRestController.class);


    @RequestMapping(value = "/unauthorized",method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
    public Object unauthorized(HttpServletRequest request){
        log.info("无效登陆口令，请重新登陆");
        throw new BusinessException(CommonResultCode.REMOTE_ERROR,"无效登陆口令","无效登陆口令，请重新登陆");

    }


}
