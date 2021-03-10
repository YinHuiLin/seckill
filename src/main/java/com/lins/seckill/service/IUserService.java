package com.lins.seckill.service;

import com.lins.seckill.vo.Result;
import com.lins.seckill.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lins.seckill.vo.LoginVo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lin
 * @since 2021-02-19
 */
public interface IUserService extends IService<User> {
    Result doLogin(LoginVo loginVo,HttpServletRequest request,HttpServletResponse response);
    //通过cookie获取user
    User getUserByCookie(String userTicket,HttpServletRequest request,HttpServletResponse response);
    Result updatePassword(String userTicket,String password,HttpServletRequest request,HttpServletResponse response);
}
