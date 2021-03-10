package com.lins.seckill.service;

import com.lins.seckill.entity.Goods;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lins.seckill.vo.GoodsVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lin
 * @since 2021-02-19
 */
public interface IGoodsService extends IService<Goods> {
    List<GoodsVo>findGoodsVo();

    GoodsVo findGoodById(Long goodsId);
}
