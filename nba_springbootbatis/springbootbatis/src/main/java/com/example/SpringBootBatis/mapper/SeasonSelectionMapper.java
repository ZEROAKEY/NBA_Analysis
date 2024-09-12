package com.example.SpringBootBatis.mapper;

import com.example.SpringBootBatis.bean.dataBase.SeasonSelection;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SeasonSelectionMapper {
    List<SeasonSelection> getSeason();
}
