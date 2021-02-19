package com.lins.seckill.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName Result
 * @Description TODO
 * @Author lin
 * @Date 2021/2/19 15:39
 * @Version 1.0
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result {
    private Integer code;
    private String message;
    private Object obj;
    public static Result success(){
        return new Result(ResultEnum.SUCCESS.getCode(),ResultEnum.SUCCESS.getMessage(),null);
    }
    public static Result success(Object obj){
        return new Result(ResultEnum.SUCCESS.getCode(),ResultEnum.SUCCESS.getMessage(),obj);
    }

    public static Result error(ResultEnum resultEnum){
        return new Result(resultEnum.getCode(),resultEnum.getMessage(),null);
    }
    public static Result error(ResultEnum resultEnum,Object obj){
        return new Result(resultEnum.getCode(),resultEnum.getMessage(),obj);
    }

}
