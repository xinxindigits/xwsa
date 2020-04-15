package cn.com.xinxin.sass.auth.utils;

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
        String token = request.getHeader("XToken");
        return token;
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
