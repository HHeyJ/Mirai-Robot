package com.hyq.robot.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @author nanke
 * @date 2020/4/9 下午4:52
 */
public class MessageUtil {

    /**
     * 关键字提取器
     * @param content
     * @return
     */
    public static String trim(String content) {
        if (StringUtils.isBlank(content))
            return null;
        return content.replace(" ","");
    }

    /**
     * 系统分隔符替换
     * @param content
     * @return
     */
    public static String separator(String content) {
        if (StringUtils.isBlank(content))
            return null;
        return Objects.requireNonNull(trim(content)).replace("？", "?");
    }

    /**
     * 关键词分割
     * @param content
     * @return
     */
    public static ArrayList<String> split(String content) {
        if (StringUtils.isBlank(content))
            return new ArrayList<>();
        String[] split = Objects.requireNonNull(separator(content)).split("\\?");
        return new ArrayList(Arrays.asList(split));
    }

    /**
     * 获取一级关键词
     * @param content
     * @return
     */
    public static String getKeybyWord(String content, Integer level) {
        if (StringUtils.isBlank(content))
            return "";
        List<String> split = split(content);
        if (split.isEmpty() || split.size() < level)
            return "";
        return split.get(level - 1);
    }
}
