package com.hyq.robot.facade.factory.message;

import com.hyq.robot.enums.EnumKeyWord;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.message.data.Message;

/**
 * @author nanke
 * @date 2020/7/22 下午1:06
 */
public interface MessageFacade {

    /**
     * 关键字诶类型
     * @return
     */
    public EnumKeyWord get();

    /**
     * 操作
     * @param sender
     * @param group
     * @param message
     */
    public void execute(Contact sender, Contact group, Message message);
}
