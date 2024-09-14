package com.example.SpringBootBatis.service;

import com.example.SpringBootBatis.bean.dataBase.PositionSelectionBean;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PositionSelectionService {
    List<PositionSelectionBean> getSelection();
}
