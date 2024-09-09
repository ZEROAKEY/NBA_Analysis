package com.example.SpringBootBatis.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum msgEnum {
    FALSE(-1,"登录失败"),
    OK(200, "请求成功"),
    CREATED(201,"创建成功"),
    DELETED(204,"删除成功"),
    BAD_REQUEST(400,"请求的地址不存在或者包含不支持的参数"),
    UNAUTHORIZED(401,"未授权"),
    FORBIDDEN(403,"被禁止访问"),
    NOT_FOUND(404,"被请求的资源不存在"),
    UNPROCESSABLE_ENTITY(422,"[POST/PUT/PATCH] 当创建一个对象时，发生一个验证错误"),//错单词,在api接口文档中
    INTERNAL_SERVER_ERROR(500,"内部错误"),
    ;
    private int status;
    private String msg;
}
