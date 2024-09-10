package com.example.SpringBootBatis.servicelmpl;

import com.example.SpringBootBatis.bean.dataBase.WinRateBean;
import com.example.SpringBootBatis.mapper.WinRateMapper;
import com.example.SpringBootBatis.service.WinRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 整合mapper（dao）和service
 */
@Component
public class WinRateServiceImpl implements WinRateService {

    @Autowired
    private WinRateMapper winrateMapper;

    /**
     * 都是选填项
     *
     * @param VisitTeam
     * @param HomeTeam
     * @return
     */
    @Override
    public List<WinRateBean> getWinRateSortByIndex(String VisitTeam, String HomeTeam) {
        List<WinRateBean> winrateBeans=winrateMapper.getWinRateSortByIndex(VisitTeam,HomeTeam);
        return winrateBeans;
    }

}
