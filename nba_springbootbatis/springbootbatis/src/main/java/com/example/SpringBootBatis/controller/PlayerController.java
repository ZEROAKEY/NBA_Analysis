package com.example.SpringBootBatis.controller;

import com.example.SpringBootBatis.bean.ResponseResult;
import com.example.SpringBootBatis.bean.dataBase.PlayerBean;
import com.example.SpringBootBatis.service.PlayerService;
import com.example.SpringBootBatis.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("search")
public class PlayerController {

    @Autowired(required = false)
    private PlayerService playerService;

    @GetMapping("/player")
    public ResponseResult getPlayersByEachParameter(@RequestParam(required = false, value = "team_id") Integer team_id,
                                                    @RequestParam(required = false, value = "team_name") String team_name,
                                                    @RequestParam(required = false, value = "player_name") String player_name,
                                                    @RequestParam(required = false, value = "pageSize") Integer pageSize,
                                                    @RequestParam(required = false, value = "pageNum") Integer pageNum) throws Exception {
        // 确保 pageSize 和 pageNum 有效，提供默认值或处理异常情况
        if (pageSize == null || pageSize <= 0) {
            pageSize = 10;  // 默认每页显示10条
        }
        if (pageNum == null || pageNum <= 0) {
            pageNum = 1;  // 默认显示第一页
        }

        // 获取玩家数据，参数可以为空
        List<PlayerBean> playerBeans = playerService.getPlayersByEachParameter(team_id, player_name, team_name);

        // 返回分页后的结果
        return ResultUtil.querySuccessPage(playerBeans, pageNum, pageSize);
    }
}
