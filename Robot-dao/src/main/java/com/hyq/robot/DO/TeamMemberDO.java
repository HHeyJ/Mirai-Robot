package com.hyq.robot.DO;

import lombok.Data;

import java.util.Date;

/**
* @author nanke
* @date 2020/7/22
*/
@Data
public class TeamMemberDO {

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
     * 职业
     */
	private String position;
    /**
     * 填充颜色
     */
	private String color;
    /**
     * 角色名
     */
	private String memberName;
    /**
     * QQ
     */
	private Long qq;
    /**
     * 修改时间
     */
	private Date gmtModify;
    /**
     * 创建时间
     */
	private Date gmtCreate;
    /**
     * 0:否 1:删除
     */
	private Integer delete;

}

