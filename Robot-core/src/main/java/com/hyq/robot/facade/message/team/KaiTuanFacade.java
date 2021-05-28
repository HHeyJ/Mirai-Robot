package com.hyq.robot.facade.message.team;

import com.hyq.robot.DO.TeamDO;
import com.hyq.robot.dao.TeamDAO;
import com.hyq.robot.enums.EnumKeyWord;
import com.hyq.robot.facade.message.MessageFacade;
import com.hyq.robot.helper.SendHelper;
import com.hyq.robot.utils.MessageUtil;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.message.data.At;
import net.mamoe.mirai.message.data.Message;
import net.mamoe.mirai.message.data.PlainText;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


/**
 * @author nanke
 * @date 2020/7/22 下午4:53
 */
@Component
public class KaiTuanFacade extends TeamFacade implements MessageFacade {

    @Resource
    private TeamDAO teamDAO;

    @Override
    public EnumKeyWord get() {
        return EnumKeyWord.GROUP_KAITUAN;
    }

    @Override
    public void execute(Contact sender, Contact group, Message message) {

        At at = new At(sender.getId());

        // 检查群内是否开团
        String errorMsg = checkTeam(group.getId());
        if (StringUtils.isNotBlank(errorMsg)) {
            SendHelper.sendSing(group,at.plus(errorMsg));
            return ;
        }

        String content = message.contentToString();
        String teamName = MessageUtil.getKeybyWord(content, 2);
        if (StringUtils.isBlank(teamName)) {
            SendHelper.sendSing(group,at.plus(new PlainText("🙅暗号错误")));
            return ;
        }

        TeamDO insetDO = new TeamDO();
        insetDO.setGroupId(group.getId());
        insetDO.setTeamName(teamName);
        teamDAO.insertSelective(insetDO);

        SendHelper.sendSing(group,at.plus("👌"));
    }

}
