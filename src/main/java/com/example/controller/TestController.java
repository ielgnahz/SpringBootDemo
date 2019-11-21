package com.example.controller;

import com.example.bean.JsonResult;
import com.example.demo.Article;
import com.example.demo.User;
import com.example.util.HttpUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;

@Controller
@RequestMapping("/test")
public class TestController {

    @RequestMapping(value = "/emp", method = RequestMethod.PUT)
    @ResponseBody
    public void testPut(User user){
        System.out.println(user);
    }

    @RequestMapping(value = "/status")
    @ResponseBody
    public String testStatus(){
        return "OK";
    }

}
