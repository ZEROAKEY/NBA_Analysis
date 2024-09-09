package com.example.SpringBootBatis.bean.dataBase;

import lombok.Data;

@Data
public class TeamBean {
    private String seasonId;
    private String seasonType;
    private String teamId;
    private String CnName;
    private String fullCnName;
    private String city;
    private String conference;
    private String logo;
    private String coach;
    private String venue;
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
    private String recentGameScore;
    private String lastTenGames;
    private String nextTenGames;
}
