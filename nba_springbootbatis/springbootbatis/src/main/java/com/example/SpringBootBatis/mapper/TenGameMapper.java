package com.example.SpringBootBatis.mapper;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.SpringBootBatis.bean.dataBase.TenGameBean;

@Mapper
public interface TenGameMapper {
    List<TenGameBean> getLastTenGameInformation();
    List<TenGameBean> getNextTenGameInformation();
}
