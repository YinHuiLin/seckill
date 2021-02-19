package com.lins.seckill.service.serviceImpl;

import com.lins.seckill.entity.Goods;
import com.lins.seckill.mapper.GoodsMapper;
import com.lins.seckill.service.IGoodsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lin
 * @since 2021-02-19
 */
@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements IGoodsService {

}
