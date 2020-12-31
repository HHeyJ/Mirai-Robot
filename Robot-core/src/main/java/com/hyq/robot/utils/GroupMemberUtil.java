package com.hyq.robot.utils;

import com.hyq.robot.DO.TeamMemberDO;
import com.hyq.robot.constants.CommonConstant;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author nanke
 * @date 2020/7/22 下午7:49
 */
public class GroupMemberUtil {

    public static ArrayList<Long> locationList = new ArrayList(
            Arrays.asList(
                    11L,12L,13L,14L,15L,
                    21L,22L,23L,24L,25L,
                    31L,32L,33L,34L,35L,
                    41L,42L,43L,44L,45L,
                    51L,52L,53L,54L,55L));

    /**
     * 根据团队成员替换HTML中初始元素
     * @param teamName
     * @param memberDOS
     * @return
     */
    public static String replaceMember(String teamName,List<TeamMemberDO> memberDOS) {
        String originStr = CommonConstant.htmlStr.replace("开团标题",teamName);
        for (TeamMemberDO memberDO : memberDOS) {
            originStr = originStr
                    .replace("图片" + memberDO.getLocation(),memberDO.getColor())
                    .replace("成员" + memberDO.getLocation(),memberDO.getMemberName());
        }
        String htmlStr = replaceInit(teamName, originStr);
        return htmlStr;
    }

    /**
     * 替换海报中所有初始值
     * @param teamName
     * @return
     */
    public static String replaceInit(String teamName, String htmlStr) {

        return htmlStr
                .replace("开团标题",teamName)

                .replace("成员11","")
                .replace("图片11",CommonConstant.blankImg)
                .replace("成员12","")
                .replace("图片12",CommonConstant.blankImg)
                .replace("成员13","")
                .replace("图片13",CommonConstant.blankImg)
                .replace("成员14","")
                .replace("图片14",CommonConstant.blankImg)
                .replace("成员15","")
                .replace("图片15",CommonConstant.blankImg)

                .replace("成员21","")
                .replace("图片21",CommonConstant.blankImg)
                .replace("成员22","")
                .replace("图片22",CommonConstant.blankImg)
                .replace("成员23","")
                .replace("图片23",CommonConstant.blankImg)
                .replace("成员24","")
                .replace("图片24",CommonConstant.blankImg)
                .replace("成员25","")
                .replace("图片25",CommonConstant.blankImg)

                .replace("成员31","")
                .replace("图片31",CommonConstant.blankImg)
                .replace("成员32","")
                .replace("图片32",CommonConstant.blankImg)
                .replace("成员33","")
                .replace("图片33",CommonConstant.blankImg)
                .replace("成员34","")
                .replace("图片34",CommonConstant.blankImg)
                .replace("成员35","")
                .replace("图片35",CommonConstant.blankImg)

                .replace("成员41","")
                .replace("图片41",CommonConstant.blankImg)
                .replace("成员42","")
                .replace("图片42",CommonConstant.blankImg)
                .replace("成员43","")
                .replace("图片43",CommonConstant.blankImg)
                .replace("成员44","")
                .replace("图片44",CommonConstant.blankImg)
                .replace("成员45","")
                .replace("图片45",CommonConstant.blankImg)

                .replace("成员51","")
                .replace("图片51",CommonConstant.blankImg)
                .replace("成员52","")
                .replace("图片52",CommonConstant.blankImg)
                .replace("成员53","")
                .replace("图片53",CommonConstant.blankImg)
                .replace("成员54","")
                .replace("图片54",CommonConstant.blankImg)
                .replace("成员55","")
                .replace("图片55",CommonConstant.blankImg);
    }

    /**
     * 检查输入位置是否正确
     * @param location
     * @return
     */
    public static boolean checkLocation(Long location) {
        return location == null || locationList.contains(location);
    }

    /**
     * 随机获取一个位置
     * @param list
     * @return
     */
    public static Long getLocation(List<Long> list) {

        List<Long> collect = locationList.stream().filter(e -> !list.contains(e)).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(collect)) {
            return collect.get(0);
        }
        return null;
    }
}
