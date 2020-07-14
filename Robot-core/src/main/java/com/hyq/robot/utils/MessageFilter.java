package com.hyq.robot.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * @author nanke
 * @date 2020/4/12 上午10:57
 */
public class MessageFilter {

    public static boolean filter(String content) {

        if (StringUtils.isBlank(content)
                || content.length() > 128 || content.contains("代充") || content.contains("呆充")
                || content.contains("杂货铺") || content.contains("招募") || content.contains("经验+3")
                || content.contains("代售") || content.contains("扶摇九天") || content.contains("桃宝")
                ) {
            return false;
        }
        if (!content.contains("出") && !content.contains("收"))
            return false;
        return true;
    }
}
