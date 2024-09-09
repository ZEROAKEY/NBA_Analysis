package com.example.SpringBootBatis.controller;

import com.example.SpringBootBatis.bean.ResponseResult;
import com.example.SpringBootBatis.bean.dataBase.TeamBean;
import com.example.SpringBootBatis.service.NbaService;
import com.example.SpringBootBatis.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/search")
public class NbaController {@Autowired(required = false)
private NbaService nbaService;
    @GetMapping("/team")
    public ResponseResult getTeamsSortByIndex(@RequestParam(required = false,value = "seasonId") String seasonId,
                                              @RequestParam(required = false,value = "seasonType") String seasonType,
                                              @RequestParam(required = false,value = "pageNum") String pageNum,
                                              @RequestParam(required = false,value = "name") String name,
                                              @RequestParam(required = false,value = "conference") String conference,
                                              @RequestParam(required = false,value = "pageSize") String pageSize,
                                              @RequestParam(required = false,value = "index") String index,
                                              @RequestParam(required = false,value = "teamId") String teamId) throws Exception {
        List<TeamBean> teamBeans=nbaService.getTeamsSortByIndex(index,seasonId,seasonType,name,conference,teamId);
        return ResultUtil.querySuccessPage(teamBeans,Integer.parseInt(pageNum),Integer.parseInt(pageSize));
    }


    @GetMapping("/teamRank")
    public ResponseResult getTeamsRank(@RequestParam(required = false,value = "seasonId") String seasonId,
                                       @RequestParam(required = false,value = "seasonType") String seasonType,
                                       @RequestParam(required = false,value = "teamId") String teamId) throws Exception {
        List<TeamBean> teamBeans=nbaService.getTeamsRank(seasonId,seasonType,teamId);
        return ResultUtil.querySuccessFull(teamBeans);
    }

}
