package com.example.controller;

import com.example.bean.demo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/test")
public class TestController {

    @Autowired
    Environment environment;

    @RequestMapping(value = "/emp", method = RequestMethod.PUT)
    @ResponseBody
    public void testPut(User user){
        System.out.println(user);
    }

    @RequestMapping(value = "/status")
    @ResponseBody
    public String testStatus(){
//        System.out.println(environment.getProperty("example.datasource.url"));
        return "OK";
    }

}
