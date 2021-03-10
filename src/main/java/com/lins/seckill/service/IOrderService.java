package com.lins.seckill.service;

import com.lins.seckill.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lins.seckill.entity.User;
import com.lins.seckill.vo.GoodsVo;
import com.lins.seckill.vo.DetailVo;
import com.lins.seckill.vo.OrderDetailVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lin
 * @since 2021-02-19
 */
public interface IOrderService extends IService<Order> {

    Order seckill(User user, GoodsVo goodsVo);

    OrderDetailVo detail(Long orderId);
}
