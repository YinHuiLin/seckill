package com.lins.seckill.config;

import com.lins.seckill.entity.User;
import com.lins.seckill.service.IUserService;
import com.lins.seckill.utils.CookieUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName UserArgumentResolver
 * @Description 自定义用户参数
 * @Author lin
 * @Date 2021/2/20 10:01
 * @Version 1.0
 **/
@Component
public class UserArgumentResolver implements HandlerMethodArgumentResolver {
    @Autowired
    private IUserService userService;
    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        Class<?> clazz=methodParameter.getParameterType();
        return clazz== User.class;
    }
    //上方法进行条件判断，返回true才执行下面的方法
    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        HttpServletRequest request = nativeWebRequest.getNativeRequest(HttpServletRequest.class);
        HttpServletResponse response = nativeWebRequest.getNativeResponse(HttpServletResponse.class);
        String ticket = CookieUtil.getCookieValue(request,"userTicket");
        if(StringUtils.isEmpty(ticket)){
            return null;
        }
        return userService.getUserByCookie(ticket,request,response);
    }
}
