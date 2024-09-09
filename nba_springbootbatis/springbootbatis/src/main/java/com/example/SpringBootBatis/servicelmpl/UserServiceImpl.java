package com.example.SpringBootBatis.servicelmpl;

import com.example.SpringBootBatis.bean.dataBase.UserBean;
import com.example.SpringBootBatis.mapper.UserMapper;
import com.example.SpringBootBatis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 整合mapper（dao）和service
 */
@Component
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserBean loginIn(String username,String password){
        UserBean userBean=userMapper.loginCheck(username,password);
        return  userBean;
    }

    /**
     * 查询用户
     */
    @Override
    public List<UserBean> queryUsersByName(String username) {
        List<UserBean> users=userMapper.queryUsersByName(username);
        return users;
    }

    @Override
    public UserBean queryUserByName(String username) {
        return userMapper.queryUserByName(username);
    }

    @Override
    public UserBean queryUserById(String id) {
        return userMapper.selectUserById(id);
    }

    @Override
    public int addUser(UserBean userBean) {
        return userMapper.insertUser(userBean);
    }

    @Override
    public int dropUser(String id) {
        return userMapper.deleteUser(id);
    }

    @Override
    public int modifyUser(UserBean userBean) {
        return userMapper.updateUser(userBean);
    }



    @Override
    public List<UserBean> queryAllUser() {
        return userMapper.getAllUser();
    }
}
