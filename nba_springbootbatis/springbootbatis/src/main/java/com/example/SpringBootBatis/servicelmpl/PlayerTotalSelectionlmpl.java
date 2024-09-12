package com.example.SpringBootBatis.servicelmpl;

import com.example.SpringBootBatis.bean.dataBase.PlayerTotalSelectionBean;
import com.example.SpringBootBatis.mapper.PlayerTotalSelectionMapper;
import com.example.SpringBootBatis.service.PlayerTotalSelectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PlayerTotalSelectionlmpl implements PlayerTotalSelectionService {
    @Autowired
    private PlayerTotalSelectionMapper PlayerTotalSelectionMapper;
    @Override
    public List<PlayerTotalSelectionBean> getSelection(){ return PlayerTotalSelectionMapper.getSelection(); }
}
