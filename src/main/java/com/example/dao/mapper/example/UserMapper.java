package com.example.dao.mapper.example;

import com.example.bean.demo.User;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

public interface UserMapper {

    User selectUserById(Integer userId);

    Integer saveUser(@Param("name") String name, @Param("sex")Integer sex, @Param("date")Date date, @Param("address")String address);

}
