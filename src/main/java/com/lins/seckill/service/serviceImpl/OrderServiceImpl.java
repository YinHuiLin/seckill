package com.lins.seckill.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lins.seckill.entity.Order;
import com.lins.seckill.entity.SeckillGoods;
import com.lins.seckill.entity.SeckillOrder;
import com.lins.seckill.entity.User;
import com.lins.seckill.mapper.OrderMapper;
import com.lins.seckill.mapper.SeckillGoodsMapper;
import com.lins.seckill.mapper.SeckillOrderMapper;
import com.lins.seckill.service.IOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lins.seckill.service.ISeckillGoodsService;
import com.lins.seckill.service.ISeckillOrderService;
import com.lins.seckill.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
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
    private OrderMapper orderMapper;
    @Autowired
    private SeckillGoodsMapper seckillGoodsMapper;
    @Autowired
    private SeckillOrderMapper seckillOrderMapper;
    @Autowired
    private IOrderService orderService;
    @Autowired
    private ISeckillOrderService seckillOrderService;
    @Override
    @Transactional
    public Order seckill(User user, GoodsVo goods) {
        //减库存
        SeckillGoods seckillGoods = seckillGoodsMapper.selectOne(
                new QueryWrapper<SeckillGoods>().eq("goods_id", goods.getId()));
        seckillGoods.setStockCount(seckillGoods.getStockCount()-1);
        seckillGoodsMapper.updateById(seckillGoods);
        //解决库存超卖问题
//        boolean update = seckillGoodsService.update(
//                new UpdateWrapper<SeckillGoods>().setSql("stock_count = stock_count -1 ")
//                        .eq("id", seckillGoods.getId()).gt("stock_count", 0)
//        );
//        if(!update){
//            return null;
//        }
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
        System.out.println("id:"+order.getId());
        orderMapper.insert(order);
        //生成秒杀订单
        SeckillOrder seckillOrder = new SeckillOrder();
        seckillOrder.setUserId(user.getId());
        seckillOrder.setOrderId(order.getId());
        seckillOrder.setGoodsId(goods.getId());
        System.out.println("id1:"+seckillOrder.getId());
        seckillOrderService.save(seckillOrder);
//        seckillOrderMapper.insert(seckillOrder);
//        redisTemplate.opsForValue().set("order:" + user.getId() + ":" + goods.getId(), seckillOrder);
        return order;
    }
}
