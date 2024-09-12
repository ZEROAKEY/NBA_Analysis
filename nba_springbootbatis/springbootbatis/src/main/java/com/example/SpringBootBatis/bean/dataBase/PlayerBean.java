package com.example.SpringBootBatis.bean.dataBase;

import lombok.Data;

@Data
public class PlayerBean {
    private String rank;                    // rank (排名)
    private String player_name;             // player_name (球员姓名)
    private String career_start_year;       // career_start_year (生涯开始年份)
    private String career_end_year;         // career_end_year (生涯结束年份)
    private String years_played;            // years_played (生涯年数)
    private String games_played;            // games_played (比赛场次)
    private String minutes_played;          // minutes_played (上场分钟数)
    private String field_goals;             // field_goals (投篮命中数)
    private String field_goals_attempted;   // field_goals_attempted (投篮出手数)
    private String three_pointers;          // three_pointers (三分球命中数)
    private String three_pointers_attempted;// three_pointers_attempted (三分球出手数)
    private String free_throws;             // free_throws (罚球命中数)
    private String free_throws_attempted;   // free_throws_attempted (罚球出手数)
    private String offensive_rebounds;      // offensive_rebounds (进攻篮板)
    private String total_rebounds;          // total_rebounds (总篮板)
    private String assists;                 // assists (助攻)
    private String steals;                  // steals (抢断)
    private String blocks;                  // blocks (盖帽)
    private String turnovers;               // turnovers (失误)
    private String personal_fouls;          // personal_fouls (犯规)
    private String points;                  // points (总得分)
    private String field_goal_percentage;   // field_goal_percentage (投篮命中率)
    private String three_pointer_percentage;// three_pointer_percentage (三分球命中率)
    private String free_throw_percentage;   // free_throw_percentage (罚球命中率)
    private String minutes_per_game;        // minutes_per_game (每场上场分钟数)
    private String points_per_game;         // points_per_game (每场得分)
    private String rebounds_per_game;       // rebounds_per_game (每场总篮板)
    private String assists_per_game;        // assists_per_game (每场助攻)
    private String steals_per_game;         // steals_per_game (每场抢断)
    private String blocks_per_game;         // blocks_per_game (每场盖帽)
    private String team_name;               // team_name (球队名称)
    private Integer team_id;                // team_id (球队ID)
}
