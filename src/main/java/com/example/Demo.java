package com.example;

import com.example.bean.demo.Article;
import com.example.util.CustomerJsonSerializer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

public class Demo {
    public static void main(String[] args) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setFilterProvider(new SimpleFilterProvider().addFilter("ID-TITLE", SimpleBeanPropertyFilter.filterOutAllExcept("id","title")));
        String filterOut = mapper.writeValueAsString(new Article());

        mapper = new ObjectMapper();
        mapper.setFilterProvider(new SimpleFilterProvider().addFilter("ID-TITLE", SimpleBeanPropertyFilter.serializeAllExcept("id", "title")));
        String serializeAll = mapper.writeValueAsString(new Article());

        System.out.println("filterOut: " + filterOut);
        System.out.println("serializeAll: " + serializeAll);
//

        CustomerJsonSerializer cjs = new CustomerJsonSerializer();
        cjs.filter(Article.class, "id,title", null);
        System.out.println(cjs.toJson(new Article()));

        cjs = new CustomerJsonSerializer();
        cjs.filter(Article.class, "", "id,title");
        System.out.println(cjs.toJson(new Article()));

    }

}
