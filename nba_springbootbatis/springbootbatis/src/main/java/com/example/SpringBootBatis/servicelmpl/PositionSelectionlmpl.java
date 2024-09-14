package com.example.SpringBootBatis.servicelmpl;

import com.example.SpringBootBatis.bean.dataBase.PositionSelectionBean;
import com.example.SpringBootBatis.mapper.PositionSelectionMapper;
import com.example.SpringBootBatis.service.PositionSelectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PositionSelectionlmpl implements PositionSelectionService {
    @Autowired
    private PositionSelectionMapper PositionSelectionMapper;
    @Override
    public List<PositionSelectionBean> getSelection(){ return PositionSelectionMapper.getPosition(); }
}
