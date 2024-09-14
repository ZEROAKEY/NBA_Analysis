package com.example.SpringBootBatis.servicelmpl;

import com.example.SpringBootBatis.bean.dataBase.SeasonSelectionBean;
import com.example.SpringBootBatis.mapper.SeasonSelectionMapper;
import com.example.SpringBootBatis.service.SeasonSelectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SeasonSelectionlmpl implements SeasonSelectionService {
    @Autowired
    private SeasonSelectionMapper SeasonSelectionMapper;
    @Override
    public List<SeasonSelectionBean> getSeason(){return SeasonSelectionMapper.getSeason();}

}
