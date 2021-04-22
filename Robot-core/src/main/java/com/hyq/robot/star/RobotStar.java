package com.hyq.robot.star;

import com.hyq.robot.listener.GroupListener;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.BotFactory;
import net.mamoe.mirai.utils.BotConfiguration;
import net.mamoe.mirai.utils.DeviceInfo;

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
        bot = BotFactory.INSTANCE.newBot(QQ, PASSWORD, new BotConfiguration() {{
            // 设备缓存信息
            setDeviceInfo(context -> DeviceInfo.from(new File(RobotStar.QQ + "L.json")));
        }});
        // 登陆
        bot.login();
    }

    public static void star(GroupListener groupListener) {

        /**
         * 事件监听器注册
         */
        bot.getEventChannel().registerListenerHost(groupListener);
        /**
         * 挂载该机器人的协程
         */
        bot.join();
    }
}
