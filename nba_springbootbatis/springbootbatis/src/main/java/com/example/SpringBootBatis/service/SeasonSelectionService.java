package com.example.SpringBootBatis.service;

import com.example.SpringBootBatis.bean.dataBase.SeasonSelection;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SeasonSelectionService {
    List<SeasonSelection> getSeason();
}
