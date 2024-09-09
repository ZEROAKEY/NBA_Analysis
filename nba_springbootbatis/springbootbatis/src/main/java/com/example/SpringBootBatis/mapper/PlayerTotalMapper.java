package com.example.SpringBootBatis.mapper;

import com.example.SpringBootBatis.bean.dataBase.PlayerBean;
import com.example.SpringBootBatis.bean.dataBase.PlayerTotalBean;
import com.example.SpringBootBatis.bean.dataBase.TeamBean;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 对于数据库的操作
 */
@Repository
public interface PlayerTotalMapper {
    /**
     *
     * @param teamId 队伍ID
     * @param teamName 队伍名
     * @param position 位置
     * @param name 姓名
     * @param index 按某一列排序
     * @param seasonId 几几年
     * @param seasonType 那个赛季
     * @return
     */
    List<PlayerTotalBean> getPlayerTotalsByEachParameterAndSortByIndex(String teamId,String teamName,
                                                                       String position,
                                                                       String name,
                                                                       String index,
                                                                       String seasonId,String seasonType);

}
