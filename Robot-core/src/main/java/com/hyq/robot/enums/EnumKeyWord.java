package com.hyq.robot.enums;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author nanke
 * @date 2020/4/4 上午2:20
 */
public enum EnumKeyWord {
    /**
     * 查询?双梦?踏云
     */
    GROUP_SELECT(1,2,"查询"),
    /**
     * 副本排单助手
     */
    GROUP_KAITUAN(1,6,"开团"),
    GROUP_CANCEL_KAITUAN(1,7,"取消开团"),
    GROUP_SEE_KAITUAN(1,8,"查看团队"),
    GROUP_BAOMING(1,9,"报名"),
    GROUP_JIAOHUAN(1,10,"交换"),
    GROUP_CANCEL_BAOMING(1,11,"取消报名"),
    /**
     * 开服监控
     */
    GROUP_KFJK(1,14,"开服监控"),
    GROUP_QXJK(1,15,"取消监控"),
    GROUP_KFCX(1,16,"开服查询"),
    /**
     * 私聊口令
     */
    PRIVATE_DEFAULT(2,1,""),
    PRIVATE_SELECT(2,2,"查询"),
    ;

    /**
     * 类型
     * 1\群聊 2\私聊
     */
    public Integer type;
    /**
     * 关键码
     */
    public Integer code;
    /**
     * 关键词
     */
    public String keyWord;

    EnumKeyWord(Integer type, Integer code, String keyWord) {
        this.type = type;
        this.code = code;
        this.keyWord = keyWord;
    }

    /**
     * 群聊关键词匹配
     * @param keyWord
     * @return
     */
    public static EnumKeyWord groupFind(String keyWord) {

        List<EnumKeyWord> collect = Arrays.stream(EnumKeyWord.values()).filter(e -> e.type.equals(1)).collect(Collectors.toList());
        for (EnumKeyWord value : collect) {
//            if (keyWord.matches(value.keyWord + ".*")) {
//                return value;
//            }
            if (keyWord.equals(value.keyWord)) {
                return value;
            }
        }
        return null;
    }

    /**
     * 私聊关键词匹配
     * @param keyWord
     * @return
     */
    public static EnumKeyWord privateFind(String keyWord) {

        List<EnumKeyWord> collect = Arrays.stream(EnumKeyWord.values()).filter(e -> e.type.equals(2)).collect(Collectors.toList());
        for (EnumKeyWord value : collect) {
            if (keyWord.matches(value.keyWord + ".*"))
                return value;
        }
        return EnumKeyWord.PRIVATE_DEFAULT;
    }
}
