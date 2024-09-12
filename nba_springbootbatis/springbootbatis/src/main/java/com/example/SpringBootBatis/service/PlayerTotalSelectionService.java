package com.example.SpringBootBatis.service;

import com.example.SpringBootBatis.bean.dataBase.PlayerTotalSelectionBean;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PlayerTotalSelectionService {
    List<PlayerTotalSelectionBean> getSelection();
}
