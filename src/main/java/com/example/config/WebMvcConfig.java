package com.example.config;

import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
/*
开启拦截器配置后静态资源无法访问
 */
//@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        super.addInterceptors(registry);
//        registry.addInterceptor(new LoginInterceptor()).addPathPatterns("/**");
    }

    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        super.addResourceHandlers(registry);
        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/**")
                .addResourceLocations("classpath:/static/");
    }

}
