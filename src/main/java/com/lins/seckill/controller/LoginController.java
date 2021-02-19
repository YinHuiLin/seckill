package com.lins.seckill.controller;

import com.lins.seckill.entity.Result;
import com.lins.seckill.service.IUserService;
import com.lins.seckill.vo.LoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class LoginController {
    @Autowired
    private IUserService userService;
    @GetMapping("/login")
    public String login() {
        return "login";
    }
    @RequestMapping("/user/login")
    public Result doLogin(@Validated LoginVo loginVo, HttpServletRequest request, HttpServletResponse response){
        return userService.doLogin(loginVo,request,response);
    }
}
