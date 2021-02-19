package com.lins.seckill.utils;

import java.util.UUID;

/**
 * @ClassName UUIDUtil
 * @Description TODO
 * @Author lin
 * @Date 2021/2/19 16:29
 * @Version 1.0
 **/
public class UUIDUtil {
    public static String uuid(){
        return UUID.randomUUID().toString().replace("-","");
    }
}
