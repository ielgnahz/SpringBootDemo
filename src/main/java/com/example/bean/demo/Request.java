package com.example.bean.demo;

import java.util.List;

public class Request {
    Integer id;

    List<String> list;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "id = "+id+" , "+list.toString();
    }
}
