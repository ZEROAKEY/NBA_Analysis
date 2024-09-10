package com.example.SpringBootBatis.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.SpringBootBatis.bean.dataBase.TenGameBean;

@Service
public interface TenGameService {
    List<TenGameBean> getLastTenGames();
    List<TenGameBean> getNextTenGames();
}
