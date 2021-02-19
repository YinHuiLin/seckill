package com.lins.seckill.service.serviceImpl;

import com.lins.seckill.entity.Result;
import com.lins.seckill.entity.ResultEnum;
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
    public Result doLogin(LoginVo loginVo, HttpServletRequest request, HttpServletResponse response) {
        String phone=loginVo.getPhone();
        String password=loginVo.getPassword();
        if (StringUtils.isEmpty(phone)||StringUtils.isEmpty(password)){
            return Result.error(ResultEnum.LOGIN_ERROR);
        }
        if(!ValidatorUtil.isMobile(phone)){
            return Result.error(ResultEnum.MOBILE_ERROR);
        }
        User user=userMapper.selectById(phone);
        if(user==null){
            throw new GlobalException(ResultEnum.LOGIN_ERROR);
        }
        if(!MD5Util.formPassToDBPass(password,user.getSalt()).equals(user.getPassword())){
            throw new GlobalException(ResultEnum.LOGIN_ERROR);
        }
        //生成cookie
        String ticket= UUIDUtil.uuid();
        redisTemplate.opsForValue().set("user:"+ticket,user);

        return null;
    }
}
