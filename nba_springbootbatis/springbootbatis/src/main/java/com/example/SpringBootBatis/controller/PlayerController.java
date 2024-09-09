package com.example.SpringBootBatis.controller;

import com.example.SpringBootBatis.bean.ResponseResult;
import com.example.SpringBootBatis.bean.dataBase.PlayerBean;
import com.example.SpringBootBatis.bean.dataBase.UserBean;
import com.example.SpringBootBatis.service.PlayerService;
import com.example.SpringBootBatis.service.UserService;
import com.example.SpringBootBatis.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("search")
public class PlayerController {@Autowired(required = false)
private PlayerService playerService;

    @GetMapping("/player")
    public ResponseResult getPlayersByEachParameter(@RequestParam(required = false,value = "teamId") String teamId,
                                                    @RequestParam(required = false,value = "teamName") String teamName,
                                                    @RequestParam(required = false,value = "name") String name,
                                                    @RequestParam(required = false,value = "pageSize") String pageSize,
                                                    @RequestParam(required = false,value = "pageNum") String pageNum) throws Exception {
        List<PlayerBean> playerBeans=playerService.getPlayersByEachParameter(teamId,name,teamName);
        return ResultUtil.querySuccessPage(playerBeans,Integer.parseInt(pageNum),Integer.parseInt(pageSize));
    }
}