package com.hyq.robot.facade.factory.message;

import com.hyq.robot.client.CreeperClient;
import com.hyq.robot.constants.ApiURLConstant;
import com.hyq.robot.constants.CommonConstant;
import com.hyq.robot.enums.EnumKeyWord;
import com.hyq.robot.helper.SendHelper;
import com.hyq.robot.star.RobotStar;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.contact.Member;
import net.mamoe.mirai.message.data.At;
import net.mamoe.mirai.message.data.Message;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.PlainText;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

/**
 * @author nanke
 * @date 2020/7/22 下午1:38
 */
@Component
public class FuckFacade implements MessageFacade {

    @Override
    public EnumKeyWord get() {
        return EnumKeyWord.GROUP_FUCK;
    }

    @Override
    public void execute(Contact sender, Contact group, Message message) {

        try {
            Document htmlDocument = CreeperClient.getHtmlDocument(ApiURLConstant.fuckURL, null, null);
            Elements select = htmlDocument.select("p[id=words]");
            MessageChain plus = new At((Member) sender).plus(new PlainText(select.text()));
            SendHelper.sendSing(group,plus);
        } catch (Exception e) {
            SendHelper.sendSing(group,new PlainText("网络繁忙,求助群主!!!"));
            SendHelper.sendSing(RobotStar.bot.getFriend(CommonConstant.errorSendId),new PlainText("FuckURL错误," + e));
        }
    }
}
