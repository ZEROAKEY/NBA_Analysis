package com.example.SpringBootBatis.bean.SecondaryData;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class FullData<T> {
    private List<T> fullData;
}
