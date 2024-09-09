package com.example.SpringBootBatis.bean.dataBase;

import lombok.Data;

@Data
public class PlayerBean {
    private String playerId;
    private String CnName;
    private String enName;
    private String cnAlias;
    private String birthDate;
    private String teamId;
    private String teamName;
    private String jerseyNum;
    private String position;
    private String height;
    private String weight;
    private String honor;
    private String draftYear;
    private String picFromSIB;
}
