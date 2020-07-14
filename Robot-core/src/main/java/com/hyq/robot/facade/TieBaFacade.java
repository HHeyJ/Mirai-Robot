package com.hyq.robot.facade;

import com.hyq.robot.DO.BarPostDO;
import com.hyq.robot.client.CreeperClient;
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
public class TieBaFacade {

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
     * 拼接回复内容
     * @param dos
     * @return
     */
    public static List<String> wrapContent(List<BarPostDO> dos) {

        List<String> resultList = new ArrayList<>();
        if (CollectionUtils.isEmpty(dos))
            return resultList;

        dos.stream().sorted(Comparator.comparing(BarPostDO::getFloorId))
                .forEach(barPostDO -> resultList.add(barPostDO.getFloorId() + "楼:" + barPostDO.getContent()));
        return resultList;
    }

    /**
     * 拼接回复内容
     * @param contentList
     * @param floorList
     * @return
     */
    private static List<String> wrapContent(List<Element> contentList, List<Element> floorList) {

        List<String> resultList = new ArrayList<>();
        // 拼接
        for (int i = 0; i < contentList.size() && i < floorList.size(); i++) {
            String str = floorList.get(i).text() + ":" + contentList.get(i).text();
            resultList.add(str);
        }
        return resultList;
    }
}
