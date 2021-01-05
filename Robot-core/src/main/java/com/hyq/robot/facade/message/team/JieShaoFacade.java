package com.hyq.robot.facade.message.team;

import com.hyq.robot.constants.CommonConstant;
import com.hyq.robot.enums.EnumKeyWord;
import com.hyq.robot.facade.message.MessageFacade;
import com.hyq.robot.helper.SendHelper;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.contact.Member;
import net.mamoe.mirai.message.data.At;
import net.mamoe.mirai.message.data.Image;
import net.mamoe.mirai.message.data.Message;
import net.mamoe.mirai.message.data.PlainText;
import org.springframework.stereotype.Component;

import java.net.URL;


/**
 * @author nanke
 * @date 2020/7/22 下午4:53
 */
@Component
public class JieShaoFacade implements MessageFacade {

    @Override
    public EnumKeyWord get() {
        return EnumKeyWord.GROUP_JIESHAO;
    }

    @Override
    public void execute(Contact sender, Contact group, Message message) {

        At at = new At((Member) sender);
        try {
            Image image = group.uploadImage(new URL(CommonConstant.explainImg));
            SendHelper.sendSing(group,at.plus(image));
        } catch (Exception e) {
            SendHelper.sendSing(group,at.plus(new PlainText("网络繁忙,请稍后再试!")));
        }
    }

}
