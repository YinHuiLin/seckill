package com.lins.seckill.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.lins.seckill.entity.Order;
import com.lins.seckill.entity.SeckillGoods;
import com.lins.seckill.entity.SeckillOrder;
import com.lins.seckill.entity.User;
import com.lins.seckill.exception.GlobalException;
import com.lins.seckill.mapper.OrderMapper;
import com.lins.seckill.mapper.SeckillGoodsMapper;
import com.lins.seckill.service.IGoodsService;
import com.lins.seckill.service.IOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lins.seckill.service.ISeckillGoodsService;
import com.lins.seckill.service.ISeckillOrderService;
import com.lins.seckill.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lin
 * @since 2021-02-19
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements IOrderService {
    @Autowired
    private ISeckillGoodsService seckillGoodsService;
    @Autowired
    private IGoodsService goodsService;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private IOrderService orderService;
    @Autowired
    private SeckillGoodsMapper seckillGoodsMapper;
    @Autowired
    private ISeckillOrderService seckillOrderService;
    @Autowired
    private RedisTemplate redisTemplate;
    @Transactional
    @Override
    public Order seckill(User user, GoodsVo goods) {
        //减库存
        SeckillGoods seckillGoods = seckillGoodsService.getOne(
                new QueryWrapper<SeckillGoods>().eq("goods_id", goods.getId()));
        seckillGoods.setStockCount(seckillGoods.getStockCount()-1);
//        seckillGoodsMapper.updateById(seckillGoods);
        //使用乐观锁解决库存超卖问题
        boolean update = seckillGoodsService.update(
                new UpdateWrapper<SeckillGoods>().setSql("stock_count = stock_count -1 ")
                        .eq("id", seckillGoods.getId()).gt("stock_count", 0)
        );
        if(!update){
            return null;
        }
        //生成订单
        Order order = new Order();
        order.setUserId(user.getId());
        order.setGoodsId(goods.getId());
        order.setAddrId(0L);
        order.setGoodsName(goods.getGoodsName());
        order.setGoodsCount(1);
        order.setGoodsPrice(seckillGoods.getSeckillPrice());
        order.setOrderChannel(1);
        order.setStatus(0);
        order.setCreateDate(new Date());
        orderMapper.insert(order);
//        orderService.save(order);
        //生成秒杀订单
        SeckillOrder seckillOrder = new SeckillOrder();
        seckillOrder.setUserId(user.getId());
        seckillOrder.setOrderId(order.getId());
        seckillOrder.setGoodsId(goods.getId());
        seckillOrderService.save(seckillOrder);
        redisTemplate.opsForValue().set("order:" + user.getId() + ":" + goods.getId(), seckillOrder);
        return order;
    }

    @Override
    public OrderDetailVo detail(Long orderId) {
        if(orderId==null){
            throw new GlobalException(ResultEnum.ORDER_NOT_EXIST);
        }
        Order order=orderMapper.selectById(orderId);
        GoodsVo goodsVo=goodsService.findGoodById(order.getGoodsId());
        OrderDetailVo detail=new OrderDetailVo(order,goodsVo);
        return detail;
    }
}
