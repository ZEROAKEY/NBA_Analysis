package com.example.SpringBootBatis.controller;

import com.example.SpringBootBatis.bean.ResponseResult;
import com.example.SpringBootBatis.bean.dataBase.PlayerTotalBean;
import com.example.SpringBootBatis.service.PlayerTotalService;
import com.example.SpringBootBatis.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/search")
public class PlayerTotalController {

    @Autowired(required = false)
    private PlayerTotalService playerTotalService;

    // 获取球员总数据，并根据特定字段排序，查询中包含 Year 字段
    @GetMapping("/PlayerTotal")
    public ResponseResult getPlayerTotalsAndSortByIndex(@RequestParam(required = false, value = "season_year") Integer season_year,
                                                        @RequestParam(required = false, value = "index") String index,
                                                        @RequestParam(required = false, value = "position") String position,
                                                        @RequestParam(required = false, value = "pageNum", defaultValue = "1") int pageNum,
                                                        @RequestParam(required = false, value = "pageSize", defaultValue = "10") int pageSize,
                                                        @RequestParam(required = false, value = "team_id") Integer team_id,
                                                        @RequestParam(required = false, value = "player_name") String player_name,
                                                        @RequestParam(required = false, value = "team_name") String team_name) throws Exception {

        // 根据参数查询符合条件的球员数据
        List<PlayerTotalBean> playerTotalBeans = playerTotalService
                .getPlayerTotalsByEachParameterAndSortByIndex(
                        team_id, team_name, position, player_name, season_year, index);

        // 返回分页后的结果，保留teamName和teamId
        return ResultUtil.querySuccessPage(playerTotalBeans, pageNum, pageSize);
    }
}