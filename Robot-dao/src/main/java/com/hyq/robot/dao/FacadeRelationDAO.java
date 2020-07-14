package com.hyq.robot.dao;

import com.hyq.robot.DO.FacadeRelationDO;
import com.hyq.robot.query.FacadeRelationQuery;

import java.util.List;

/**
* @author nanke
* @date 2020/4/12
*/
public interface FacadeRelationDAO {

    /**
     * 选择性插入
     * @param relationDO
     */
    void insertSelective(FacadeRelationDO relationDO);

    /**
     * 条件查询
     * @param query
     * @return
     */
    List<FacadeRelationDO> queryByCondition(FacadeRelationQuery query);

}
