package com.hyq.robot.query;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
* @author nanke
* @date 2020/7/22
*/
@Data
@EqualsAndHashCode(callSuper = false)
public class TeamMemberQuery extends BaseQuery {

    /**
     * 主键
     */
	private Long id;
    /**
     * 团ID
     */
	private Long teamId;
    /**
     * 队伍位置
     */
	private Long location;
    /**
     * QQ
     */
	private Long qq;
    /**
     * 0:否 1:删除
     */
	private Integer delete;

}

