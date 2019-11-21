package com.example.demo;

import com.example.service.UserService;
import com.example.service.UserServiceImpl;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class Test {
    public static void main(String[] args) {
        UserService us = new UserServiceImpl();
//        User user = us.findUserById(1);
//        System.out.println(user);
//        List<User> list = us.findUsersByRange(0, 5);
//        System.out.println(list);

//        User user = new User();
//        user.setName("qianqi");
//        user.setSex(32);
//        user.setDate(new Date());
//        user.setAddress("海南");
//        System.out.println(us.saveUser(user));

        Optional<String> optional = Optional.of("1234");
        System.out.println(optional.orElse("12"));

    }
}
