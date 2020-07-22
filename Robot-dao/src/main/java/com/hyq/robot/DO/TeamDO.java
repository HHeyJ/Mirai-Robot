package com.hyq.robot.DO;

import lombok.Data;

import java.util.Date;

/**
* @author nanke
* @date 2020/7/22
*/
@Data
public class TeamDO {

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

