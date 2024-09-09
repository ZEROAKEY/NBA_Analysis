package com.example.SpringBootBatis.servicelmpl;

import com.example.SpringBootBatis.bean.dataBase.PlayerBean;
import com.example.SpringBootBatis.bean.dataBase.UserBean;
import com.example.SpringBootBatis.mapper.PlayerMapper;
import com.example.SpringBootBatis.mapper.UserMapper;
import com.example.SpringBootBatis.service.PlayerService;
import com.example.SpringBootBatis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.naming.Name;
import java.util.List;

/**
 * 整合mapper（dao）和service
 */
@Component
public class PlayerServiceImpl implements PlayerService {

    @Autowired
    private PlayerMapper playerMapper;

    /**
     * 都是选填项
     *
     * @param teamId   队伍ID
     * @param name
     * @param teamName
     * @return
     */
    @Override
    public List<PlayerBean> getPlayersByEachParameter(String teamId, String name, String teamName) {
        List<PlayerBean> playerBeans=playerMapper.getPlayersByEachParameter(teamId,name,teamName);
        return playerBeans;
    }
}
