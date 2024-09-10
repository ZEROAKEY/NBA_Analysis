package com.example.SpringBootBatis.service;

import com.example.SpringBootBatis.bean.dataBase.WinRateBean;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 对于controller的操作，与mapper一一对应
 */
@Service
public interface WinRateService {

    /**
     * 都是选填项
     * @param VisitTeam
     * @param HomeTeam
     * @return
     */
    List<WinRateBean> getWinRateSortByIndex(String VisitTeam, String HomeTeam);




}
