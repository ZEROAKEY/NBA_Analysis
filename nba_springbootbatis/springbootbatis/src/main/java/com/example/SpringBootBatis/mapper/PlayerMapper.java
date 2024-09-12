package com.example.SpringBootBatis.mapper;

import com.example.SpringBootBatis.bean.dataBase.PlayerBean;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PlayerMapper {
    /**
     * 根据不同的参数查询球员列表。每个参数都是可选的。
     *
     * @param team_id 队伍ID（可选）
     * @param player_name 球员名字（可选）
     * @param team_name 球队名字（可选）
     * @return 符合条件的球员列表
     */
    List<PlayerBean> getPlayersByEachParameter(Integer team_id, String player_name, String team_name);
}
