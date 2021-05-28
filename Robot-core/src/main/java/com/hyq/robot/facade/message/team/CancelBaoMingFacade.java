package com.hyq.robot.facade.message.team;

import com.hyq.robot.DO.TeamDO;
import com.hyq.robot.DO.TeamMemberDO;
import com.hyq.robot.dao.TeamMemberDAO;
import com.hyq.robot.enums.EnumKeyWord;
import com.hyq.robot.facade.message.MessageFacade;
import com.hyq.robot.helper.SendHelper;
import com.hyq.robot.utils.GroupMemberUtil;
import com.hyq.robot.utils.MessageUtil;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.message.data.At;
import net.mamoe.mirai.message.data.Message;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author nanke
 * @date 2020/7/22 下午11:14
 */
@Component
public class CancelBaoMingFacade extends TeamFacade implements MessageFacade {

    @Resource
    private TeamMemberDAO teamMemberDAO;

    @Override
    public EnumKeyWord get() {
        return EnumKeyWord.GROUP_CANCEL_BAOMING;
    }

    @Override
    public void execute(Contact sender, Contact group, Message message) {

        At at = new At(sender.getId());
        String content = message.contentToString();
        long groupId = group.getId();

        // 检查群内是否开团
        String errorMsg = checkTeam(groupId);
        if (StringUtils.isNotBlank(errorMsg)) {
            SendHelper.sendSing(group,at.plus(errorMsg));
            return ;
        }
        // 获取位置
        Long location = null;
        String locationStr = MessageUtil.getKeybyWord(content, 2);
        try {
            location = Long.valueOf(locationStr);
        } catch (Exception e) {
            SendHelper.sendSing(group,at.plus("🙅暗号错误"));
            return ;
        }
        // 位置合法性
        if (!GroupMemberUtil.checkLocation(location)) {
            SendHelper.sendSing(group,at.plus("🙅暗号错误"));
            return ;
        }
        // 获取团队成员
        TeamDO teamDO = getTeam(groupId);
        List<TeamMemberDO> teamMemberDOS = getTeamMember(teamDO.getId(),location);
        // 位置是否有人
        if (CollectionUtils.isEmpty(teamMemberDOS)) {
            SendHelper.sendSing(group,at.plus("🙅暗号错误"));
            return ;
        }
        // 取消
        TeamMemberDO updateDO = new TeamMemberDO();
        updateDO.setId(teamMemberDOS.get(0).getId());
        updateDO.setDelete(1);
        teamMemberDAO.updateById(updateDO);
        SendHelper.sendSing(group,at.plus("👌"));
    }
}
