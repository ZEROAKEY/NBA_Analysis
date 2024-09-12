package com.example.SpringBootBatis.service;

import com.example.SpringBootBatis.bean.dataBase.TeamBean;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * 对于 Controller 的操作，与 Mapper 一一对应
 */
@Service
public interface NbaService {

    /**
     * 获取球队信息并按某一列排序
     * 所有参数均为选填项
     *
     * @param index       按照某一列排序
     * @param season_year 赛季年份
     * @param team_name   球队名称
     * @param team_id     球队 ID
     * @return 符合条件的球队信息列表
     */
    List<TeamBean> getTeamsSortByIndex(String index, String season_year, String team_name, String team_id);

    /**
     * 获取球队排名信息
     *
     * @param season_year 赛季年份
     * @param team_id     球队 ID
     * @return 符合条件的球队排名信息
     */
    List<TeamBean> getTeamsRank(String season_year, String team_id);
}
