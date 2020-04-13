package cn.com.xinxin.portal.web.utils;

import cn.com.xinxin.oplog.client.Oplog;
import cn.com.xinxin.oplog.client.enums.AppProductEnum;
import cn.com.xinxin.oplog.client.enums.OperationTypeEnum;
import cn.com.xinxin.oplog.client.request.OplogProxyReq;
import cn.com.xinxin.oplog.client.request.OplogReq;

import java.util.Date;

/**
 * Created by dengyunhui on 2018/5/15
 **/
public class PortalOplogUtil {

    public static void logReq(AppProductEnum appProduct,
                              OperationTypeEnum operationType,
                              String userSessionId,
                              String opUserName,
                              String obejctKey,
                              String operIp,
                              String device,
                              Object params) {
        OplogReq oplogReq = new OplogReq(
                appProduct,
                operationType,
                opUserName, userSessionId,obejctKey + "",
                params, operIp, device, new Date()
        );

        Oplog.log(oplogReq);
    }


    public static void logProxyReq(AppProductEnum appProduct,
                                   OperationTypeEnum operationType,
                                   String userSessionId,
                                   String opUserName,
                                   String priKey,
                                   Object invokerObj,
                                   String method,
                                   Class[] parameterTypes,
                                   Object[] params,
                                   String ipAddress,
                                   String device) {
        OplogProxyReq oplogProxyReq = new OplogProxyReq(
            appProduct,
            operationType,
            userSessionId,
            opUserName,
            priKey,
            invokerObj,
            method,
            parameterTypes,
            params,
            ipAddress,
            device,
            new Date()
        );

        Oplog.proxyLog(oplogProxyReq);
    }


}
