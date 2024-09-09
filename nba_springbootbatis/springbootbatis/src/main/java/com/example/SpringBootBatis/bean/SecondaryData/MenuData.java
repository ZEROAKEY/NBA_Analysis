package com.example.SpringBootBatis.bean.SecondaryData;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author LiuDingYin
 * @create 2021 - 06 - 07 23:15
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuData {
    String id;
    String authName;
    String path;
    List<MenuData> children;
}
