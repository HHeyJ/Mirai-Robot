package com.hyq.robot.listener;

import com.hyq.robot.constants.CommonConstant;
import com.hyq.robot.facade.GroupMessageFacade;
import com.hyq.robot.star.RobotStar;
import kotlin.coroutines.CoroutineContext;
import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.SimpleListenerHost;
import net.mamoe.mirai.message.GroupMessageEvent;
import net.mamoe.mirai.message.data.*;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author nanke
 * @date 2020/7/1 下午12:26
 */
@Service
public class GroupListener extends SimpleListenerHost {

    @Resource
    private GroupMessageFacade groupMessageFacade;

    @EventHandler
    public void onMessage(GroupMessageEvent event) {

        /**
         * 消息链
         */
        MessageChain messageChain = event.getMessage();
        PlainText plainText = messageChain.first(PlainText.Key);
        if (plainText != null) {
            groupMessageFacade.execute(event.getSender(),plainText);
        }

//        Image image = messageChain.first(Image.Key);
//        if (image != null) {
//            At at = new At(event.getSender());
//            event.getGroup().sendMessage(at.plus(image));
//        }

    }

    @Override
    public void handleException(@NotNull CoroutineContext context, @NotNull Throwable exception) {
        /**
         * 异常处理方式
         * 给自己发消息
         */
        RobotStar.bot.getFriend(CommonConstant.errorSendId).sendMessage("群聊消息处理错误!" + exception.getMessage());
    }
}
