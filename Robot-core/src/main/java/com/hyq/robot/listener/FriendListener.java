package com.hyq.robot.listener;

import com.hyq.robot.star.RobotStar;
import kotlin.coroutines.CoroutineContext;
import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.SimpleListenerHost;
import net.mamoe.mirai.message.FriendMessageEvent;
import net.mamoe.mirai.message.data.Message;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.PlainText;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

/**
 * @author nanke
 * @date 2020/6/28 下午2:22
 */
@Service
public class FriendListener extends SimpleListenerHost {

    /**
     * Java方法级别注解,标注一个方法为事件监听器
     * @param event
     */
    @EventHandler
    public void onMessage(FriendMessageEvent event) {


        MessageChain messageChain = event.getMessage();
        Message plainText = messageChain.first(PlainText.Key);
        if (plainText != null) {
            event.getSender().sendMessage(new PlainText("滴滴滴"));
        }

    }

    @Override
    public void handleException(@NotNull CoroutineContext context, @NotNull Throwable exception) {
        /**
         * 异常处理方式
         * 先直接打印堆栈吧～
         */
        RobotStar.bot.getFriend(1154685452L).sendMessage("私聊消息处理错误!" + exception.getMessage());
    }
}
