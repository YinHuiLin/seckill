package com.lins.seckill.service.serviceImpl;

import com.lins.seckill.utils.CookieUtil;
import com.lins.seckill.vo.Result;
import com.lins.seckill.vo.ResultEnum;
import com.lins.seckill.entity.User;
import com.lins.seckill.exception.GlobalException;
import com.lins.seckill.mapper.UserMapper;
import com.lins.seckill.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lins.seckill.utils.MD5Util;
import com.lins.seckill.utils.UUIDUtil;
import com.lins.seckill.utils.ValidatorUtil;
import com.lins.seckill.vo.LoginVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lin
 * @since 2021-02-19
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    @Autowired
    UserMapper userMapper;
    @Autowired
    RedisTemplate redisTemplate;
    @Override
    public Result doLogin(LoginVo loginVo,HttpServletRequest request,HttpServletResponse response) {
        String phone=loginVo.getMobile();
        String password=loginVo.getPassword();
//        参数校验
//        if (StringUtils.isEmpty(phone)||StringUtils.isEmpty(password)){
//            return Result.error(ResultEnum.LOGIN_ERROR);
//        }
//        if(!ValidatorUtil.isMobile(phone)){
//            return Result.error(ResultEnum.MOBILE_ERROR);
//        }
        User user=userMapper.selectById(phone);
        if(user==null){
            throw new GlobalException(ResultEnum.LOGIN_ERROR);
        }
        if(!MD5Util.formPassToDBPass(password,user.getSalt()).equals(user.getPassword())){
            throw new GlobalException(ResultEnum.LOGIN_ERROR);
        }
        //生成cookie
        String ticket= UUIDUtil.uuid();
//        request.getSession().setAttribute(ticket,user);
        //不用session，将用户信息存入redis
        redisTemplate.opsForValue().set("user:"+ticket,user);
        CookieUtil.setCookie(request,response,"userTicket",ticket);
        return Result.success(ticket);
    }

    @Override
    public User getUserByCookie(String userTicket,HttpServletRequest request,HttpServletResponse response) {
        User user= (User) redisTemplate.opsForValue().get("user:"+userTicket);
        if (user!=null){
            //如果用户不为空，重新设置cookie
            CookieUtil.setCookie(request,response,"userTicket",userTicket);
        }
        return user;
    }

    @Override
    public Result updatePassword(String userTicket,String password,HttpServletRequest request,HttpServletResponse response) {
        User user = getUserByCookie(userTicket, request, response);
        if(user==null){
            throw new GlobalException(ResultEnum.MOBILE_NOT_EXIST);
        }
        //更新密码
        user.setPassword(MD5Util.inputPassToDBPass(password,user.getSalt()));
        int i = userMapper.updateById(user);
        if(i==1){
            //删除redis
            redisTemplate.delete("user:"+userTicket);
            //更新
            redisTemplate.opsForValue().set("user:"+userTicket,user);
            return Result.success();
        }
        return Result.error(ResultEnum.PASSWORD_UPDATE_FAIL);
    }
}
