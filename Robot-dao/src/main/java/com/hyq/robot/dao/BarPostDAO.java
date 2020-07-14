package com.hyq.robot.dao;

import com.hyq.robot.DO.BarPostDO;
import com.hyq.robot.query.BarPostQuery;

import java.util.List;

/**
* @author nanke
* @date 2020/4/4
*/
public interface BarPostDAO {

    /**
     * 批量插入
     * @param dos
     */
    void insertBatch(List<BarPostDO> dos);
    /**
     * 条件查询,默认分页
     * @param query
     * @return
     */
    List<BarPostDO> queryByCondition(BarPostQuery query);
}
