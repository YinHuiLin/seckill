package com.lins.seckill.controller;


import com.lins.seckill.entity.User;
import com.lins.seckill.service.IOrderService;
import com.lins.seckill.vo.DetailVo;
import com.lins.seckill.vo.OrderDetailVo;
import com.lins.seckill.vo.Result;
import com.lins.seckill.vo.ResultEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lin
 * @since 2021-02-19
 */
@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private IOrderService orderService;

    @RequestMapping("/detail")
    @ResponseBody
    public Result detail(User user, Long orderId) {
        if (user == null)
            return Result.error(ResultEnum.SESSION_ERROR);

        OrderDetailVo orderDetail = orderService.detail(orderId);
        return Result.success(orderDetail);
    }

}
