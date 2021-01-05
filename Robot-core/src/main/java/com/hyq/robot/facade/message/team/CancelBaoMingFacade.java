package com.hyq.robot.facade.message.team;

import com.hyq.robot.DO.TeamDO;
import com.hyq.robot.DO.TeamMemberDO;
import com.hyq.robot.dao.TeamDAO;
import com.hyq.robot.dao.TeamMemberDAO;
import com.hyq.robot.enums.EnumKeyWord;
import com.hyq.robot.facade.message.MessageFacade;
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

/**
 * @author nanke
 * @date 2020/7/22 下午11:14
 */
@Component
public class CancelBaoMingFacade implements MessageFacade {

    @Resource
    private TeamDAO teamDAO;
    @Resource
    private TeamMemberDAO teamMemberDAO;

    @Override
    public EnumKeyWord get() {
        return EnumKeyWord.GROUP_CANCEL_BAOMING;
    }

    @Override
    public void execute(Contact sender, Contact group, Message message) {

        At at = new At((Member) sender);
        String content = message.contentToString();

        // 群内是否开团
        TeamQuery query = new TeamQuery();
        query.setGroupId(group.getId());
        List<TeamDO> teamDOS = teamDAO.queryByCondition(query);
        if (CollectionUtils.isEmpty(teamDOS)) {
            SendHelper.sendSing(group,at.plus("暂无有效团队,请确认。"));
            return ;
        }
        // 获取位置
        Long location = null;
        String locationStr = MessageUtil.getKeybyWord(content, 2);
        try {
            location = Long.valueOf(locationStr);
        } catch (Exception e) {
            SendHelper.sendSing(group,at.plus("请输入正确队伍位置。"));
            return ;
        }
        // 位置合法性
        if (!GroupMemberUtil.checkLocation(location)) {
            SendHelper.sendSing(group,at.plus("请输入正确队伍位置。"));
            return ;
        }
        // 查询位置报名情况
        TeamDO teamDO = teamDOS.get(0);
        TeamMemberQuery memberQuery = new TeamMemberQuery();
        memberQuery.setTeamId(teamDO.getId());
        memberQuery.setLocation(location);
        List<TeamMemberDO> teamMemberDOS = teamMemberDAO.queryByCondition(memberQuery);
        if (CollectionUtils.isEmpty(teamMemberDOS)) {
            SendHelper.sendSing(group,at.plus("该位置暂无人报名,请确认。"));
            return ;
        }
        // 取消
        TeamMemberDO updateDO = new TeamMemberDO();
        updateDO.setId(teamMemberDOS.get(0).getId());
        updateDO.setDelete(1);
        teamMemberDAO.updateById(updateDO);
        SendHelper.sendSing(group,at.plus("取消报名成功。"));
    }
}
