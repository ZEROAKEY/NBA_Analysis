package com.example.SpringBootBatis.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.SpringBootBatis.bean.dataBase.TenGameBean;
import com.example.SpringBootBatis.service.TenGameService;

@RestController
@RequestMapping("/search")
public class TenGameController {
    
    @Autowired
    private TenGameService tenGameService;

    // 获取最近10场比赛信息
    @GetMapping("/last10")
    public List<TenGameBean> getLastTenGames() {
        return tenGameService.getLastTenGames();
    }

    // 获取未来10场比赛信息
    @GetMapping("/next10")
    public List<TenGameBean> getNextTenGames() {
        return tenGameService.getNextTenGames();
    }
}
