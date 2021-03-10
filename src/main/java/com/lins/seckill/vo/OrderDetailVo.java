package com.lins.seckill.vo;

import com.lins.seckill.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName OrderDetailVo
 * @Description TODO
 * @Author lin
 * @Date 2021/2/21 0:54
 * @Version 1.0
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailVo {
    private Order order;
    private GoodsVo goodsVo;
}
