package com.lins.seckill.vo;

import com.lins.seckill.validator.IsMobile;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * @ClassName LoginVo
 * @Description TODO
 * @Author lin
 * @Date 2021/2/19 16:16
 * @Version 1.0
 **/
@Data
public class LoginVo {
    @IsMobile
    @NotNull
    private String phone;
    @NotNull
    @Length(min = 32)
    private String password;
}
