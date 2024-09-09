package com.example.SpringBootBatis.service;

import com.example.SpringBootBatis.bean.dataBase.PlayerBean;
import com.example.SpringBootBatis.bean.dataBase.UserBean;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 对于controller的操作，与mapper一一对应
 */
@Service
public interface PlayerService {

    /**
     * 都是选填项
     * @param teamId 队伍ID
     * @param name
     * @param teamName
     * @return
     */
    List<PlayerBean> getPlayersByEachParameter(String teamId,String name,String teamName);

}
