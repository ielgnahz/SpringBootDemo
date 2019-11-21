package com.example.demo;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Size;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.persistence.Entity;

//@Entity
public class User {

    private int id;

    @Size(min = 1,max = 2,message = "用户名不符合规范")
    private String name;

    private int sex;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date date;

    private String address;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "id:"+id+",name="+name+",sex:"+sex+",birthday="+new SimpleDateFormat("yyyy-MM-dd").format(date)+",address="+address;
    }
}
