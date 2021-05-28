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

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author nanke
 * @date 2020/7/22 下午8:48
 */
@Component
public class JiaoHuanFacade extends TeamFacade implements MessageFacade {

    @Resource
    private TeamMemberDAO teamMemberDAO;

    @Override
    public EnumKeyWord get() {
        return EnumKeyWord.GROUP_JIAOHUAN;
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
        // 第一个位置/第二个位置
        Long one = null; Long two = null;
        String oneStr = MessageUtil.getKeybyWord(content, 2);
        String twoStr = MessageUtil.getKeybyWord(content, 3);
        try {
            one = Long.valueOf(oneStr);
            two = Long.valueOf(twoStr);
            if (!GroupMemberUtil.checkLocation(one) || !GroupMemberUtil.checkLocation(two)) {
                throw new RuntimeException();
            }
        } catch (Exception e) {
            SendHelper.sendSing(group,at.plus("🙅暗号错误"));
            return ;
        }
        // 获取团队报名情况
        TeamDO teamDO = getTeam(group.getId());
        List<TeamMemberDO> teamMemberDOS = getTeamMember(teamDO.getId(),null);
        Map<Long, TeamMemberDO> memberMap = teamMemberDOS.stream().collect(Collectors.toMap(TeamMemberDO::getLocation, Function.identity(), (x, y) -> x));
        // 查看位置是否有人
        TeamMemberDO oneMember = memberMap.get(one);
        TeamMemberDO twoMember = memberMap.get(two);
        // 如果第一个位置有人把换到第二个位置去
        if (oneMember != null) {
            TeamMemberDO updateDO = new TeamMemberDO();
            updateDO.setId(oneMember.getId());
            updateDO.setLocation(two);
            teamMemberDAO.updateLocationById(updateDO);
        }
        // 如果第二个位置有人把换到第一个位置去
        if (twoMember != null) {
            TeamMemberDO updateDO = new TeamMemberDO();
            updateDO.setId(twoMember.getId());
            updateDO.setLocation(one);
            teamMemberDAO.updateLocationById(updateDO);
        }
        SendHelper.sendSing(group,at.plus("👌"));
    }
}
