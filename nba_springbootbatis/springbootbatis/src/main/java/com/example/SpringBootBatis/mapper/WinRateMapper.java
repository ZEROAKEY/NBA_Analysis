package com.example.SpringBootBatis.mapper;

import com.example.SpringBootBatis.bean.dataBase.WinRateBean;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 对于数据库nba_team的操作
 */
@Repository
public interface WinRateMapper {

    /**
     * 都是选填项
     * @param VisitTeam
     * @param HomeTeam
     * @return
     */
    List<WinRateBean> getWinRateSortByIndex(String VisitTeam, String HomeTeam);

}
