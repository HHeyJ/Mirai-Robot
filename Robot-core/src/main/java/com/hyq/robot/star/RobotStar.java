package com.hyq.robot.star;

import com.hyq.robot.listener.GroupListener;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.BotFactoryJvm;
import net.mamoe.mirai.event.Events;
import net.mamoe.mirai.utils.BotConfiguration;
import net.mamoe.mirai.utils.SystemDeviceInfoKt;

import java.io.File;

/**
 * @author nanke
 * @date 2020/6/28 上午11:48
 */
public class RobotStar {

    public static Bot bot = null;

    public static final Long QQ = 3420204519L;
    public static final String PASSWORD = "";

    static {
        // 机器人
        bot = BotFactoryJvm.newBot(QQ, PASSWORD, new BotConfiguration() {{
            // 设备缓存信息
            setDeviceInfo(context -> SystemDeviceInfoKt.loadAsDeviceInfo(new File(RobotStar.QQ + "L.json"), this.getJson(),context));
        }});
        // 登陆
        bot.login();
    }

    public static void star(GroupListener groupListener) {

        /**
         * 事件监听器注册
         */
        Events.registerEvents(bot,groupListener);
        /**
         * 挂载该机器人的协程
         */
        bot.join();
    }
}
