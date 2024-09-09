package com.example.SpringBootBatis.bean.dataBase;

import lombok.Data;

@Data
/**
 * id：账户编号
 * username：账户名
 * password：密码
 * rid：角色类型
 * mobile：手机号
 */
public class UserBean {
    private String id;
    private String username;
    private String password;
    private String rid;
    private String mobile;
    private String email;
}
