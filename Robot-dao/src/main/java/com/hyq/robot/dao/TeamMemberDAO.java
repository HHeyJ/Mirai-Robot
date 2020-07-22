package com.hyq.robot.dao;

import com.hyq.robot.DO.TeamMemberDO;
import com.hyq.robot.query.TeamMemberQuery;

import java.util.List;

/**
* @author nanke
* @date 2020/7/22
*/
public interface TeamMemberDAO {

    /**
    * 选择性插入
    * @param insertDO
    */
    void insertSelective(TeamMemberDO insertDO);

    /**
     * 根据ID取消报名
     * @param updateDO
     */
    void updateById(TeamMemberDO updateDO);

    /**
    * 条件查询
    * @param query
    * @return
    */
    List<TeamMemberDO> queryByCondition(TeamMemberQuery query);
}
