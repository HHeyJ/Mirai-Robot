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
import net.mamoe.mirai.message.FriendMessageEvent;
import net.mamoe.mirai.message.data.Message;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.PlainText;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author nanke
 * @date 2020/6/28 下午2:22
 */
@Service
public class FriendListener extends SimpleListenerHost {

    @Resource
    private MessageFactory messageFactory;

    /**
     * Java方法级别注解,标注一个方法为事件监听器
     * @param event
     */
    @EventHandler
    public void onMessage(FriendMessageEvent event) {

        MessageChain messageChain = event.getMessage();
        Message plainText = messageChain.first(PlainText.Key);
        if (plainText != null) {
            // 关键词检索
            EnumKeyWord ruleEnum = EnumKeyWord.privateFind(MessageUtil.getKeybyWord(plainText.contentToString(),1));
            if (ruleEnum == null) {
                return ;
            }
            // 会话处理器
            MessageFacade messageFacade = messageFactory.get(ruleEnum);
            messageFacade.execute(event.getSender(),null,plainText);
        }

    }

    @Override
    public void handleException(@NotNull CoroutineContext context, @NotNull Throwable exception) {
        /**
         * 异常处理方式
         * 先直接打印堆栈吧～
         */
        RobotStar.bot.getFriend(CommonConstant.errorSendId).sendMessage("私聊消息处理错误!" + exception.getMessage());
    }
}
