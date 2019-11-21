package com.example.config;

import com.example.Interceptor.LoginInterceptor;
import com.example.util.JsonReturnHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {

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

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor).addPathPatterns("/**").excludePathPatterns("/login.html", "/callback");
    }
}
