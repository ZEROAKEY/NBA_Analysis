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
public class NbaController {

    @Autowired(required = false)
    private NbaService nbaService;

    /**
     * 根据条件获取球队并按指定字段排序
     *
     * @param season_year 赛季年份
     * @param pageNum     当前页码
     * @param team_name   球队名称
     * @param pageSize    每页显示条数
     * @param index       排序字段
     * @param team_id     球队ID
     * @return 带分页的查询结果
     */
    @GetMapping("/team")
    public ResponseResult getTeamsSortByIndex(@RequestParam(required = false, value = "season_year") String season_year,
                                              @RequestParam(required = false, value = "pageNum", defaultValue = "1") String pageNum,
                                              @RequestParam(required = false, value = "team_name") String team_name,
                                              @RequestParam(required = false, value = "pageSize", defaultValue = "10") String pageSize,
                                              @RequestParam(required = false, value = "index") String index,
                                              @RequestParam(required = false, value = "team_id") String team_id) throws Exception {

        // 获取排序后的球队数据
        List<TeamBean> teamBeans = nbaService.getTeamsSortByIndex(index, season_year, team_name, team_id);

        return ResultUtil.querySuccessPage(teamBeans, Integer.parseInt(pageNum), Integer.parseInt(pageSize));
    }

    /**
     * 获取球队排名信息
     *
     * @param season_year 赛季年份
     * @param team_id     球队ID
     * @return 查询结果
     */
    @GetMapping("/teamRank")
    public ResponseResult getTeamsRank(@RequestParam(required = false, value = "season_year") String season_year,
                                       @RequestParam(required = false, value = "team_id") String team_id) throws Exception {

        // 获取球队排名数据
        List<TeamBean> teamBeans = nbaService.getTeamsRank(season_year, team_id);

        return ResultUtil.querySuccessFull(teamBeans);
    }
}
