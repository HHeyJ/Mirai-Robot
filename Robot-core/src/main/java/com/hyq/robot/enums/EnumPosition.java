package com.hyq.robot.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * @author nanke
 * @date 2020/7/22 下午8:28
 *
 * 报名职业枚举
 */
public enum EnumPosition {

    YANTIAN("衍天 演员","https://img.jx3box.com/image/xf/10615.png","内功"),
    LINXUE("凌雪 凌血","https://img.jx3box.com/image/xf/10585.png","外功"),
    PENGLAI("蓬莱","https://img.jx3box.com/image/xf/10533.png","外功"),
    BADAO("霸刀","https://img.jx3box.com/image/xf/10464.png","外功"),
    CHANGGE("莫问 长歌","https://img.jx3box.com/image/xf/10447.png","内功"),
    NAIGE("奶歌 奶鸽 相知","https://img.jx3box.com/image/xf/10448.png","奶妈"),
    FENSHAN("分山 苍云 岔劲 分山劲","https://img.jx3box.com/image/xf/10390.png","外功"),
    TIEGU("铁骨","https://img.jx3box.com/image/xf/10389.png","坦克"),
    GAIBANG("丐帮","https://img.jx3box.com/image/xf/10268.png","外功"),
    FENGYING("焚影 明教","https://img.jx3box.com/image/xf/10242.png","内功"),
    MINGZUN("明尊","https://img.jx3box.com/image/xf/10243.png","坦克"),
    TIANLUO("田螺 天罗","https://img.jx3box.com/image/xf/10225.png","内功"),
    JINGYU("惊羽 唐门","https://img.jx3box.com/image/xf/10224.png","外功"),
    DUJING("毒经 五毒","https://img.jx3box.com/image/xf/10175.png","内功"),
    NAIDU("奶毒 补天","https://img.jx3box.com/image/xf/10176.png","奶妈"),
    CANGJIAN("藏剑","https://img.jx3box.com/image/xf/10144.png","外功"),
    AOXUE("傲血 傲雪","https://img.jx3box.com/image/xf/10026.png","外功"),
    TIELAO("铁牢","https://img.jx3box.com/image/xf/10062.png","坦克"),
    JIANCHUN("剑纯 备胎","https://img.jx3box.com/image/xf/10015.png","外功"),
    QICHUN("气纯","https://img.jx3box.com/image/xf/10014.png","内功"),
    BINGXIN("冰心 法王","https://img.jx3box.com/image/xf/10081.png","内功"),
    NAIXIU("奶秀","https://img.jx3box.com/image/xf/10080.png","奶妈"),
    HUAJIAN("花间","https://img.jx3box.com/image/xf/10021.png","内功"),
    NAIHUA("奶花 离经","https://img.jx3box.com/image/xf/10028.png","奶妈"),
    YIJING("易筋 和尚","https://img.jx3box.com/image/xf/10003.png","内功"),
    XISUI("洗髓","https://img.jx3box.com/image/xf/10002.png","坦克"),
    HAOLAI("号来","https://img.jx3box.com/image/xf/0.png",""),
    ;

    /**
     * 职业
     */
    public String position;
    /**
     * 职业图标
     */
    public String color;
    /**
     * 职业类型
     */
    public String type;

    EnumPosition(String position, String color, String type) {
        this.position = position;
        this.color = color;
        this.type = type;
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
