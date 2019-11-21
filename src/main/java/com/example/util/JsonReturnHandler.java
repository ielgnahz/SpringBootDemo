package com.example.util;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.mvc.method.annotation.ModelAndViewMethodReturnValueHandler;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;

import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.util.Arrays;

public class JsonReturnHandler implements HandlerMethodReturnValueHandler {

    private RequestResponseBodyMethodProcessor target;

    public JsonReturnHandler(RequestResponseBodyMethodProcessor target){
        this.target = target;
    }

    @Override
    public boolean supportsReturnType(MethodParameter methodParameter) {
        return methodParameter.hasMethodAnnotation(JSON.class) ||
                methodParameter.hasMethodAnnotation(ResponseBody.class);
    }

    @Override
    public void handleReturnValue(Object o, MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest) throws Exception {
//        modelAndViewContainer.setRequestHandled(true);
//        HttpServletResponse response = nativeWebRequest.getNativeResponse(HttpServletResponse.class);
//        Annotation[] annos = methodParameter.getMethodAnnotations();
//        CustomerJsonSerializer cjs = new CustomerJsonSerializer();
//        Arrays.asList(annos).forEach(anno -> {
//            if(anno instanceof  JSON){
//                JSON json = (JSON) anno;
//                cjs.filter(json.type(), json.include(), json.filter());
//            }
//        });
//        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
//        response.getWriter().write(cjs.toJson(o));

        if(methodParameter.hasMethodAnnotation(JSON.class)){
            modelAndViewContainer.setRequestHandled(true);
            HttpServletResponse response = nativeWebRequest.getNativeResponse(HttpServletResponse.class);
            CustomerJsonSerializer cjs = new CustomerJsonSerializer();
            JSON json = methodParameter.getMethodAnnotation(JSON.class);
            cjs.filter(json.type(), json.include(), json.filter());
            response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            response.getWriter().write(cjs.toJson(o));
        }else{
            target.handleReturnValue(o, methodParameter, modelAndViewContainer, nativeWebRequest);
        }

    }
}
