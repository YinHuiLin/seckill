package com.lins.seckill.utils;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * @ClassName MD5Util
 * @Description TODO
 * @Author lin
 * @Date 2021/2/19 18:01
 * @Version 1.0
 **/
public class MD5Util {
    // 前端传输密码时进行第一次MD5加密加盐的salt值，客户端和服务端一致
    private static final String salt="1a2b3c4d";
    /**
     * @Description
     * @param src
     * @return java.lang.String
     */
        public static String md5(String src){
        return DigestUtils.md5Hex(src);
    }

    public static String inputPassToFormPass(String inputPass){

        String string=salt.charAt(0)+salt.charAt(1)+inputPass+salt.charAt(5)+salt.charAt(4);
        return md5(string);
    }
    public static String formPassToDBPass(String formPass,String salt){
        //新建salt，存入数据库
        String string=salt.charAt(0)+salt.charAt(1)+formPass+salt.charAt(5)+salt.charAt(4);
        return md5(string);
    }
    //实际应用时，直接从inputpass转换成dbpass
    public static String inputPassToDBPass(String inputPass,String salt){
        String formpass=inputPassToFormPass(inputPass);
        String dbpass=formPassToDBPass(formpass,salt);
        return dbpass;
    }
    public static void main(String[] args) {
        System.out.println(inputPassToFormPass("123456"));
        System.out.println(formPassToDBPass("9a55e2956c86f67c7221991e5f08160c","1a2b3c4d"));
        System.out.println(inputPassToDBPass("123456","1a2b3c4d"));
    }
}
