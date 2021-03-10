package com.lins.seckill.vo;

import com.lins.seckill.entity.Goods;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @ClassName GoodsVo
 * @Description TODO
 * @Author lin
 * @Date 2021/2/20 10:32
 * @Version 1.0
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GoodsVo extends Goods {
    /**
     * 秒杀价
     */
    private BigDecimal seckillPrice;

    /**
     * 库存数量
     */
    private Integer stockCount;

    /**
     * 秒杀开始时间
     */
    private Date startDate;

    /**
     * 秒杀结束时间
     */
    private Date endDate;
}
