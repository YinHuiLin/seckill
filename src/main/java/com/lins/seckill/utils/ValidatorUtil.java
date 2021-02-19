package com.lins.seckill.utils;

import org.thymeleaf.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author xiangzhimin
 * @Description 校验工具类
 * @create 2021-02-08 17:30
 */
public class ValidatorUtil {

    private static final Pattern mobile_pattern = Pattern.compile("[1]([3-9])[0-9]{9}$");

    public static boolean isMobile(String mobile) {
        if (StringUtils.isEmpty(mobile))
            return false;

        Matcher matcher = mobile_pattern.matcher(mobile);
        return matcher.matches();
    }
}
