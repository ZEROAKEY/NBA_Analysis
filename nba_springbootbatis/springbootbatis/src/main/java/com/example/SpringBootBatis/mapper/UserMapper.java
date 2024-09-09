package com.example.SpringBootBatis.mapper;

import com.example.SpringBootBatis.bean.dataBase.UserBean;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 对于数据库的操作
 */
@Repository
public interface UserMapper {
    //登录核对
    UserBean loginCheck(String username,String password);

    //根据ID查询用户信息
    UserBean selectUserById(String id);

    //插入用户
    int insertUser (UserBean userBean);

    //删除用户
    int deleteUser (String id);

    //修改用户（电话号码或密码）
    int updateUser(UserBean userBean);

    List<UserBean> queryUsersByName(String username);
    UserBean queryUserByName(String username);
    //查询所有用户
    List<UserBean> getAllUser();

}
