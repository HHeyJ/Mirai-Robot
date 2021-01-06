
package com.hyq.robot.DO;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
* @author nanke
* @date 2021-1-6
* 致终于来到这里的勇敢的人:
* 永远不要放弃！永远不要对自己失望！永远不要逃走辜负了自己。
* 永远不要哭啼！永远不要说再见！永远不要说慌来伤害目己。
*/
@Data
public class OpenServiceSubscribeDO implements Serializable {

    /**
     * 记录ID
     */
	private Long id;
    /**
     * 主服务ID
     */
	private Long mainId;
    /**
     * QQ
     */
	private Long qq;
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

