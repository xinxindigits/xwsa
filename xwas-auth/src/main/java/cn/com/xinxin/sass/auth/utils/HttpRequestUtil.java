package cn.com.xinxin.sass.auth.utils;

/*
 *
 * Copyright 2020 www.xinxindigits.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software
 * and associated documentation files (the "Software"),to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice
 * shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 * Redistribution and selling copies of the software are prohibited, only if the authorization from xinxin digits
 * was obtained.Neither the name of the xinxin digits; nor the names of its contributors may be used to
 * endorse or promote products derived from this software without specific prior written permission.
 *
 */

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by dengyunhui on 2018/5/11
 * @author zhouyang
 **/
public class HttpRequestUtil {

    private static final Logger logger = LoggerFactory.getLogger(HttpRequestUtil.class);

    private static final String OPS_TENANT_ID = "OpsTenantId";

    /**
     * 1. Windows
     * 2. Mac OS
     * 3. iPad
     * 4. iPhone
     * 5. Android
     * @param request
     * @return
     */
    public static String getRequestDevice(HttpServletRequest request){
        String userAgent = request.getHeader("user-agent");

        if (userAgent == null || "".equals(userAgent)){
            return null;
        }

        String[] deviceArray = new String[]{"Andriod","Mac OS","Windows","iPad","iPhone"};

        for (String device : deviceArray) {
            if (userAgent.indexOf(device) > -1){
                return device;
            }
        }
        return null;

    }

    public static String getLoginToken(HttpServletRequest request){
        // 重请求信息中获取token
        String token = request.getHeader(JWTUtil.TOKEN_NAME);
        return token;
    }


    public static String getOpsTenantId(HttpServletRequest request){
        // 重请求信息中获取token
        String tenantId = request.getHeader(OPS_TENANT_ID);
        return tenantId;
    }




    public final static String getIpAddress(HttpServletRequest request){

        // 获取请求主机IP地址,如果通过代理进来，则透过防火墙获取真实IP地址
        try {

            String ip = request.getHeader("X-Forwarded-For");

            if (StringUtils.isEmpty(ip)|| "unknown".equalsIgnoreCase(ip)) {
                if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
                    ip = request.getHeader("Proxy-Client-IP");
                }
                if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
                    ip = request.getHeader("WL-Proxy-Client-IP");
                }
                if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
                    ip = request.getHeader("HTTP_CLIENT_IP");
                }
                if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
                    ip = request.getHeader("HTTP_X_FORWARDED_FOR");
                }
                if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
                    ip = request.getRemoteAddr();
                }
            } else if (ip.length() > 15) {
                String[] ips = ip.split(",");
                for (int index = 0; index < ips.length; index++) {
                    String strIp = (String) ips[index];
                    if (!("unknown".equalsIgnoreCase(strIp))) {
                        ip = strIp;
                        break;
                    }
                }
            }
            logger.info("getIpAddress(HttpServletRequest) - getRemoteAddr - String ip=" + ip);
            return ip;
        }catch (Exception ex){
            logger.error("HttpRequestUtil, getIpAddress error = {}", ex.getMessage());
        }
        return null;
    }

}
