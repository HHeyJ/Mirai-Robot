package com.hyq.robot.query;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
* @author nanke
* @date 2021-1-6
* 致终于来到这里的勇敢的人:
* 永远不要放弃！永远不要对自己失望！永远不要逃走辜负了自己。
* 永远不要哭啼！永远不要说再见！永远不要说慌来伤害目己。
*/
@Data
@EqualsAndHashCode(callSuper = false)
public class OpenServiceSubscribeQuery extends BaseQuery {

    /**
     * 主服务ID
     */
	private Long mainId;
    /**
     * QQ群ID
     */
	private Long groupId;
    /**
     * 0:否 1:删除
     */
	private Integer delete;

}
