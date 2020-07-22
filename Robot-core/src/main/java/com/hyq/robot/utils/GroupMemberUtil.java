package com.hyq.robot.utils;

import com.hyq.robot.DO.TeamMemberDO;
import com.hyq.robot.constants.CommonConstant;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author nanke
 * @date 2020/7/22 下午7:49
 */
public class GroupMemberUtil {

    public static ArrayList locationList = new ArrayList(
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
                    .replace("颜色" + memberDO.getLocation(),memberDO.getColor())
                    .replace("成员" + memberDO.getLocation(),memberDO.getPosition() + "->>" + memberDO.getMemberName());
        }
        return originStr;
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
                .replace("颜色11","#FDFFFF")
                .replace("成员12","")
                .replace("颜色12","#FDFFFF")
                .replace("成员13","")
                .replace("颜色13","#FDFFFF")
                .replace("成员14","")
                .replace("颜色14","#FDFFFF")
                .replace("成员15","")
                .replace("颜色15","#FDFFFF")

                .replace("成员21","")
                .replace("颜色21","#FDFFFF")
                .replace("成员22","")
                .replace("颜色22","#FDFFFF")
                .replace("成员23","")
                .replace("颜色23","#FDFFFF")
                .replace("成员24","")
                .replace("颜色24","#FDFFFF")
                .replace("成员25","")
                .replace("颜色25","#FDFFFF")

                .replace("成员31","")
                .replace("颜色31","#FDFFFF")
                .replace("成员32","")
                .replace("颜色32","#FDFFFF")
                .replace("成员33","")
                .replace("颜色33","#FDFFFF")
                .replace("成员34","")
                .replace("颜色34","#FDFFFF")
                .replace("成员35","")
                .replace("颜色35","#FDFFFF")

                .replace("成员41","")
                .replace("颜色41","#FDFFFF")
                .replace("成员42","")
                .replace("颜色42","#FDFFFF")
                .replace("成员43","")
                .replace("颜色43","#FDFFFF")
                .replace("成员44","")
                .replace("颜色44","#FDFFFF")
                .replace("成员45","")
                .replace("颜色45","#FDFFFF")

                .replace("成员51","")
                .replace("颜色51","#FDFFFF")
                .replace("成员52","")
                .replace("颜色52","#FDFFFF")
                .replace("成员53","")
                .replace("颜色53","#FDFFFF")
                .replace("成员54","")
                .replace("颜色54","#FDFFFF")
                .replace("成员55","")
                .replace("颜色55","#FDFFFF");
    }

    /**
     * 检查输入位置是否正确
     * @param location
     * @return
     */
    public static boolean checkLocation(Long location) {
        return locationList.contains(location);
    }
}
