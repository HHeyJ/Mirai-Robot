package com.hyq.robot.DO;

import lombok.Data;

import java.util.Date;

/**
* @author nanke
* @date 2020/4/12
*/
@Data
public class PostLinkDO {

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

