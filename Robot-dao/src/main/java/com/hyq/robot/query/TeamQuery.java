package com.hyq.robot.query;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
* @author nanke
* @date 2020/7/22
*/
@Data
@EqualsAndHashCode(callSuper = false)
public class TeamQuery extends BaseQuery {

    /**
     * 主键
     */
	private Long id;
    /**
     * 团标题
     */
	private String teamName;
    /**
     * QQ群ID
     */
	private Long groupId;
    /**
     * 0:否 1:删除
     */
	private Integer delete;

}

