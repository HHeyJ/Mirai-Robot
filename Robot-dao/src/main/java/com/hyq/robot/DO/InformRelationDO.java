package com.hyq.robot.DO;

import lombok.Data;

import java.util.Date;

/**
* @author nanke
* @date 2020/4/12
*/
@Data
public class InformRelationDO {

    /**
     * 主键
     */
	private Long id;
    /**
     * 帖子链接ID
     */
	private Long postLinkId;
    /**
     * QQ群号
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

