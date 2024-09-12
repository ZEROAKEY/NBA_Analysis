package com.example.SpringBootBatis.service;

import com.example.SpringBootBatis.bean.dataBase.PlayerTotalBean;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * PlayerTotalService 与 controller、mapper 一一对应
 */
@Service
public interface PlayerTotalService {

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
    List<PlayerTotalBean> getPlayerTotalsByEachParameterAndSortByIndex(Integer team_id, String team_name,
                                                                       String position, String player_name,
                                                                       Integer season_year, String index);
}
