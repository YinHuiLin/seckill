package com.lins.seckill.exception;

import com.lins.seckill.entity.ResultEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName GlobalException
 * @Description TODO
 * @Author lin
 * @Date 2021/2/19 16:26
 * @Version 1.0
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GlobalException extends RuntimeException{
    private ResultEnum resultEnum;
}
