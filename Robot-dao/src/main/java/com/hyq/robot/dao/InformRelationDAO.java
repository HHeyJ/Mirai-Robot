package com.hyq.robot.dao;
import com.hyq.robot.DO.InformRelationDO;
import com.hyq.robot.query.InformRelationQuery;

import java.util.List;

/**
* @author nanke
* @date 2020/4/12
*/
public interface InformRelationDAO {

    /**
    * 选择性插入
    * @param insertDO
    */
    void insertSelective(InformRelationDO insertDO);

    /**
    * 条件查询
    * @param query
    * @return
    */
    List<InformRelationDO> queryByCondition(InformRelationQuery query);
}
