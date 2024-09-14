package com.example.SpringBootBatis.mapper;

import com.example.SpringBootBatis.bean.dataBase.PositionSelectionBean;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PositionSelectionMapper {
    List<PositionSelectionBean> getPosition();
}
