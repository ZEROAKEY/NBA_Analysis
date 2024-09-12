package com.example.SpringBootBatis.servicelmpl;

import com.example.SpringBootBatis.bean.dataBase.PlayerTotalBean;
import com.example.SpringBootBatis.mapper.PlayerTotalMapper;
import com.example.SpringBootBatis.service.PlayerTotalService;
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
     * 根据给定参数查询并按指定列排序的球员总数据信息
     *
     * @param team_id   队伍ID
     * @param team_name 队伍名
     * @param position 球员位置
     * @param player_name     球员姓名
     * @param index    排序字段
     * @param season_year     赛季年份
     * @return 符合条件的球员数据列表
     */
    @Override
    public List<PlayerTotalBean> getPlayerTotalsByEachParameterAndSortByIndex(Integer team_id, String team_name,
                                                                              String position, String player_name,
                                                                              Integer season_year, Integer index) {
        // 通过 PlayerTotalMapper 查询符合条件的数据
        return playerTotalMapper.getPlayerTotalsByEachParameterAndSortByIndex(team_id, team_name, position, player_name, season_year, index);
    }
}
