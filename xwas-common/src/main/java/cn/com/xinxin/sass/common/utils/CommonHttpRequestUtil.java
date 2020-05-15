package cn.com.xinxin.sass.common.utils;

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
