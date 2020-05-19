package cn.com.xinxin.sass.web.utils;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: zhouyang
 * @created: 12/05/2020.
 * @updater:
 * @description:
 */
public class KaptchaUtils {

    /**
     * 将获取到的前端参数转为string类型
     * @param request
     * @param key
     * @return
     */
    public static String getString(HttpServletRequest request, String key) {
        try {
            String result = request.getParameter(key);
            if(result != null) {
                result = result.trim();
            }
            if("".equals(result)) {
                result = null;
            }
            return result;
        }catch(Exception e) {
            return null;
        }
    }


    /**
     * 验证码校验
     * @param request
     * @return
     */
    public static boolean checkVerifyCode(HttpServletRequest request) {
        //获取生成的验证码
        String verifyCodeExpected = (String)
                request.getSession().getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
        //获取用户输入的验证码
        String verifyCodeActual = KaptchaUtils.getString(request, "verifyCode");

        if(verifyCodeActual == null ||!StringUtils.equalsIgnoreCase(verifyCodeActual,verifyCodeExpected)) {
            return false;
        }
        return true;
    }

}
