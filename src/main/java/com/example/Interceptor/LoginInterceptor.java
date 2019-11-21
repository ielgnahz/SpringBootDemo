package com.example.Interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
@Component
public class LoginInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        if(request.getRequestURI().equals("/user/login")){
//            return true;
//        }
//        if(request.getSession().getAttribute("CURRENT_EMP") != null){
//            return true;
//        }
//        response.sendRedirect("/user/login?next=".concat(request.getRequestURI()));
//        return false;
        Cookie[] cookies = request.getCookies();
        HttpSession session = request.getSession();
        Object user = session.getAttribute("user");
        if(user == null){
            response.sendRedirect("/login.html");
            return false;
        }
        return true;
    }
}
