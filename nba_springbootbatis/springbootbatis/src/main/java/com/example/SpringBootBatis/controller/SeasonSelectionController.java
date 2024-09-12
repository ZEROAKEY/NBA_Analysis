package com.example.SpringBootBatis.controller;

import com.example.SpringBootBatis.bean.ResponseResult;
import com.example.SpringBootBatis.bean.dataBase.SeasonSelection;
import com.example.SpringBootBatis.service.SeasonSelectionService;
import com.example.SpringBootBatis.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/search")
public class SeasonSelectionController {
    @Autowired
    private SeasonSelectionService SeasonSelectionService;
    @GetMapping("/season")
    public ResponseResult getSeason(){
        List<SeasonSelection> SeasonSelectionBeans = SeasonSelectionService.getSeason();
        return ResultUtil.querySuccessFull(SeasonSelectionBeans);
    }
}
