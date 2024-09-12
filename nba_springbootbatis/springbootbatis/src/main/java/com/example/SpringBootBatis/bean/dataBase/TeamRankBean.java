package com.example.SpringBootBatis.bean.dataBase;

import lombok.Data;

/**
 * @author 37472
 * @Description Team Rank Information
 * @date 2021/06/08 23:46
 * @Version 1.0
 **/
@Data
public class TeamRankBean {
    private String team_id;               // 球队 ID

    // 每场比赛得分相关信息
    private String points_per_game;       // 场均得分
    private String points_per_game_rank;  // 场均得分排名
    private String points_per_game_max;   // 场均得分最大值
    private String points_per_game_avg;   // 场均得分平均值

    // 每场比赛篮板相关信息
    private String rebounds_per_game;     // 场均篮板
    private String rebounds_per_game_rank;// 场均篮板排名
    private String rebounds_per_game_max; // 场均篮板最大值
    private String rebounds_per_game_avg; // 场均篮板平均值

    // 每场比赛助攻相关信息
    private String assists_per_game;      // 场均助攻
    private String assists_per_game_rank; // 场均助攻排名
    private String assists_per_game_max;  // 场均助攻最大值
    private String assists_per_game_avg;  // 场均助攻平均值

    // 每场比赛抢断相关信息
    private String steals_per_game;       // 场均抢断
    private String steals_per_game_rank;  // 场均抢断排名
    private String steals_per_game_max;   // 场均抢断最大值
    private String steals_per_game_avg;   // 场均抢断平均值

    // 每场比赛盖帽相关信息
    private String blocks_per_game;       // 场均盖帽
    private String blocks_per_game_rank;  // 场均盖帽排名
    private String blocks_per_game_max;   // 场均盖帽最大值
    private String blocks_per_game_avg;   // 场均盖帽平均值
}
