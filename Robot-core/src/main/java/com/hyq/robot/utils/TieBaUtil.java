package com.hyq.robot.utils;

import com.hyq.robot.DO.BarPostDO;
import com.hyq.robot.client.CreeperClient;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author nanke
 * @date 2020/7/14 下午6:54
 */
public class TieBaUtil {

    /**
     * 获取帖子总页数
     * @param url
     * @return
     */
    public static Long queryPageNo(String url, String host, Integer port) {

        Document document = CreeperClient.getHtmlDocument(url,host,port);
        List<Element> select = document.select("span[class=\"red\"]");
        Set<Long> set = new HashSet<>();
        for (Element element : select) {
            try {
                set.add(Long.valueOf(element.text()));
            } catch (Exception e) {}
        }

        Long pageNo = 1L;
        if (set.contains(0L) || set.contains(1L))
            return pageNo;
        return set.stream().sorted().limit(1).collect(Collectors.toList()).get(0);
    }

    /**
     * 获取帖子数据
     * @param url
     * @return
     */
    public static Map<String, List<Object>> handle(String url, String host, Integer port) {
        // 进行爬取并接收数据
        Document document = CreeperClient.getHtmlDocument(url,host,port);
        // 获取帖子中用户回复的内容
        List<Element> contentList = document.select("div[id~=post_content_(-?\\d*)(\\.\\d+)?]");
        List<Element> floorList = document.select("span[class=tail-info]");

        List<Object> contents = contentList.stream().map(Element::text).collect(Collectors.toList());
        List<Object> floors = floorList.stream().filter(element -> Pattern.compile("楼").matcher(String.valueOf(element)).find())
                .map(e -> Long.valueOf(e.text().replace("楼", ""))).collect(Collectors.toList());

        Map<String,List<Object>> map = new HashMap<>();
        map.put("contentList",contents);
        map.put("floorList",floors);
        return map;
    }

    /**
     * 查询
     * @param content
     * @return
     */
    public static boolean messageFilter(String content) {

        if (StringUtils.isBlank(content)
                || content.length() > 128 || content.contains("代充") || content.contains("呆充")
                || content.contains("杂货铺") || content.contains("招募") || content.contains("经验+3")
                || content.contains("代售") || content.contains("扶摇九天") || content.contains("桃宝")
        ) {
            return false;
        }
        if (!content.contains("出") && !content.contains("收")) {
            return false;
        }
        return true;
    }
}
