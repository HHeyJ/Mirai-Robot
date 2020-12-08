package com.hyq.robot.enums;

/**
 * @author nanke
 * @date 2020/7/22 下午8:28
 *
 * 报名职业枚举
 */
public enum EnumPosition {

    // 深紫罗兰色
    YANTIAN("衍天","#9400D3"),
    // 猩红
    LINXUE("凌雪","#DC143C"),
    // 薰衣草淡紫
    PENGLAI("蓬莱","#E6E6FA"),
    // 适中的板岩暗蓝灰色
    BADAO("霸刀","#7B68EE"),
    // 绿玉/碧绿
    CHANGGE("莫问","#7FFFAA"),
    NAIGE("奶歌","#7FFFAA"),
    // 黄土赭色
    FENSHAN("分山","#A0522D"),
    TIEGU("铁骨","#A0522D"),
    // 草坪绿
    GAIBANG("丐帮","#7CFC00"),
    // 深橙色
    FENGYING("焚影","#FF8C00"),
    MINGZUN("明尊","#FF8C00"),
    // 深绿宝石
    TIANLUO("田螺","#00CED1"),
    JINGYU("惊羽","#00CED1"),
    // 皇家蓝
    DUJING("毒经","#4169E1"),
    NAIDU("奶毒","#4169E1"),
    // 纯黄
    CANGJIAN("藏剑","#FFFF00"),
    // 纯红
    AOXUE("傲血","#FF0000"),
    TIELAO("铁牢","#FF0000"),
    // 深天蓝
    JIANCHUN("剑纯","#00BFFF"),
    QICHUN("气纯","#00BFFF"),
    // 粉红
    BINGXIN("冰心","#FF69B4"),
    NAIXIU("奶秀","#FF69B4"),
    // 深紫罗兰的蓝色
    HUAJIAN("花间","#8A2BE2"),
    NAIHUA("奶花","#8A2BE2"),
    // 卡其色
    YIJING("易筋","#F0E68C"),
    XISUI("洗髓","#F0E68C"),
    // 灰色
    HAOLAI("号来","#808080"),
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
