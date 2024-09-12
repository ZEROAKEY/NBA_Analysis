package com.example.SpringBootBatis.mapper;

import com.example.SpringBootBatis.bean.dataBase.PlayerTotalSelectionBean;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PlayerTotalSelectionMapper {
    List<PlayerTotalSelectionBean> getSelection();
}
