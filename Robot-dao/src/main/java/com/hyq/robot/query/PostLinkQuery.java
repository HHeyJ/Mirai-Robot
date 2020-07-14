package com.hyq.robot.query;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
* @author nanke
* @date 2020/4/12
*/
@Data
@EqualsAndHashCode(callSuper = false)
public class PostLinkQuery extends BaseQuery {

    public PostLinkQuery() {
    }

    public PostLinkQuery(String areaName) {
        this.areaName = areaName;
    }

    /**
     * 主键
     */
	private Long id;
    /**
     * 区服ID
     */
	private Long area;
    /**
     * 区服务名
     */
	private String areaName;
    /**
     * 帖子链接类型
     */
	private Long linkType;
    /**
     * 帖子链接地址
     */
	private String linkUrl;
    /**
     * 0:否 1:删除
     */
	private Integer delete;
}

