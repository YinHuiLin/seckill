package com.lins.seckill.utils;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Component;

/**
 * @author xiangzhimin
 * @Description
 * @create 2021-02-08 10:34
 */

@Component
/**
 * MD5工具类
 */
public class MD5Util {

    // MD5的第一次加盐的salt值，客户端和服务端一致
    private static final String salt = "1a2b3c4d";

    /**
     * 获取输入字符串的MD5
     *
     * @param src
     * @return
     */
    public static String md5(String src) {
        return DigestUtils.md5Hex(src);
    }


    public static String inputPassToFormPass(String inputPassword) {
        // 加盐规则（自定义）
        String str = "" + salt.charAt(0) + salt.charAt(2) + inputPassword + salt.charAt(5) + salt.charAt(4);
        // Calculates the MD5 digest and returns the value as a 32 character hex string.
        return md5(str);
    }

    /**
     * 前端传来的加密成成存储数据库中的密码
     * @param formPassword
     * @param saltDb
     * @return
     */
    public static String formPassToDbPass(String formPassword, String saltDb) {
        String str = "" + saltDb.charAt(0) + saltDb.charAt(2) + formPassword + saltDb.charAt(5) + saltDb.charAt(4);
        return md5(str);
    }

    /**
     * 两次密码加密
     * @param inputPassword
     * @param saltDb
     * @return
     */
    public static String inputPassToDbPass(String inputPassword, String saltDb) {
        String formPass = inputPassToFormPass(inputPassword);
        String dbPass = formPassToDbPass(formPass, saltDb);
        return dbPass;
    }

}

