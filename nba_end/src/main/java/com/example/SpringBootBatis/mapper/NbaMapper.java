package com.example.SpringBootBatis.mapper;

import com.example.SpringBootBatis.bean.dataBase.PlayerBean;
import com.example.SpringBootBatis.bean.dataBase.PlayerTotalBean;
import com.example.SpringBootBatis.bean.dataBase.TeamBean;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 对于数据库nba_team的操作
 */
@Repository
public interface NbaMapper {

    /**
     * 都是选填项
     * @param index 按照某一列排序
     * @param seasonId
     * @param seasonType
     * @param name
     * @return
     */
    List<TeamBean> getTeamsSortByIndex(String index,String seasonId,String seasonType,String name,String conference,String teamId);
    List<TeamBean> getTeamsRank(String seasonId,String seasonType,String teamId);

}
