package com.example.service;

import com.example.bean.demo.User;

import java.util.List;

public interface UserService {

    User findUserById(int id);

    List<User> findUsersByRange(int start,int end);

    Integer saveUser(User user);

    Integer saveUser();

}
