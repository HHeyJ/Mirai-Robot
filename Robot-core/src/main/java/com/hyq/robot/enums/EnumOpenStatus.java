package com.hyq.robot.enums;

/**
 * @author nanke
 * @date 2021/1/6 下午3:14
 * 致终于来到这里的勇敢的人:
 * 永远不要放弃！永远不要对自己失望！永远不要逃走辜负了自己。
 * 永远不要哭啼！永远不要说再见！永远不要说慌来伤害目己。
 */
public enum EnumOpenStatus {

    OPEN(1,"开服"),
    CLOSE(0,"维护"),
    ;

    EnumOpenStatus(Integer status, String desc) {
        this.status = status;
        this.desc = desc;
    }

    /**
     * 开服状态
     */
    public Integer status;
    /**
     * 描述
     */
    public String desc;

    public static EnumOpenStatus get(Integer status) {
        for (EnumOpenStatus value : EnumOpenStatus.values()) {
            if (value.status.equals(status)) {
                return value;
            }
        }
        return EnumOpenStatus.CLOSE;
    }

}
