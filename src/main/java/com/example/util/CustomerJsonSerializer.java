package com.example.util;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

public class CustomerJsonSerializer{

    @JsonFilter(DYNC_FILTER)
    interface DynamicFilter{

    }

    @JsonFilter(DYNC_INCLUDE)
    interface DynamicInclude{

    }

    static final String DYNC_INCLUDE = "DYNC_INCLUDE";
    static final String DYNC_FILTER = "DYNC_FILTER";
    private ObjectMapper mapper = new ObjectMapper();

    public void filter(Class<?> clazz, String filter, String include){
        if(clazz == null) return;

        if(filter != null && !filter.isEmpty()){
            mapper.setFilterProvider(new SimpleFilterProvider().addFilter(DYNC_FILTER, SimpleBeanPropertyFilter.serializeAllExcept(filter.split(","))));
            mapper.addMixIn(clazz, DynamicFilter.class);
        }else if(include != null && !include.isEmpty()){
            mapper.setFilterProvider(new SimpleFilterProvider().addFilter(DYNC_INCLUDE, SimpleBeanPropertyFilter.filterOutAllExcept(include.split(","))));
            mapper.addMixIn(clazz, DynamicInclude.class);
        }

    }

    public String toJson(Object obj) throws JsonProcessingException {
        return mapper.writeValueAsString(obj);
    }

}