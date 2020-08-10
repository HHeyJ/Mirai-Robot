package com.hyq.robot.listener;

import com.hyq.robot.constants.CommonConstant;
import com.hyq.robot.enums.EnumKeyWord;
import com.hyq.robot.facade.factory.MessageFactory;
import com.hyq.robot.facade.factory.message.MessageFacade;
import com.hyq.robot.star.RobotStar;
import com.hyq.robot.utils.MessageUtil;
import kotlin.coroutines.CoroutineContext;
import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.SimpleListenerHost;
import net.mamoe.mirai.message.GroupMessageEvent;
import net.mamoe.mirai.message.data.*;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author nanke
 * @date 2020/7/1 下午12:26
 */
@Service
public class GroupListener extends SimpleListenerHost {

    @Resource
    private MessageFactory messageFactory;

    @EventHandler
    public void onMessage(GroupMessageEvent event) {

        /**
         * 消息链
         */
        MessageChain messageChain = event.getMessage();
        PlainText plainText = messageChain.first(PlainText.Key);
        if (plainText != null) {
            // 关键词检索
            EnumKeyWord ruleEnum = EnumKeyWord.groupFind(MessageUtil.getKeybyWord(plainText.contentToString(),1));
            if (ruleEnum == null) {
                return ;
            }
            // 会话处理器
            MessageFacade messageFacade = messageFactory.get(ruleEnum);
            messageFacade.execute(event.getSender(),event.getGroup(),plainText);
        }

        FlashImage flashImage = messageChain.first(FlashImage.Key);
        if (flashImage != null) {
            try {
                PlainText remindText = new PlainText(event.getGroup().getId() + "群内" + event.getSender().getId() + "发送了闪照");
                Image image = event.getGroup().uploadImage(new URL(RobotStar.bot.queryImageUrl(flashImage.getImage())));
                RobotStar.bot.getGroup(CommonConstant.informGroupId).sendMessage(remindText.plus(image));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }

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
