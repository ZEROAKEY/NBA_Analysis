package com.example.SpringBootBatis.service;

import com.example.SpringBootBatis.bean.dataBase.UserBean;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 对于controller的操作，与mapper一一对应
 *  * id：账户编号
 *  * username：账户名
 *  * password：密码
 *  * rid：角色类型
 *  * mobile：手机号
 */
@Service
public interface UserService {
    //登录核对
    UserBean loginIn(String username,String password) throws Exception;

    //根据ID查询用户信息
    UserBean queryUserById(String id);

    //插入用户
    int addUser (UserBean userBean);

    //删除用户
    int dropUser (String id);

    //修改用户（电话号码或密码）
    int modifyUser(UserBean userBean);

    List<UserBean> queryUsersByName(String username);
    UserBean queryUserByName(String username);

    //查询所有用户
    List<UserBean> queryAllUser();

}
