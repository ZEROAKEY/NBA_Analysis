package com.example.SpringBootBatis.servicelmpl;

import com.example.SpringBootBatis.bean.dataBase.TeamBean;
import com.example.SpringBootBatis.bean.dataBase.UserBean;
import com.example.SpringBootBatis.mapper.NbaMapper;
import com.example.SpringBootBatis.mapper.UserMapper;
import com.example.SpringBootBatis.service.NbaService;
import com.example.SpringBootBatis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 整合mapper（dao）和service
 */
@Component
public class NbaServiceImpl implements NbaService {

    @Autowired
    private NbaMapper nbaMapper;

    /**
     * 都是选填项
     *
     * @param index      按照某一列排序
     * @param seasonId
     * @param seasonType
     * @param name
     * @return
     */
    @Override
    public List<TeamBean> getTeamsSortByIndex(String index, String seasonId, String seasonType, String name,String conference,String teamId) {
        List<TeamBean> teamBeans=nbaMapper.getTeamsSortByIndex(index,seasonId,seasonType,name,conference,teamId);
        return teamBeans;
    }

    @Override
    public List<TeamBean> getTeamsRank(String seasonId, String seasonType, String teamId) {
        List<TeamBean> teamBeans=nbaMapper.getTeamsRank(seasonId,seasonType,teamId);
        return teamBeans;
    }

}
