package com.example.SpringBootBatis.servicelmpl;

import com.example.SpringBootBatis.bean.dataBase.TeamBean;
import com.example.SpringBootBatis.mapper.NbaMapper;
import com.example.SpringBootBatis.service.NbaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 整合 mapper（dao） 和 service
 */
@Component
public class NbaServiceImpl implements NbaService {

    @Autowired
    private NbaMapper nbaMapper;

    /**
     * 获取球队信息并按某一列排序
     *
     * @param index       按照某一列排序
     * @param season_year 赛季年份
     * @param team_name   球队名称
     * @param team_id     球队 ID
     * @return 符合条件的球队信息列表
     */
    @Override
    public List<TeamBean> getTeamsSortByIndex(String index, String season_year, String team_name, String team_id) {
        // 调用 Mapper 层的方法
        return nbaMapper.getTeamsSortByIndex(index, season_year, team_name, team_id);

    }

    /**
     * 获取球队排名信息
     *
     * @param season_year 赛季年份
     * @param team_id     球队 ID
     * @return 符合条件的球队排名信息
     */
    @Override
    public List<TeamBean> getTeamsRank(String season_year, String team_id) {
        // 调用 Mapper 层的方法
        return nbaMapper.getTeamsRank(season_year, team_id);
    }
}
