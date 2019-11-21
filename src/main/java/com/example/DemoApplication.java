package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
//import org.springframework.util.unit.DataSize;

import javax.servlet.MultipartConfigElement;
import javax.sql.DataSource;

@SpringBootApplication
@Configuration
public class DemoApplication {

    @Bean
    public MultipartConfigElement multipartConfigElement(){
        MultipartConfigFactory factory = new MultipartConfigFactory();
        return factory.createMultipartConfig();
    }

    @Bean
    public DataSource dataSource(){
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
        driverManagerDataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        driverManagerDataSource.setUrl("jdbc:mysql://localhost:3306/mybatis?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=GMT%2B8");
        driverManagerDataSource.setUsername("root");
        driverManagerDataSource.setPassword("1234");
        return driverManagerDataSource;
    }



    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
}
