package com.example.SpringBootBatis.bean.dataBase;

import lombok.Data;

@Data
public class PlayerTotalBean {
    private String player_name;           // 球员姓名
    private String position;              // 位置
    private String age;                   // 年龄
    private String team;                  // 球队
    private String games_played;          // 比赛场次
    private String games_started;         // 首发场次
    private String minutes_played;        // 上场时间
    private String field_goals;           // 投篮命中数
    private String field_goals_attempted; // 投篮出手数
    private String field_goal_pct;        // 投篮命中率
    private String three_pointer;         // 三分命中数
    private String three_pointer_attempted; // 三分出手数
    private String three_pointer_pct;     // 三分命中率
    private String two_pointer;           // 两分命中数
    private String two_pointer_attempted; // 两分出手数
    private String two_pointer_pct;       // 两分命中率
    private String effective_field_goal_pct; // 有效命中率
    private String free_throws;           // 罚球命中数
    private String free_throws_attempted; // 罚球出手数
    private String free_throw_pct;        // 罚球命中率
    private String offensive_rebounds;    // 进攻篮板
    private String defensive_rebounds;    // 防守篮板
    private String total_rebounds;        // 总篮板
    private String assists;               // 助攻
    private String steals;                // 抢断
    private String blocks;                // 盖帽
    private String turnovers;             // 失误
    private String personal_fouls;        // 犯规
    private String points;                // 总得分
    private String minutes_per_game;      // 每场上场分钟数
    private String points_per_game;       // 每场得分
    private String total_rebounds_per_game; // 每场总篮板
    private String assists_per_game;      // 每场助攻
    private String steals_per_game;       // 每场抢断
    private String blocks_per_game;       // 每场盖帽
    private String team_name;             // 球队名称
    private Integer team_id;              // 球队 ID
    private Integer season_year;          // 赛季年份
}
