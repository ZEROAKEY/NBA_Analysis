package com.example.SpringBootBatis.mapper;

import com.example.SpringBootBatis.bean.dataBase.PlayerBean;
import com.example.SpringBootBatis.bean.dataBase.PlayerTotalBean;
import com.example.SpringBootBatis.bean.dataBase.TeamBean;
import com.example.SpringBootBatis.bean.dataBase.UserBean;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 对于数据库的操作
 */
@Repository
public interface PlayerMapper {
    /**
     * 都是选填项
     * @param teamId 队伍ID
     * @param name
     * @param teamName
     * @return
     */
    List<PlayerBean> getPlayersByEachParameter(String teamId,String name,String teamName);
}
