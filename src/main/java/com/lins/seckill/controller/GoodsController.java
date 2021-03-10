package com.lins.seckill.controller;


import com.lins.seckill.entity.User;
import com.lins.seckill.service.IGoodsService;
import com.lins.seckill.service.IUserService;
import com.lins.seckill.vo.GoodsVo;
import com.lins.seckill.vo.DetailVo;
import com.lins.seckill.vo.Result;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lin
 * @since 2021-02-19
 */
@Controller
@RequestMapping("/goods")
public class GoodsController {
    @Autowired
    private IUserService userService;
    @Autowired
    private IGoodsService goodsService;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private ThymeleafViewResolver thymeleafViewResolver;
    @RequestMapping(value = "/tolist",produces = "text/html;charset=utf-8")
    @ResponseBody
    public String tolist(User user, Model model,
                         HttpServletRequest request,HttpServletResponse response){
        //redis获取页面，若不为空，直接返回页面
        ValueOperations valueOperations=redisTemplate.opsForValue();
        String html= (String) valueOperations.get("goodslist");
        if (!StringUtils.isEmpty(html)){
            return html;
        }
        model.addAttribute("goodsList",goodsService.findGoodsVo());
        model.addAttribute("user",user);
        //如果为空，手动渲染存入redis手动返回
        WebContext webContext = new WebContext(request, response,
                request.getServletContext(),
                request.getLocale(),
                model.asMap());
        html = thymeleafViewResolver.getTemplateEngine().process("goodsList", webContext);
        if(!org.thymeleaf.util.StringUtils.isEmpty(html)){
            //失效时间60s
            valueOperations.set("goodsList",html,60, TimeUnit.SECONDS);
        }
        return html;
    }
    @RequestMapping(value = "/todetail2/{goodsId}",produces = "text/html;charset=utf-8")
    @ResponseBody
    public String toDetail2(User user, Model model, @PathVariable Long goodsId,
                           HttpServletRequest request,HttpServletResponse response){
        //redis获取页面，若不为空，直接返回页面
        ValueOperations valueOperations=redisTemplate.opsForValue();
        String html= (String) valueOperations.get("goodsdetail:"+goodsId);
        if (!StringUtils.isEmpty(html)){
            return html;
        }
        model.addAttribute("user",user);
        GoodsVo goods=goodsService.findGoodById(goodsId);
        Date startDate = goods.getStartDate();
        Date endDate = goods.getEndDate();
        Date nowDate = new Date();
        //秒杀状态
        int secKillStatus = 0 ;
        //秒杀的剩余时间
        int remainSeconds=0;
        //秒杀还没开始
        if(nowDate.before(startDate)){
            remainSeconds = ((int) ((startDate.getTime() - nowDate.getTime()) / 1000));
        }else if(nowDate.after(endDate)){
            //秒杀结束
            secKillStatus=2;
            remainSeconds=-1;
        }else{
            secKillStatus=1;
            remainSeconds=0;
        }
        model.addAttribute("remainSeconds",remainSeconds);
        model.addAttribute("seckillStatus",secKillStatus);
        model.addAttribute("goods", goods);
        //如果为空，手动渲染存入redis手动返回
        WebContext webContext = new WebContext(request, response,
                request.getServletContext(),
                request.getLocale(),
                model.asMap());
        html = thymeleafViewResolver.getTemplateEngine().process("goodsdetail", webContext);
        if(!org.thymeleaf.util.StringUtils.isEmpty(html)){
            //失效时间60s
            valueOperations.set("goodsdetail:"+goodsId,html,60, TimeUnit.SECONDS);
        }
        return html;
//        return "goodsdetail";
    }

    @RequestMapping(value = "/todetail/{goodsId}")
    @ResponseBody
    public Result toDetail(User user, @PathVariable Long goodsId){
        GoodsVo goods = goodsService.findGoodById(goodsId);
        Date startDate = goods.getStartDate();
        Date endDate = goods.getEndDate();
        Date nowDate = new Date();
        //秒杀状态
        int secKillStatus = 0 ;
        //秒杀的剩余时间
        int remainSeconds=0;
        //秒杀还没开始
        if(nowDate.before(startDate)){
            remainSeconds = ((int) ((startDate.getTime() - nowDate.getTime()) / 1000));
        }else if(nowDate.after(endDate)){
            //秒杀结束
            secKillStatus=2;
            remainSeconds=-1;
        }else{
            secKillStatus=1;
            remainSeconds=0;
        }
        DetailVo detailVo = new DetailVo();
        detailVo.setUser(user);
        detailVo.setGoodsVo(goods);
        detailVo.setRemainSeconds(remainSeconds);
        detailVo.setSecKillStatus(secKillStatus);
        return Result.success(detailVo);
    }
}
