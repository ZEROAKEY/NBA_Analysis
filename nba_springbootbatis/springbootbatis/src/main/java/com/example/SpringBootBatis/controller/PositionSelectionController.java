package com.example.SpringBootBatis.controller;

import com.example.SpringBootBatis.bean.ResponseResult;
import com.example.SpringBootBatis.bean.dataBase.PositionSelectionBean;
import com.example.SpringBootBatis.service.PositionSelectionService;
import com.example.SpringBootBatis.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/search")
public class PositionSelectionController {
    @Autowired
    private PositionSelectionService PositionSelectionService;
    @GetMapping("/position")
    public ResponseResult getSelection(){
        List<PositionSelectionBean> positionSelectionBeans = PositionSelectionService.getSelection();
        return ResultUtil.querySuccessFull(positionSelectionBeans);
    }
}
