package com.hyq.robot.enums;

/**
 * @author nanke
 * @date 2020/7/22 下午8:28
 *
 * 报名职业枚举
 */
public enum EnumPosition {

    YANTIAN("衍天","#9A32CD"),
    LINXUE("凌雪","#930000"),
    PENGLAI("蓬莱","#C4E1FF"),
    BADAO("霸刀","#5151A2"),
    CHANGGE("莫问","#7AFEC6"),
    NAIGE("奶歌","#7AFEC6"),
    FENSHAN("分山","#F75000"),
    TIEGU("铁骨","#F75000"),
    GAIBANG("丐帮","#C6A300"),
    FENGYING("焚影","#FF8000"),
    MINGZUN("明尊","#FF8000"),
    TIANLUO("田螺","#73BF00"),
    JINGYU("惊羽","#73BF00"),
    DUJING("毒经","#0072E3"),
    NAIDU("奶毒","#0072E3"),
    CANGJIAN("藏剑","#F9F900"),
    AOXUE("傲雪","#FF0000"),
    TIELAO("铁牢","#FF0000"),
    JIANCHUN("剑纯","#46A3FF"),
    QICHUN("气纯","#46A3FF"),
    BINGXIN("冰心","#FF79BC"),
    NAIXIU("奶秀","#FF79BC"),
    HUAJIAN("花间","#6F00D2"),
    NAIHUA("奶花","#6F00D2"),
    YIJING("易筋","#D9B300"),
    XISUI("洗髓","#D9B300"),
    ;

    /**
     * 职业
     */
    public String position;
    /**
     * 颜色
     */
    public String color;

    EnumPosition(String position, String color) {
        this.position = position;
        this.color = color;
    }

    public static EnumPosition get(String position) {
        for (EnumPosition value : EnumPosition.values()) {
            if (value.position.equals(position)) {
                return value;
            }
            continue;
        }
        return null;
    }
}
