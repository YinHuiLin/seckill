package com.lins.seckill.mapper;

import com.lins.seckill.entity.Goods;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lins.seckill.vo.GoodsVo;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lin
 * @since 2021-02-19
 */
public interface GoodsMapper extends BaseMapper<Goods> {
    List<GoodsVo> findGoodsVo();
    GoodsVo findGoodById(Long goodsId);
}
