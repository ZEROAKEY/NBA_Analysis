package com.example.SpringBootBatis.servicelmpl;

import com.example.SpringBootBatis.bean.dataBase.PlayerBean;
import com.example.SpringBootBatis.mapper.PlayerMapper;
import com.example.SpringBootBatis.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 整合mapper（dao）和service
 */
@Component
public class PlayerServiceImpl implements PlayerService {

    @Autowired
    private PlayerMapper playerMapper;

    /**
     * 根据不同的参数查询球员列表。每个参数都是可选的。
     *
     * @param team_id 队伍ID（可选）
     * @param player_name 球员名字（可选）
     * @param team_name 球队名字（可选）
     * @return 符合条件的球员列表
     */
    @Override
    public List<PlayerBean> getPlayersByEachParameter(Integer team_id, String player_name, String team_name) {
        return playerMapper.getPlayersByEachParameter(team_id,player_name,team_name);
    }
}
