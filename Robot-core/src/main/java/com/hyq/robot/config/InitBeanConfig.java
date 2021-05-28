package com.hyq.robot.config;

import com.google.common.eventbus.AsyncEventBus;
import com.hyq.robot.constants.CommonConstant;
import com.hyq.robot.listener.GroupListener;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.BotFactory;
import net.mamoe.mirai.utils.BotConfiguration;
import net.mamoe.mirai.utils.DeviceInfo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.File;
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

    @Resource
    private GroupListener groupListener;
    @Resource
    private Bot bot;

    @Bean
    public AsyncEventBus asyncEventBus() {
        return new AsyncEventBus(Executors.newFixedThreadPool(5));
    }

    @Bean
    public Bot mirai() {
        // 机器人
        Bot bot = BotFactory.INSTANCE.newBot(CommonConstant.robotQQ, CommonConstant.robotPassword, new BotConfiguration() {{
            // 设备缓存信息
            setDeviceInfo(context -> DeviceInfo.from(new File(CommonConstant.robotQQ + "L.json")));
            setProtocol(MiraiProtocol.ANDROID_PAD);
        }});
        bot.getEventChannel().registerListenerHost(groupListener);
        return bot;
    }

    @PostConstruct
    public void login() {
        bot.login();
    }
}
