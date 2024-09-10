package com.example.SpringBootBatis;

import com.example.SpringBootBatis.bean.dataBase.UserBean;
import com.example.SpringBootBatis.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@MapperScan("com.example.SpringBootBatis.mapper")
class SpringBootBatisApplicationTests {

    @Autowired(required = false)
    UserService userService;

    @Test
    void contextLoads() {
        System.out.println("查询");
        List<UserBean> users=userService.queryAllUser();
        System.out.println(users.size());
        if(users ==null)
            return;
        for(UserBean u:users){
            System.out.println(u);
        }
    }

}