package cn.com.xinxin.sass.biz.util;

import cn.com.xinxin.sass.biz.vo.UserPwdVO;
import cn.com.xinxin.sass.common.constants.TokenEncryptConstants;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

/**
 * Created by dengyunhui on 2018/5/2
 **/
public class PasswordUtils {

    private static RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();

    public static UserPwdVO encryptPassword(String userNo, String password){
        String salt = randomNumberGenerator.nextBytes().toHex();

        String newPassword = new SimpleHash(
                TokenEncryptConstants.USER_PASSWORD_ENCRYPT_ALGORITHM_NAME,
                password,
                ByteSource.Util.bytes(userNo + salt),
                TokenEncryptConstants.USER_PASSWORD_HASH_ITERATION)
                .toHex();

        UserPwdVO userPwdVO = new UserPwdVO(newPassword,salt);
        return userPwdVO;
    }


    public static String encryptPassword(String userNo, String salt, String password){
        String newPassword = new SimpleHash(
                TokenEncryptConstants.USER_PASSWORD_ENCRYPT_ALGORITHM_NAME,
                password,
                ByteSource.Util.bytes(userNo + salt),
                TokenEncryptConstants.USER_PASSWORD_HASH_ITERATION)
                .toHex();

        return newPassword;
    }
}
