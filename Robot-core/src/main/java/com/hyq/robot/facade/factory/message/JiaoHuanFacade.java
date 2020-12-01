package com.hyq.robot.facade.factory.message;

import com.hyq.robot.DO.TeamDO;
import com.hyq.robot.DO.TeamMemberDO;
import com.hyq.robot.dao.TeamDAO;
import com.hyq.robot.dao.TeamMemberDAO;
import com.hyq.robot.enums.EnumKeyWord;
import com.hyq.robot.helper.SendHelper;
import com.hyq.robot.query.TeamMemberQuery;
import com.hyq.robot.query.TeamQuery;
import com.hyq.robot.utils.GroupMemberUtil;
import com.hyq.robot.utils.MessageUtil;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.contact.Member;
import net.mamoe.mirai.message.data.At;
import net.mamoe.mirai.message.data.Message;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

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
public class JiaoHuanFacade implements MessageFacade {

    @Resource
    private TeamDAO teamDAO;
    @Resource
    private TeamMemberDAO teamMemberDAO;

    @Override
    public EnumKeyWord get() {
        return EnumKeyWord.GROUP_JIAOHUAN;
    }

    @Override
    public void execute(Contact sender, Contact group, Message message) {

        At at = new At((Member) sender);
        // 检查群内是否开团
        TeamQuery query = new TeamQuery();
        query.setGroupId(group.getId());
        List<TeamDO> teamDOS = teamDAO.queryByCondition(query);
        if (CollectionUtils.isEmpty(teamDOS)) {
            SendHelper.sendSing(group,at.plus("暂无有效团队,请确认。"));
            return ;
        }
        String content = message.contentToString();
        // 获取第一个位置
        Long one = null;
        String oneStr = MessageUtil.getKeybyWord(content, 2);
        try {
            one = Long.valueOf(oneStr);
        } catch (Exception e) {
            SendHelper.sendSing(group,at.plus("请输入正确队伍位置。"));
            return ;
        }
        // 获取第二个位置
        Long two = null;
        String twoStr = MessageUtil.getKeybyWord(content, 3);
        try {
            two = Long.valueOf(twoStr);
        } catch (Exception e) {
            SendHelper.sendSing(group,at.plus("请输入正确队伍位置。"));
            return ;
        }
        // 检查位置正确性
        if (!GroupMemberUtil.checkLocation(one) || !GroupMemberUtil.checkLocation(two)) {
            SendHelper.sendSing(group,at.plus("请输入正确队伍位置。"));
            return ;
        }
        // 获取团队报名情况
        TeamMemberQuery memberQuery = new TeamMemberQuery();
        memberQuery.setTeamId(teamDOS.get(0).getId());
        List<TeamMemberDO> teamMemberDOS = teamMemberDAO.queryByCondition(memberQuery);
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
        SendHelper.sendSing(group,at.plus("操作成功。"));
    }
}
