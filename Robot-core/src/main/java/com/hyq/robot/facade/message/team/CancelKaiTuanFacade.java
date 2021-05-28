package com.hyq.robot.facade.message.team;

import com.hyq.robot.DO.TeamDO;
import com.hyq.robot.dao.TeamDAO;
import com.hyq.robot.enums.EnumKeyWord;
import com.hyq.robot.facade.message.MessageFacade;
import com.hyq.robot.helper.SendHelper;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.message.data.At;
import net.mamoe.mirai.message.data.Message;
import net.mamoe.mirai.message.data.PlainText;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author nanke
 * @date 2020/7/22 下午7:21
 */
@Component
public class CancelKaiTuanFacade extends TeamFacade implements MessageFacade {

    @Resource
    private TeamDAO teamDAO;

    @Override
    public EnumKeyWord get() {
        return EnumKeyWord.GROUP_CANCEL_KAITUAN;
    }

    @Override
    public void execute(Contact sender, Contact group, Message message) {

        At at = new At(sender.getId());

        TeamDO teamDO = getTeam(group.getId());
        if (teamDO == null) {
            SendHelper.sendSing(group,at.plus(new PlainText("🙅团长被抓")));
            return ;
        }

        // 取消团队
        TeamDO updateDO = new TeamDO();
        updateDO.setId(teamDO.getId());
        updateDO.setDelete(1);
        teamDAO.updateById(updateDO);
        SendHelper.sendSing(group,at.plus("👌"));
    }
}
