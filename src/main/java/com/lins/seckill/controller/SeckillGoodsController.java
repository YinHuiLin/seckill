package com.lins.seckill.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lins.seckill.entity.Order;
import com.lins.seckill.entity.SeckillOrder;
import com.lins.seckill.entity.User;
import com.lins.seckill.service.IGoodsService;
import com.lins.seckill.service.IOrderService;
import com.lins.seckill.service.ISeckillOrderService;
import com.lins.seckill.vo.GoodsVo;
import com.lins.seckill.vo.Result;
import com.lins.seckill.vo.ResultEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
@RequestMapping("/seckill")
public class SeckillGoodsController {
    @Autowired
    private IGoodsService goodsService;
    @Autowired
    private ISeckillOrderService seckillOrderService;
    @Autowired
    private IOrderService orderService;
    @Autowired
    private RedisTemplate redisTemplate;

    @RequestMapping("/doSecKill2")
    public String doSecKill2(Model model, User user, Long goodsId){
        if(user==null){
            return "login";
        }
        model.addAttribute("user",user);
        GoodsVo goods = goodsService.findGoodById(goodsId);
        //库存判断
        if(goods.getStockCount()<1){
            model.addAttribute("errmsg", ResultEnum.EMPTY_STOCK.getMessage());
            return "seckillfail";
        }
        //重复抢购判断
        SeckillOrder seckillOrder = seckillOrderService.getOne(new QueryWrapper<SeckillOrder>()
                .eq("user_id", user.getId())
                .eq("goods_id", goodsId));
        if(seckillOrder!=null){
            model.addAttribute("errmsg", ResultEnum.REPEAT_ERROR.getMessage());
            return "seckillfail";
        }
        Order order = orderService.seckill(user,goods);
        model.addAttribute("order",order);
        model.addAttribute("goods",goods);
        return "orderdetail";
    }
    @PostMapping("/doSecKill")
    public Result doSecKill(Model model, User user, Long goodsId){
        if(user==null){
            return Result.error(ResultEnum.SESSION_ERROR);
        }
        GoodsVo goods = goodsService.findGoodById(goodsId);
        //库存判断
        if(goods.getStockCount()<=0){
            model.addAttribute("errmsg", ResultEnum.EMPTY_STOCK.getMessage());
            return Result.error(ResultEnum.EMPTY_STOCK);
        }
        //重复抢购判断,或者建立索引，即用户id和商品id建立联合索引。
        //通过redis获取
        SeckillOrder seckillOrder = ((SeckillOrder) redisTemplate.opsForValue().get("order:" + user.getId() + ":" + goods.getId()));
//        SeckillOrder seckillOrder = seckillOrderService.getOne(new QueryWrapper<SeckillOrder>()
//                .eq("user_id", user.getId())
//                .eq("goods_id", goodsId));
        if(seckillOrder!=null){
            model.addAttribute("errmsg", ResultEnum.REPEAT_ERROR.getMessage());
            return Result.error(ResultEnum.REPEAT_ERROR);
        }
        Order order = orderService.seckill(user,goods);
        return Result.success(order);
    }
}
