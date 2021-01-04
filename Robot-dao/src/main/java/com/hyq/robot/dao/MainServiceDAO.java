package com.hyq.robot.dao;

import com.hyq.robot.DO.MainServiceDO;
import com.hyq.robot.query.MainServiceQuery;

import java.util.List;

/**
* @author nanke
* @date 2021-1-4
* 致终于来到这里的勇敢的人:
* 永远不要放弃！永远不要对自己失望！永远不要逃走辜负了自己。
* 永远不要哭啼！永远不要说再见！永远不要说慌来伤害目己。
*/
public interface MainServiceDAO {

    /**
    * 条件查询
    * @param query
    * @return
    */
    List<MainServiceDO> queryByCondition(MainServiceQuery query);

    /**
    * 主键更新
    * @param updateDO
    */
    void updateById(MainServiceDO updateDO);

    /**
    * 条件查询总数
    * @return
    */
    Long countByCondition(MainServiceQuery query);

}