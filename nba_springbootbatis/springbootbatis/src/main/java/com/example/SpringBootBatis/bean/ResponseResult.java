package com.example.SpringBootBatis.bean;

import lombok.Data;

@Data
/*

返回json由meta和data
 */
public class ResponseResult<T> {
    private Meta meta;
    private T data;
}
