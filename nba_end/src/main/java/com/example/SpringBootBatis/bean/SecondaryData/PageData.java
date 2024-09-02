package com.example.SpringBootBatis.bean.SecondaryData;

import com.example.SpringBootBatis.bean.dataBase.UserBean;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PageData<T> {
    private List<T> pageData;
    private int pageNum;
    private int totalPage;
    private int totalNum;
}
