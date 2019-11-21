package com.example.service;

import com.example.demo.User;

import java.util.List;

public interface UserService {

    User findUserById(int id);

    List<User> findUsersByRange(int start,int end);

    User saveUser(User user);

}
