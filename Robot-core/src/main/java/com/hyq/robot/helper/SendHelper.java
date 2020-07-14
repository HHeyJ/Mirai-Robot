package com.hyq.robot.helper;

import com.hyq.robot.constants.CommonConstant;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.contact.User;
import net.mamoe.mirai.message.data.At;
import net.mamoe.mirai.message.data.Message;
import net.mamoe.mirai.message.data.PlainText;

import java.util.List;

/**
 * @author nanke
 * @date 2020/4/4 上午2:14
 */
public class SendHelper {

    /**
     * 发送消息
     * @param contact 即可以与{@link Bot} 互动的对象. 包含[用户]{@link User}和[群]{@link Group}.
     * @param messages 可发送的或从服务器接收的消息.
     */
    public static void sendSing(Contact contact, Message messages) {

        contact.sendMessage(messages);
    }

    /**
     * 多群批发
     * @param bot 机器人对象. 一个机器人实例登录一个 QQ 账号
     * @param groupIds 群号集合. 需要发送信息的群
     * @param messages 消息集合. 消息集合
     */
    public static void sendGroupBatch(Bot bot, List<Long> groupIds, List<Message> messages) {

        messages.forEach(message -> {
            for (Long groupId : groupIds) {
                try {
                    Group group = bot.getGroup(groupId);
                    group.sendMessage(message);
                } catch (Exception e) {
                    PlainText plainText = new PlainText("群:" + groupId + "未找到。");
                    bot.getFriend(CommonConstant.errorSendId).sendMessage(plainText);
                }
            }
        });
    }

}
