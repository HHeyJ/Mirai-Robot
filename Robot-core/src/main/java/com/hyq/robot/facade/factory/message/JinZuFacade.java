package com.hyq.robot.facade.factory.message;

import com.hyq.robot.DO.TeamDO;
import com.hyq.robot.DO.TeamMemberDO;
import com.hyq.robot.constants.CommonConstant;
import com.hyq.robot.dao.TeamDAO;
import com.hyq.robot.dao.TeamMemberDAO;
import com.hyq.robot.enums.EnumKeyWord;
import com.hyq.robot.helper.SendHelper;
import com.hyq.robot.query.TeamMemberQuery;
import com.hyq.robot.query.TeamQuery;
import com.hyq.robot.star.RobotStar;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.contact.Member;
import net.mamoe.mirai.message.data.*;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author nanke
 * @date 2020/12/8 下午12:11
 * 致终于来到这里的勇敢的人:
 * 永远不要放弃！永远不要对自己失望！永远不要逃走辜负了自己。
 * 永远不要哭啼！永远不要说再见！永远不要说慌来伤害目己。
 */
@Component
public class JinZuFacade implements MessageFacade {

    @Resource
    private TeamDAO teamDAO;
    @Resource
    private TeamMemberDAO teamMemberDAO;

    @Override
    public EnumKeyWord get() {
        return EnumKeyWord.GROUP_JINZU;
    }

    @Override
    public void execute(Contact sender, Contact group, Message message) {
        // 查询群团队
        TeamQuery query = new TeamQuery();
        query.setGroupId(group.getId());
        List<TeamDO> teamDOS = teamDAO.queryByCondition(query);
        if (CollectionUtils.isEmpty(teamDOS)) {
            return ;
        }
        // 查询团队成员
        TeamMemberQuery memberQuery = new TeamMemberQuery();
        memberQuery.setTeamId(teamDOS.get(0).getId());
        List<TeamMemberDO> teamMemberDOS = teamMemberDAO.queryByCondition(memberQuery);
        if (CollectionUtils.isEmpty(teamDOS)) {
            return ;
        }
        // 获取真实群数据
        Group realGroup = RobotStar.bot.getGroup(group.getId());
        // 消息链
        MessageChainBuilder sendMessage = new MessageChainBuilder(teamDOS.size() + 1);
        for (TeamMemberDO teamMemberDO : teamMemberDOS) {
            try {
                Member member = realGroup.get(teamMemberDO.getQq());
                // 私聊
                member.sendMessage(new PlainText("团长让你们上线进组啦～"));
                // 群内AT
                sendMessage.add(new At(member));
            } catch (Exception e) {
                RobotStar.bot.getFriend(CommonConstant.errorSendId).sendMessage("获取群成员处理错误!群ID:" + group.getId() +
                        ",成员ID:" + teamMemberDO.getQq());
            }
        }
        // 群消息发送
        MessageChain build = sendMessage.build();
        SendHelper.sendSing(group,build.plus(new PlainText("快点进组!!")));
    }
}
