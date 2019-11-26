package com.example.config;

import com.example.Interceptor.LoginInterceptor;
import com.example.util.JsonReturnHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;

import java.util.ArrayList;
import java.util.List;

/*
①开启拦截器配置后静态资源无法访问
②别的带@Configuration的类不要实现WebMvcConfigurer接口，因为它是WebMvcConfigurationSupport的父接口，项目启动时会报错
 */
//@Configuration
public class WebMvcConfiguration extends WebMvcConfigurationSupport {
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

    @Autowired
    private RequestMappingHandlerAdapter requestMappingHandlerAdapter;

    @Autowired
    private LoginInterceptor loginInterceptor;

    //    @PostConstruct
    public void init(){
        List<HandlerMethodReturnValueHandler> originalHandlers = new ArrayList<>(requestMappingHandlerAdapter.getReturnValueHandlers());
        for(int i = 0;i < originalHandlers.size();i++){
            HandlerMethodReturnValueHandler handler = originalHandlers.get(i);
            if(handler instanceof RequestResponseBodyMethodProcessor){
                JsonReturnHandler jsonReturnHandler = new JsonReturnHandler((RequestResponseBodyMethodProcessor) handler);
                originalHandlers.set(i, jsonReturnHandler);
                break;
            }
        }
        requestMappingHandlerAdapter.setReturnValueHandlers(originalHandlers);
    }

//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(loginInterceptor).addPathPatterns("/**").excludePathPatterns("/login.html", "/callback");
//    }


}
