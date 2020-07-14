package com.hyq.robot.query;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
* @author nanke
* @date 2020/4/12
*/
@Data
@EqualsAndHashCode(callSuper = false)
public class InformRelationQuery extends BaseQuery {

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
     * 0:否 1:删除
     */
	private Integer delete;
}

