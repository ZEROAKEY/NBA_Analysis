package com.example.SpringBootBatis.controller;

import com.example.SpringBootBatis.bean.ResponseResult;
import com.example.SpringBootBatis.bean.dataBase.PlayerBean;
import com.example.SpringBootBatis.bean.dataBase.PlayerTotalBean;
import com.example.SpringBootBatis.bean.dataBase.TeamBean;
import com.example.SpringBootBatis.service.PlayerTotalService;
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
public class PlayerTotalController {
    @Autowired(required = false)
    private PlayerTotalService playerTotalService;
     @GetMapping("/PlayerTotal")
     public ResponseResult getPlayerTotalsAndSortByIndex(@RequestParam(required = false,value = "seasonId") String seasonId,
                                                         @RequestParam(required = false,value = "seasonType") String seasonType,
                                                         @RequestParam(required = false,value = "index") String index,
                                                         @RequestParam(required = false,value = "position") String position,
                                                         @RequestParam(required = false,value = "pageNum") String pageNum,
                                                         @RequestParam(required = false,value = "teamId") String teamId,
                                                         @RequestParam(required = false,value = "name") String name,
                                                         @RequestParam(required = false,value = "teamName") String teamName,
                                                         @RequestParam(required = false,value = "pageSize") String pageSize) throws Exception {

          List<PlayerTotalBean> playerTotalBeans=playerTotalService.
                  getPlayerTotalsByEachParameterAndSortByIndex(
                          teamId,teamName,
                          position,name,index,
                          seasonId,seasonType);
          return ResultUtil.querySuccessPage(playerTotalBeans,Integer.parseInt(pageNum),Integer.parseInt(pageSize));
     }
}
