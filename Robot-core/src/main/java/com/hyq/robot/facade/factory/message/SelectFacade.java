package com.hyq.robot.facade.factory.message;

import com.hyq.robot.enums.EnumKeyWord;
import com.hyq.robot.helper.SendHelper;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.message.data.Message;
import net.mamoe.mirai.message.data.PlainText;
import org.springframework.stereotype.Service;

/**
 * @author nanke
 * @date 2020/7/14 下午8:52
 */
@Service
public class SelectFacade implements MessageFacade {

    @Override
    public EnumKeyWord get() {
        return EnumKeyWord.GROUP_SELECT;
    }

    public void execute(Contact sender, Contact group, Message message) {

        // 提示请添加机器人好友后私聊查询
        SendHelper.sendSing(group,new PlainText("【黑市查询】请添加机器人好友后私聊查询,直接申请添加好友即可。"));
    }
}
