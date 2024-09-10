package com.example.SpringBootBatis.controller;

import com.example.SpringBootBatis.bean.ResponseResult;
import com.example.SpringBootBatis.bean.dataBase.WinRateBean;
import com.example.SpringBootBatis.service.WinRateService;
import com.example.SpringBootBatis.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("")
public class WinRateController {
    @Qualifier("winRateServiceImpl")
    @Autowired(required = false)
    private WinRateService winrateService;
    @GetMapping("/winrate")
    public ResponseResult getWinRateSortByIndex(@RequestParam(required = false,value = "VisitTeam") String VisitTeam,
                                              @RequestParam(required = false,value = "HomeTeam") String HomeTeam) throws Exception {
        List<WinRateBean> winrateBeans=winrateService.getWinRateSortByIndex(VisitTeam,HomeTeam);
        return ResultUtil.querySuccessFull(winrateBeans);
    }

}
