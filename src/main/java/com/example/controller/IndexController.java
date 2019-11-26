package com.example.controller;

import com.example.bean.demo.Request;
import com.example.bean.demo.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/demo")
public class IndexController {

    @RequestMapping(value = "/index",method = RequestMethod.GET)
    @ResponseBody
    public String index(){
        return "index";
    }

    @RequestMapping("/hello")
    public String hello(){
        return "hello";
    }

    @RequestMapping("/zhangsan")
    public String zhangsan(){
        return "zhangsan1";
    }

    @PostMapping("/json")
    @ResponseBody
    public void json(@RequestBody Request request){
        System.out.println(request);
    }


}
