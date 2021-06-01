package com.hyq.robot.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author nanke
 * @date 2021/1/6 下午5:17
 * 致终于来到这里的勇敢的人:
 * 永远不要放弃！永远不要对自己失望！永远不要逃走辜负了自己。
 * 永远不要哭啼！永远不要说再见！永远不要说慌来伤害目己。
 */
public class DateUtil {

    private static final SimpleDateFormat YMDHMS =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private static final SimpleDateFormat YMD =  new SimpleDateFormat("yyyy-MM-dd");

    public static String toYMDHMS(Date date) {
        try {
            return YMDHMS.format(date);
        } catch (Exception e) {
            return "";
        }
    }

    public static String toYMD(Date date) {
        try {
            return YMD.format(date);
        } catch (Exception e) {
            return "";
        }
    }

}
