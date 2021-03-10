package com.lins.seckill.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @ClassName MvcConfig
 * @Description TODO
 * @Author lin
 * @Date 2021/2/20 9:58
 * @Version 1.0
 **/
@Configuration
@EnableWebMvc
public class MvcConfig implements WebMvcConfigurer {
    @Autowired
    private UserArgumentResolver userArgumentResolver;
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(userArgumentResolver);
    }
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler("/bootstrap/**")
                .addResourceLocations("classpath:/static/bootstrap/");

        registry.addResourceHandler("/jquery-validation/**")
                .addResourceLocations("classpath:/static/jquery-validation/");

        registry.addResourceHandler("/js/**")
                .addResourceLocations("classpath:/static/js/");

        registry.addResourceHandler("/layer/**")
                .addResourceLocations("classpath:/static/layer/");

        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");

    }
}
