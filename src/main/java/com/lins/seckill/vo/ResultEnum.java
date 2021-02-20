package com.lins.seckill.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * @ClassName ResultEnum
 * @Description TODO
 * @Author lin
 * @Date 2021/2/19 15:41
 * @Version 1.0
 **/
@Getter
@ToString
@AllArgsConstructor
public enum  ResultEnum {
    SUCCESS(200,"success"),
    ERROR(500,"服务器出错"),
    //登录模块5002XX
    LOGIN_ERROR(50010,"用户名或密码不正确"),
    MOBILE_ERROR(500211,"用户名格式不正确"),
    BIND_ERROR(500212,"参数校验异常"),
    MOBILE_NOT_EXIST(500213, "手机号码不存在"),
    PASSWORD_UPDATE_FAIL(500214, "更新密码失败"),
    SESSION_ERROR(500215, "用户不存在"),
    URL_ERROR(404,"URL不存在"),
    //秒杀模块5005XX
    EMPTY_STOCK(500500, "库存不足"),
    REPEAT_ERROR(500501, "该商品每人限购一件"),
    //订单模块5003XX
    ORDER_NOT_EXIST(500300, "订单信息不存在");

    private final Integer code;
    private final String message;
}
