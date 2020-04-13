package cn.com.xinxin.portal.biz.util;

import cn.com.xinxin.portal.biz.vo.UserPwdVO;
import cn.com.xinxin.portal.common.constants.XPortalConstants;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

/**
 * Created by dengyunhui on 2018/5/2
 **/
public class PasswordUtils {

    private static RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();

    public static UserPwdVO encryptPassword(String userNo,String password){
        String salt = randomNumberGenerator.nextBytes().toHex();

        String newPassword = new SimpleHash(
                XPortalConstants.USER_PASSWORD_ENCRYPT_ALGORITHM_NAME,
                password,
                ByteSource.Util.bytes(userNo + salt),
                XPortalConstants.USER_PASSWORD_HASH_ITERATION)
                .toHex();

        UserPwdVO userPwdVO = new UserPwdVO(newPassword,salt);
        return userPwdVO;
    }


    public static String encryptPassword(String userNo, String salt, String password){
        String newPassword = new SimpleHash(
                XPortalConstants.USER_PASSWORD_ENCRYPT_ALGORITHM_NAME,
                password,
                ByteSource.Util.bytes(userNo + salt),
                XPortalConstants.USER_PASSWORD_HASH_ITERATION)
                .toHex();

        return newPassword;
    }
}
