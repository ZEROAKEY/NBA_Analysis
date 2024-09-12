package com.example.SpringBootBatis.controller;

import com.example.SpringBootBatis.bean.ResponseResult;
import com.example.SpringBootBatis.bean.dataBase.PlayerTotalSelectionBean;
import com.example.SpringBootBatis.service.PlayerTotalSelectionService;
import com.example.SpringBootBatis.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/search")
public class PlayerTotalSelectionController {
    @Autowired
    private PlayerTotalSelectionService PlayerTotalSelectionService;
    @GetMapping("/position")
    public ResponseResult getSelection(){
        List<PlayerTotalSelectionBean> PlayerTotalSelectionBeans = PlayerTotalSelectionService.getSelection();
        return ResultUtil.querySuccessFull(PlayerTotalSelectionBeans);
    }
}
