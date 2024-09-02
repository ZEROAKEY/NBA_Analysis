package com.example.SpringBootBatis.service;

import com.example.SpringBootBatis.bean.dataBase.PlayerTotalBean;
import com.example.SpringBootBatis.bean.dataBase.UserBean;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 对于controller的操作，与mapper一一对应
 *  * id：账户编号
 *  * username：账户名
 *  * password：密码
 *  * rid：角色类型
 *  * mobile：手机号
 */
@Service
public interface PlayerTotalService {

    /**
     *
     * @param teamId 队伍ID
     * @param teamName 队伍名
     * @param position 位置
     * @param name 姓名
     * @param index 按某一列排序
     * @param seasonId 几几年
     * @param seasonType 那个赛季
     * @return
     */
    List<PlayerTotalBean> getPlayerTotalsByEachParameterAndSortByIndex(String teamId,String teamName,
                                                                       String position,
                                                                       String name,
                                                                       String index,
                                                                       String seasonId,String seasonType);

}
