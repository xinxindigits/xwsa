package cn.com.xinxin.sass.biz.util;

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
