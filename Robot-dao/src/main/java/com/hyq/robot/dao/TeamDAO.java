package com.hyq.robot.dao;

import com.hyq.robot.DO.TeamDO;
import com.hyq.robot.query.TeamQuery;

import java.util.List;

/**
* @author nanke
* @date 2020/7/22
*/
public interface TeamDAO {

    /**
    * 选择性插入
    * @param insertDO
    */
    void insertSelective(TeamDO insertDO);

    /**
     * 根据群删除团
     * @param updateDO
     */
    void updateById(TeamDO updateDO);

    /**
    * 条件查询
    * @param query
    * @return
    */
    List<TeamDO> queryByCondition(TeamQuery query);
}
