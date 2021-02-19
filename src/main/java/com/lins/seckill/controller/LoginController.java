package com.lins.seckill.controller;

import com.lins.seckill.entity.Result;
import com.lins.seckill.service.IUserService;
import com.lins.seckill.vo.LoginVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/login")
@Slf4j
public class LoginController {
    @Autowired
    private IUserService userService;
    /**
     * @Description :跳转登录页面
     * @param
     * @return java.lang.String
     */
    @RequestMapping("/tologin")
    public String login() {
        return "login";
    }
    @RequestMapping("/user/login")
    public Result doLogin(@Validated LoginVo loginVo, HttpServletRequest request, HttpServletResponse response){
        return userService.doLogin(loginVo,request,response);
    }
}
