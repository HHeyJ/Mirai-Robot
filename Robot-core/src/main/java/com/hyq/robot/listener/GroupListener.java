package com.hyq.robot.listener;

import com.hyq.robot.constants.CommonConstant;
import com.hyq.robot.enums.EnumKeyWord;
import com.hyq.robot.facade.MessageFactory;
import com.hyq.robot.facade.message.MessageFacade;
import com.hyq.robot.helper.SendHelper;
import com.hyq.robot.repository.AiChatRepository;
import com.hyq.robot.utils.MessageUtil;
import kotlin.coroutines.CoroutineContext;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.SimpleListenerHost;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.message.data.At;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.PlainText;
import net.mamoe.mirai.message.data.SingleMessage;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author nanke
 * @date 2020/7/1 下午12:26
 */
@Service
@Slf4j
public class GroupListener extends SimpleListenerHost {

    @Resource
    private MessageFactory messageFactory;
    @Resource
    private AiChatRepository aiChatRepository;

    @EventHandler
    @SneakyThrows
    public void onMessage(GroupMessageEvent event) {

        /**
         * 消息链
         */
        MessageChain messageChain = event.getMessage();
        SingleMessage one = messageChain.get(1);
        if (one instanceof PlainText || one instanceof At) {
            PlainText plainText = one instanceof PlainText ? (PlainText) one : (PlainText) messageChain.get(2);
            // 关键词检索
            EnumKeyWord ruleEnum = EnumKeyWord.groupFind(MessageUtil.getKeybyWord(plainText.contentToString(),1));
            if (ruleEnum == null) {
                // Ai@聊天
                try {
                    At at = (At) one;
                    long target = at.getTarget();
                    if (CommonConstant.robotQQ.equals(target)) {
                        String s = aiChatRepository.qinYun(plainText.contentToString().replace(" ",""))
                                .replace("菲菲","大哥")
                                .replace("您","你");
                        SendHelper.sendSing(event.getGroup(),new PlainText(s));
                    }
                } catch (Exception e) {}
                return ;
            }
            // 是否拥有权限
            boolean havePower = checkPower(ruleEnum, event);
            if (!havePower) {
                event.getGroup().sendMessage(new At(event.getSender().getId()).plus(new PlainText("爬")));
                return ;
            }
            // 会话处理器
            MessageFacade messageFacade = messageFactory.get(ruleEnum);
            if (messageFacade == null) {
                return ;
            }
            messageFacade.execute(event.getSender(),event.getGroup(),plainText);
        }

    }

    @Override
    public void handleException(@NotNull CoroutineContext context, @NotNull Throwable exception) {
        /**
         * 异常处理方式
         * 给自己发消息
         */
        log.error("群聊消息处理错误!",exception);
    }

    /**
     * 需要校验权限的关键字
     * @param keyWord
     * @return
     */
    private boolean checkPower(EnumKeyWord keyWord, GroupMessageEvent event) {

        if (keyWord == EnumKeyWord.GROUP_CANCEL_KAITUAN
                || keyWord == EnumKeyWord.GROUP_KAITUAN) {
            return event.getPermission().getLevel() > 0 || event.getSender().getId() == 1154685452L;
        }
        return true;
    }
}
