package com.hyq.robot.facade.factory;

import com.hyq.robot.enums.EnumKeyWord;
import com.hyq.robot.facade.factory.message.MessageFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author nanke
 * @date 2020/7/22 下午1:03
 */
@Component
public class MessageFactory {

    @Autowired
    private List<MessageFacade> messageFacades;

    public MessageFacade get(EnumKeyWord keyWord) {

        for (MessageFacade messageFacade : messageFacades) {
            if (messageFacade.get().equals(keyWord)) {
                return messageFacade;
            }
            continue;
        }
        return null;
    }


}
