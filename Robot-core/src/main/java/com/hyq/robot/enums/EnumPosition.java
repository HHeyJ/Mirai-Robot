package com.hyq.robot.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * @author nanke
 * @date 2020/7/22 下午8:28
 *
 * 报名职业枚举
 */
public enum EnumPosition {

    YANTIAN("衍天 演员","https://img.jx3box.com/image/xf/10615.png"),
    LINXUE("凌雪 凌血","https://img.jx3box.com/image/xf/10585.png"),
    PENGLAI("蓬莱","https://img.jx3box.com/image/xf/10533.png"),
    BADAO("霸刀","https://img.jx3box.com/image/xf/10464.png"),
    CHANGGE("莫问 长歌","https://img.jx3box.com/image/xf/10447.png"),
    NAIGE("奶歌 奶鸽 相知","https://img.jx3box.com/image/xf/10448.png"),
    FENSHAN("分山 苍云 岔劲 分山劲","https://img.jx3box.com/image/xf/10390.png"),
    TIEGU("铁骨","https://img.jx3box.com/image/xf/10389.png"),
    GAIBANG("丐帮","https://img.jx3box.com/image/xf/10268.png"),
    FENGYING("焚影 明教","https://img.jx3box.com/image/xf/10242.png"),
    MINGZUN("明尊","https://img.jx3box.com/image/xf/10243.png"),
    TIANLUO("田螺 天罗","https://img.jx3box.com/image/xf/10225.png"),
    JINGYU("惊羽 唐门","https://img.jx3box.com/image/xf/10224.png"),
    DUJING("毒经 五毒","https://img.jx3box.com/image/xf/10175.png"),
    NAIDU("奶毒 补天","https://img.jx3box.com/image/xf/10176.png"),
    CANGJIAN("藏剑","https://img.jx3box.com/image/xf/10144.png"),
    AOXUE("傲血 傲雪","https://img.jx3box.com/image/xf/10026.png"),
    TIELAO("铁牢","https://img.jx3box.com/image/xf/10062.png"),
    JIANCHUN("剑纯 备胎","https://img.jx3box.com/image/xf/10015.png"),
    QICHUN("气纯","https://img.jx3box.com/image/xf/10014.png"),
    BINGXIN("冰心 法王","https://img.jx3box.com/image/xf/10081.png"),
    NAIXIU("奶秀","https://img.jx3box.com/image/xf/10080.png"),
    HUAJIAN("花间","https://img.jx3box.com/image/xf/10021.png"),
    NAIHUA("奶花 离经","https://img.jx3box.com/image/xf/10028.png"),
    YIJING("易筋 和尚","https://img.jx3box.com/image/xf/10003.png"),
    XISUI("洗髓","https://img.jx3box.com/image/xf/10002.png"),
    HAOLAI("号来","https://img.jx3box.com/image/xf/0.png"),
    ;

    /**
     * 职业
     */
    public String position;
    /**
     * 职业图标
     */
    public String color;

    EnumPosition(String position, String color) {
        this.position = position;
        this.color = color;
    }

    public static EnumPosition get(String position) {

        if (StringUtils.isBlank(position)) {
            return null;
        }

        for (EnumPosition value : EnumPosition.values()) {
            if (value.position.contains(position)) {
                return value;
            }
        }
        return null;
    }
}
