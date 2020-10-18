package com.hyq.robot.listener;

import com.hyq.robot.constants.CommonConstant;
import com.hyq.robot.star.RobotStar;
import kotlin.coroutines.CoroutineContext;
import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.SimpleListenerHost;
import net.mamoe.mirai.event.events.NewFriendRequestEvent;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

/**
 * @author nanke
 * @date 2020/10/17 5:58 下午
 * 致终于来到这里的勇敢的人:
 * 永远不要放弃！永远不要对自己失望！永远不要逃走辜负了自己。
 * 永远不要哭啼！永远不要说再见！永远不要说慌来伤害目己。
 */
@Service
public class FriendRequestListener extends SimpleListenerHost {

    @EventHandler
    public void onMessage(NewFriendRequestEvent event) {
        // 无条件同意好友请求
        event.accept();
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
