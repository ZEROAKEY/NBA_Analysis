package com.example.SpringBootBatis.bean.SecondaryData;

import com.example.SpringBootBatis.bean.dataBase.UserBean;
import com.example.SpringBootBatis.util.JwtUtil;
import lombok.Data;

import java.util.HashMap;

@Data
/*
返回json由一个bean和一个token组成
 */
public class LoginData {
    private String id;
    private String rid;
    private String username;
    private String mobile;
    private String email;
    private String token;
    public LoginData (UserBean userBean){
        HashMap<String, String> map = new HashMap<>();
        map.put("msg","success");
        map.put("username",userBean.getUsername());
        map.put("id", userBean.getId());
        this.id=userBean.getId();
        this.rid=userBean.getRid();
        this.username=userBean.getUsername();
        this.mobile=userBean.getMobile();
        this.email=userBean.getEmail();
        this.token=JwtUtil.generateToken(map);
    }
}
