package com.hyq.robot.config;

import com.google.common.eventbus.AsyncEventBus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executors;

/**
 * @author nanke
 * @date 2021/1/6 上午10:39
 * 致终于来到这里的勇敢的人:
 * 永远不要放弃！永远不要对自己失望！永远不要逃走辜负了自己。
 * 永远不要哭啼！永远不要说再见！永远不要说慌来伤害目己。
 */
@Configuration
public class InitBeanConfig {

    @Bean
    public AsyncEventBus asyncEventBus() {
        return new AsyncEventBus(Executors.newFixedThreadPool(5));
    }


}
