package com.hyq.robot.dao;

import com.hyq.robot.DO.PostLinkDO;
import com.hyq.robot.query.PostLinkQuery;

import java.util.List;

/**
* @author nanke
* @date 2020/4/12
*/
public interface PostLinkDAO {

    /**
    * 选择性插入
    * @param insertDO
    */
    void insertSelective(PostLinkDO insertDO);

    /**
    * 条件查询
    * @param query
    * @return
    */
    List<PostLinkDO> queryByCondition(PostLinkQuery query);
}
