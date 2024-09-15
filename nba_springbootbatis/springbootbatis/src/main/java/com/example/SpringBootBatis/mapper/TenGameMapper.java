package com.example.SpringBootBatis.mapper;
import java.time.LocalDate;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.SpringBootBatis.bean.dataBase.TenGameBean;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface TenGameMapper {
    List<TenGameBean> getLastTenGameInformation(@Param("currentDate") LocalDate currentDate);
    List<TenGameBean> getNextTenGameInformation(@Param("currentDate") LocalDate currentDate);
}
