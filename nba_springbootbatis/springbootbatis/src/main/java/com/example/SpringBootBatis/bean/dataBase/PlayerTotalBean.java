package com.example.SpringBootBatis.bean.dataBase;

import lombok.Data;

@Data
public class PlayerTotalBean {
    private String seasonId;
    private String seasonType;
    private String playerId;
    private String CnName;
    private String enName;
    private String teamId;
    private String teamName;
    private String points;
    private String pointsPG;
    private String fgAttempted;
    private String fgAttemptedPG;
    private String fgPCT;
    private String threesAttempted;
    private String threesAttemptedPG;
    private String threesMade;
    private String threesMadePG;
    private String ftAttempted;
    private String ftAttemptedPG;
    private String ftPCT;
    private String rebounds;
    private String reboundsPG;
    private String offensiveRebounds;
    private String offensiveReboundsPG;
    private String defensiveRebounds;
    private String defensiveReboundsPG;
    private String assists;
    private String assistsPG;
    private String steals;
    private String stealsPG;
    private String blocks;
    private String blocksPG;
    private String turnovers;
    private String turnoversPG;
    private String fouls;
    private String foulsPG;
    private String games;
    private String minutes;
    private String minutesPG;
    private String position;
}
