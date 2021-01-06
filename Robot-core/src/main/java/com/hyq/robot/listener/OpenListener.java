package com.hyq.robot.listener;

import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.Subscribe;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * @author nanke
 * @date 2021/1/5 下午9:24
 * 致终于来到这里的勇敢的人:
 * 永远不要放弃！永远不要对自己失望！永远不要逃走辜负了自己。
 * 永远不要哭啼！永远不要说再见！永远不要说慌来伤害目己。
 */
@Component
@Slf4j
public class OpenListener {

    @Resource
    private AsyncEventBus asyncEventBus;

    @PostConstruct
    public void register() {
        asyncEventBus.register(this);
    }

    @Subscribe
    @AllowConcurrentEvents
    public void sendOpenInform(OpenEvent event) {

        // TODO 发送服务器订阅消息
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class OpenEvent {

        /**
         * 主服务器ID
         */
        private Long mainId;
        /**
         * 状态
         */
        private Integer status;

    }
}
