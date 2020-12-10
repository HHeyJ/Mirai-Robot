package com.hyq.robot.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author nanke
 * @date 2020/4/9 下午4:52
 */
public class MessageUtil {

    /**
     * 系统分隔符替换
     * @param content
     * @return
     */
    public static String separator(String content) {
        return content.replace("？", "?");
    }

    /**
     * 关键词分割
     * @param content
     * @return
     */
    public static List<String> split(String content) {
        List<String> keyList = Arrays.stream(separator(content).split("\\?")).filter(StringUtils::isNotBlank).collect(Collectors.toList());
        if (keyList.size() <= 1) {
            keyList = Arrays.stream(separator(content).split(" ")).filter(StringUtils::isNotBlank).collect(Collectors.toList());
        }
        return keyList;
    }

    /**
     * 获取一级关键词
     * @param content
     * @return
     */
    public static String getKeybyWord(String content, Integer level) {
        if (StringUtils.isBlank(content)) {
            return "";
        }
        List<String> split = split(content);
        if (split.isEmpty() || split.size() < level)
            return "";
        return split.get(level - 1);
    }

    /**
     * 校验团队序号
     * @param allTeamNum
     * @param teamNumStr
     * @return 0发生异常,-1序号错误
     */
    public static Integer checkTeamNum(Integer allTeamNum, String teamNumStr) {

        try {
            Integer teamNum = Integer.valueOf(teamNumStr);
            if (teamNum > allTeamNum || teamNum < 1) {
                return -1;
            }
            return teamNum;
        } catch (Exception e) {
            return 0;
        }
    }
}
