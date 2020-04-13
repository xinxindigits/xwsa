package cn.com.xinxin.portal.web.rest;

import cn.com.xinxin.oplog.client.Oplog;
import cn.com.xinxin.oplog.client.enums.AppProductEnum;
import cn.com.xinxin.oplog.client.enums.OperationTypeEnum;
import cn.com.xinxin.oplog.client.request.OplogProxyReq;
import cn.com.xinxin.portal.biz.service.XPortalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * Created by zhouyang on 25/04/2018.
 */

@RestController
@RequestMapping(value = "/portal", produces = "application/json; charset=UTF-8")
public class PortalRestController {


    /*@Autowired
    private XPortalService xPortalService;


    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index(){
        try {
            String sessionId = "index-say-hello-session_id";
            String opUser = "hello-user";
            String objectId = "hello-object-id";
            String userName = "yy";
            String ipAddress = "127.0.0.1";
            String device = "MacOS";
            String hello = this.xPortalService.findHello(userName);
            OplogProxyReq oplogProxyReq = new OplogProxyReq(AppProductEnum.NOTIFY_CENTER_MODIFY_MSG_CHANNEL,
                    OperationTypeEnum.DELETE, sessionId, opUser, objectId, this.xPortalService, "findHello",
                    new Class[] {String.class }, new Object[]{userName}, ipAddress, device, new Date());
            Oplog.proxyLog(oplogProxyReq);
            return hello;
        }catch (Exception ex){
           ex.printStackTrace();
        }
        return null;
    }
*/


}

