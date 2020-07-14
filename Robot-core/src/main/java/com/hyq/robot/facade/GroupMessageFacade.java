package com.hyq.robot.facade;

import com.hyq.robot.enums.EnumKeyWord;
import com.hyq.robot.utils.MessageUtil;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.message.data.Message;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author nanke
 * @date 2020/7/14 下午8:49
 */
@Service
public class GroupMessageFacade {

    @Resource
    private GroupSelectFacade groupSelectFacade;

    public void execute(Contact contact, Message message) {
        // 一级关键词检索
        EnumKeyWord ruleEnum = EnumKeyWord.groupFind(MessageUtil.getKeybyWord(message.contentToString(),1));
        if (ruleEnum == null) {
            return ;
        }

        if (ruleEnum.equals(EnumKeyWord.GROUP_SELECT)) {
            groupSelectFacade.execute(contact,message);
        }
    }
}
