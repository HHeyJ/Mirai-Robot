package com.hyq.robot.DO;

import lombok.Data;

import java.util.Date;

/**
* @author nanke
* @date 2020/4/4
*/
@Data
public class BarPostDO {

    /**
     * 主键
     */
	private Long id;
    /**
     * 楼层
     */
	private Long floorId;
    /**
     * 楼层内容
     */
	private String content;
    /**
     * 帖子链接
     */
	private String postUrl;
    /**
     * 当前页数
     */
	private Integer pageNo;
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

