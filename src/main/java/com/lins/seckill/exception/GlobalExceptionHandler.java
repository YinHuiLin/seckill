package com.lins.seckill.exception;

import com.lins.seckill.vo.Result;
import com.lins.seckill.vo.ResultEnum;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/**
 * @ClassName GlobalExceptionHandler
 * @Description TODO
 * @Author lin
 * @Date 2021/2/19 21:24
 * @Version 1.0
 **/

@RestControllerAdvice
/**
 * 等同于@ControllerAdvice+@ResponBody
 */
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public Result ExceptionHandler(Exception e) {
        if (e instanceof GlobalException) {
            GlobalException ex = (GlobalException) e;
            return Result.error(ex.getResultEnum());
        } else if (e instanceof BindException) {
            BindException be = (BindException) e;
            Result result = Result.error(ResultEnum.BIND_ERROR);
            result.setMessage("参数校验异常" + be.getBindingResult().getAllErrors().get(0).getDefaultMessage());
            return result;
        }
        return Result.error(ResultEnum.ERROR);
    }

}
