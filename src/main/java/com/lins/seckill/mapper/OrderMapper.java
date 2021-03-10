package com.lins.seckill.mapper;

import com.lins.seckill.entity.Order;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lin
 * @since 2021-02-19
 */
public interface OrderMapper extends BaseMapper<Order> {

    void addOrder(Order order);
}
