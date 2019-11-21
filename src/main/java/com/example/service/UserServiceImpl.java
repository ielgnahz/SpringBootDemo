package com.example.service;

import com.example.demo.User;
import com.example.util.JDBCUtil;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class UserServiceImpl implements UserService {
    @Override
    @Cacheable(value = "user",key = "'id_'+#id")
    public User findUserById(int id) {
        System.out.println("-------------------未经过缓存-------------------");
        Connection conn = JDBCUtil.getConnection();
        String sql = "select * from user where id = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        User user = null;
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            while(rs.next()){
                user = new User();
                user.setId(rs.getInt(1));
                user.setName(rs.getString("username"));
                user.setSex(rs.getInt("sex"));
                user.setAddress(rs.getString("address"));
                user.setDate(rs.getDate("birthday"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public List<User> findUsersByRange(int start, int end) {
        Connection conn = JDBCUtil.getConnection();
        String sql = "select * from user limit ?,?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<User> list = new ArrayList<>();

        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, start);
            ps.setInt(2, end);
            rs = ps.executeQuery();
            while(rs.next()){
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setName(rs.getString("username"));
                user.setDate(rs.getDate("birthday"));
                user.setAddress(rs.getString("address"));
                user.setSex(rs.getInt("sex"));
                list.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    @CachePut(value = "user",key = "'id_'+#user.getId()")
    public User saveUser(User user) {
        System.out.println("-------------------存入缓存-------------------");
        Connection conn = JDBCUtil.getConnection();
        String sql = "insert into user(username,sex,birthday,address) values(?,?,?,?)";
        PreparedStatement ps = null;
        int flag = 0;
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, user.getName());
            ps.setInt(2, user.getSex());
            ps.setTimestamp(3,  new Timestamp(user.getDate().getTime()));
            ps.setString(4, user.getAddress());
            flag = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;

    }
}
