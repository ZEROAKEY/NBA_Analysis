package com.example.SpringBootBatis.servicelmpl;

import java.time.LocalDate;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.SpringBootBatis.bean.dataBase.TenGameBean;
import com.example.SpringBootBatis.mapper.TenGameMapper;
import com.example.SpringBootBatis.service.TenGameService;
@Component
public class TenGameServiceImpl implements TenGameService{
    
    @Autowired
    private TenGameMapper tenGameMapper;

    @Override
    public List<TenGameBean> getLastTenGames() {
        LocalDate currentDate = LocalDate.now();
        return tenGameMapper.getLastTenGameInformation(currentDate);
    }

    @Override
    public List<TenGameBean> getNextTenGames() {
        LocalDate currentDate = LocalDate.now();
        return tenGameMapper.getNextTenGameInformation(currentDate);
    }
}
