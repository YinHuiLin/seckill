package com.lins.seckill.vo;

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
    private long code;
    private String message;
    private Object obj;
    public static Result success(){
        return new Result(ResultEnum.SUCCESS.getCode(),ResultEnum.SUCCESS.getMessage(),null);
    }
    public static Result success(Object obj){
        return new Result(ResultEnum.SUCCESS.getCode(),ResultEnum.SUCCESS.getMessage(),obj);
    }

    /**
     * @Description 返回失败结果
     * @param resultEnum
     * @return com.lins.seckill.vo.Result
     */
    public static Result error(ResultEnum resultEnum){
        return new Result(resultEnum.getCode(),resultEnum.getMessage(),null);
    }
    public static Result error(ResultEnum resultEnum,Object obj){
        return new Result(resultEnum.getCode(),resultEnum.getMessage(),obj);
    }

}
