package cn.com.xinxin.sass.common.utils;

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

import com.xinxinfinance.commons.exception.BusinessException;
import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

/**
 * @author: zhouyang
 * @created: 09/05/2020.
 * @updater:
 * @description:
 */
public class CommonHttpRequestUtil {

    private static final Logger logger = LoggerFactory.getLogger(CommonHttpRequestUtil.class);

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

        String[] deviceArray = new String[]{"Andriod","Mac OS","Windows","iPad","iPhone","MIOS"};

        for (String device : deviceArray) {
            if (userAgent.indexOf(device) > -1){
                return device;
            }
        }
        return null;
    }


    public final static String getIpAddress(HttpServletRequest request){

        String[] headers = { "X-Forwarded-For",
                "X-Real-IP", "Proxy-Client-IP",
                "WL-Proxy-Client-IP",
                "HTTP_CLIENT_IP",
                "HTTP_X_FORWARDED_FOR" };

        // 获取请求主机IP地址,如果通过代理进来，则透过防火墙获取真实IP地址
        try {

            String ip;
            for (String header : headers) {
                ip = request.getHeader(header);
                if (false == CommonHttpRequestUtil.isUnknown(ip)) {
                    return CommonHttpRequestUtil.getMultistageReverseProxyIp(ip);
                }
            }
            ip = request.getRemoteAddr();
            logger.info("getIpAddress(HttpServletRequest) - getRemoteAddr - String ip=" + ip);
            return ip;
        }catch (Exception ex){
            logger.error("HttpRequestUtil, getIpAddress error = {}", ex.getMessage());
        }
        return null;
    }

    public static String getPath(String uriStr) {
        URI uri;
        try {
            uri = new URI(uriStr);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        return uri.getPath();
    }


    public static boolean isUnknown(String checkString) {
        return StringUtils.isBlank(checkString) || "unknown".equalsIgnoreCase(checkString);
    }

    public static String getMultistageReverseProxyIp(String ip) {
        // 多级反向代理检测
        if (ip != null && ip.indexOf(",") > 0) {
            final String[] ips = ip.trim().split(",");
            for (String subIp : ips) {
                if (false == isUnknown(subIp)) {
                    ip = subIp;
                    break;
                }
            }
        }
        return ip;
    }

}
