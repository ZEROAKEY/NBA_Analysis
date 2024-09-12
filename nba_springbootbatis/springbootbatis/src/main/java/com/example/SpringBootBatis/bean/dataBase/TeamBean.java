package com.example.SpringBootBatis.bean.dataBase;

import lombok.Data;

@Data
public class TeamBean {
    private String team_name;               // 球队名称
    private String games_played;            // 比赛场次
    private String minutes_played;          // 上场分钟数
    private String field_goals;             // 投篮命中数
    private String field_goals_attempted;   // 投篮出手数
    private String field_goal_percentage;   // 投篮命中率
    private String three_pointer;           // 三分球命中数
    private String three_pointer_attempted; // 三分球出手数
    private String three_pointer_percentage;// 三分球命中率
    private String two_pointer;             // 两分球命中数
    private String two_pointer_attempted;   // 两分球出手数
    private String two_pointer_percentage;  // 两分球命中率
    private String free_throws;             // 罚球命中数
    private String free_throws_attempted;   // 罚球出手数
    private String free_throw_percentage;   // 罚球命中率
    private String offensive_rebounds;      // 进攻篮板
    private String defensive_rebounds;      // 防守篮板
    private String total_rebounds;          // 总篮板
    private String assists;                 // 助攻
    private String steals;                  // 抢断
    private String blocks;                  // 盖帽
    private String turnovers;               // 失误
    private String personal_fouls;          // 犯规
    private String points;                  // 总得分
    private Integer season_year;            // 赛季年份
    private Integer team_id;                // 球队 ID
    private String team_location;           // 球队所在地
    private String team_alias;              // 球队别名
    private String seasons;                 // 球队参与的赛季（可能以字符串形式记录）
    private String record;                  // 球队战绩（例如 "50-32"）
    private Integer playoff_appearances;    // 季后赛次数
    private Integer championships_won;      // 冠军次数
    private String team_logo;               // 球队标志
}
