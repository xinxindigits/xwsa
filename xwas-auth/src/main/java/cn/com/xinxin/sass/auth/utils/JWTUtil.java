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

import cn.com.xinxin.sass.auth.protocol.SessionBizResultCodeEnum;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.xinxinfinance.commons.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;
import java.util.Date;
import java.util.UUID;

/**
 * @author: zhouyang
 * @created: 13/04/2020.
 * @updater:
 * @description:
 */
public class JWTUtil {


    public static final Logger log = LoggerFactory.getLogger(JWTUtil.class);

    // 过期时间480分钟分钟
   public static final long TOKEN_EXPIRE_TIME = 480 * 60 * 1000;

    // 过期时间5分钟分钟
    //public static final long TOKEN_EXPIRE_TIME = 5 * 60 * 1000;

    // 默认token倒计时失效60秒
    public static final Integer TOKEN_EXPIRE_TIME_COUNT = 30 ;

    public static final String TOKEN_NAME = "XToken";

    /**
     * 生成签名,5min后过期
     *
     * @param username 用户名
     * @param secret   用户的密码
     * @return 加密的token
     */
    public static String sign(String username,
                              String secret,
                              String tenantId) {
        try {
            Date date = new Date(System.currentTimeMillis() + TOKEN_EXPIRE_TIME);
            Algorithm algorithm = Algorithm.HMAC256(secret);
            // 附带username信息
            return JWT.create()
                    .withClaim("username", username)
                    .withClaim("tenantId", tenantId)
                    .withExpiresAt(date)
                    .sign(algorithm);

        }catch (Exception ex){
            log.info("token 解密失败  ex = {}", ex.getMessage());
            throw new BusinessException(SessionBizResultCodeEnum.INVALID_TOKEN);
        }
    }

    /**
     * 校验token是否正确
     *
     * @param token  密钥
     * @param secret 用户的密码
     * @return 是否正确
     */
    public static boolean verify(String token,
                                 String username,
                                 String tenantId,
                                 String secret) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withClaim("username", username)
                    .withClaim("tenantId", tenantId)
                    .build();
            DecodedJWT jwt = verifier.verify(token);
            return true;
        }catch (Exception ex){
            log.info("token 解密失败  ex = {}", ex.getMessage());
            throw new BusinessException(SessionBizResultCodeEnum.INVALID_TOKEN);
        }
    }


    /**
     * 获得token中的信息无需secret解密也能获得
     *
     * @return token中包含的用户名
     */
    public static String getUserAccount(String token) {

        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("username").asString();
        }catch (Exception ex){
            log.info("token 解密失败  ex = {}", ex.getMessage());
            throw new BusinessException(SessionBizResultCodeEnum.INVALID_TOKEN);
        }
    }

    /**
     * 获得token中的信息无需secret解密也能获得
     * @return token中包含的用户名
     */
    public static String getUserTeantId(String token) {

        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("tenantId").asString();
        }catch (Exception ex){
            log.info("token 解密失败  ex = {}", ex.getMessage());
            throw new BusinessException(SessionBizResultCodeEnum.INVALID_TOKEN);
        }
    }



    /**
     *从token解析出过期时间
     */
    public static Date getExpiresTime(String token){

        try {
            DecodedJWT jwt = JWT.decode(token);
            return  jwt.getExpiresAt();

        }catch (Exception ex){
            log.info("token 解密失败  ex = {}", ex.getMessage());
            throw new BusinessException(SessionBizResultCodeEnum.INVALID_TOKEN);
        }
    }



    public static boolean isExpired(String token){
        Date expireDate = getExpiresTime(token);
        if (expireDate == null) {
            return true;
        }
        return expireDate.before(new Date());
    }

    /**
     * md5加密
     * @param s：待加密字符串
     * @return 加密后16进制字符串
     */
    public static String md5(String s) {
        try {
            //实例化MessageDigest的MD5算法对象
            MessageDigest md = MessageDigest.getInstance("MD5");
            //通过digest方法返回哈希计算后的字节数组
            byte[] bytes = md.digest(s.getBytes("utf-8"));
            //将字节数组转换为16进制字符串并返回
            return toHex(bytes);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * 获取随即盐
     */
    public static String salt(){
        //利用UUID生成随机盐
        UUID uuid = UUID.randomUUID();
        //返回a2c64597-232f-4782-ab2d-9dfeb9d76932
        String[] arr = uuid.toString().split("-");
        return arr[0];
    }
    /**
     * 字节数组转换为16进制字符串
     * @param bytes
     * @return 16进制字符串
     */
    private static String toHex(byte[] bytes) {
        final char[] HEX_DIGITS = "0123456789ABCDEF".toCharArray();
        StringBuilder ret = new StringBuilder(bytes.length * 2);
        for (int i=0; i<bytes.length; i++) {
            ret.append(HEX_DIGITS[(bytes[i] >> 4) & 0x0f]);
            ret.append(HEX_DIGITS[bytes[i] & 0x0f]);
        }
        return ret.toString();
    }

}
