package com.lins.seckill.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author lin
 * @since 2021-02-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 用户电话
     */
    private String phone;

    /**
     * 用户密码，两次MD5加密
     */
    private String password;

    private String salt;

    /**
     * 用户头像
     */
    private String head;

    /**
     * 登录次数
     */
    private Integer loginCount;

    /**
     * 注册时间
     */
    private LocalDateTime registerDate;

    /**
     * 最后一次登录时间
     */
    private LocalDateTime lastLoginDate;


}
