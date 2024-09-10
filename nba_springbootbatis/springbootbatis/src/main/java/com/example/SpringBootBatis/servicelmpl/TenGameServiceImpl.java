package com.example.SpringBootBatis.servicelmpl;

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
        return tenGameMapper.getLastTenGameInformation();
    }

    @Override
    public List<TenGameBean> getNextTenGames() {
        return tenGameMapper.getNextTenGameInformation();
    }
}
