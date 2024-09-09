package com.example.SpringBootBatis.service;

import com.example.SpringBootBatis.bean.dataBase.TeamBean;
import com.example.SpringBootBatis.bean.dataBase.UserBean;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 对于controller的操作，与mapper一一对应
 */
@Service
public interface NbaService {

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
