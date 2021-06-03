package com.hyq.robot.autojob;

import com.hyq.robot.helper.ApplicationContextHelper;
import com.hyq.robot.helper.SendHelper;
import com.hyq.robot.repository.OneEnglishRepository;
import com.hyq.robot.utils.DateUtil;
import com.hyq.robot.utils.NetworkUtil;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.contact.ContactList;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.message.data.Image;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.PlainText;
import net.mamoe.mirai.utils.ExternalResource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;
import java.io.InputStream;
import java.util.Date;

/**
 * @author nanke
 * @date 2020/4/5 上午12:14
 */
@Configuration
@EnableScheduling
public class GoodMorningTask {

    @Resource
    private OneEnglishRepository oneEnglishRepository;

    @Scheduled(cron = "0 30 8 * * ? ")
    public void task() {

        OneEnglishRepository.ICiBaBody iCiBaBody = oneEnglishRepository.iCiBa();
        String content = iCiBaBody.getContent();
        String note = iCiBaBody.getNote();

        PlainText plainText = new PlainText("早安,今天是" + DateUtil.toYMD(new Date()) + "\n\n" + "●每日一句:\n" + content + "\n" + note + "\n");
        MessageChain plus = null;

        ContactList<Group> groups = ApplicationContextHelper.getBean(Bot.class).getGroups();
        for (Group e : groups) {

            if (StringUtils.isNotBlank(iCiBaBody.getFenxiang_img())) {
                InputStream inputStream = NetworkUtil.getInputStream(iCiBaBody.getFenxiang_img());
                if (inputStream != null) {
                    Image image = ExternalResource.uploadAsImage(inputStream, e);
                    plus = plainText.plus(image);
                }
            } else {
                plus = plainText.plus(new PlainText("【警告】发生了一些预期之外的问题"));
            }
            SendHelper.sendSing(e,plus);
        }

    }
}
