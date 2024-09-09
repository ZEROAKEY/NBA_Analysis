package com.example.SpringBootBatis.servicelmpl;

import com.example.SpringBootBatis.bean.dataBase.PlayerTotalBean;
import com.example.SpringBootBatis.bean.dataBase.UserBean;
import com.example.SpringBootBatis.mapper.PlayerTotalMapper;
import com.example.SpringBootBatis.mapper.UserMapper;
import com.example.SpringBootBatis.service.PlayerTotalService;
import com.example.SpringBootBatis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 整合mapper（dao）和service
 */
@Component
public class PlayerTotalServiceImpl implements PlayerTotalService {

    @Autowired
    private PlayerTotalMapper playerTotalMapper;

    /**
     * 全是选填项
     * @param teamId     队伍ID
     * @param teamName   队伍名
     * @param position   位置
     * @param name       姓名
     * @param index      按某一列排序
     * @param seasonId   几几年
     * @param seasonType 那个赛季
     * @return
     */
    @Override
    public List<PlayerTotalBean> getPlayerTotalsByEachParameterAndSortByIndex(String teamId, String teamName, String position, String name, String index, String seasonId, String seasonType) {
        List<PlayerTotalBean> playerTotalBeans=playerTotalMapper.getPlayerTotalsByEachParameterAndSortByIndex(teamId, teamName, position, name, index, seasonId, seasonType);
        return playerTotalBeans;
    }
}
