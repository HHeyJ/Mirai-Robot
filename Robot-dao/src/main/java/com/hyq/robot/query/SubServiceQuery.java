package com.hyq.robot.query;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
* @author nanke
* @date 2021-1-4
* 致终于来到这里的勇敢的人:
* 永远不要放弃！永远不要对自己失望！永远不要逃走辜负了自己。
* 永远不要哭啼！永远不要说再见！永远不要说慌来伤害目己。
*/
@Data
@EqualsAndHashCode(callSuper = false)
public class SubServiceQuery extends BaseQuery {

    /**
     * 子服务ID
     */
	private Long subId;
    /**
     * 主服务ID
     */
	private Long mainId;
    /**
     * 服务器名称
     */
	private String subServiceName;
    /**
     * 0:否 1:删除
     */
	private Integer delete;

}
