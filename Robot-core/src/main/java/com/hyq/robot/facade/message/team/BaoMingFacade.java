package com.hyq.robot.facade.message.team;

import com.hyq.robot.DO.TeamDO;
import com.hyq.robot.DO.TeamMemberDO;
import com.hyq.robot.dao.TeamDAO;
import com.hyq.robot.dao.TeamMemberDAO;
import com.hyq.robot.enums.EnumKeyWord;
import com.hyq.robot.enums.EnumPosition;
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
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author nanke
 * @date 2020/7/22 下午8:48
 */
@Component
public class BaoMingFacade implements MessageFacade {

    @Resource
    private TeamDAO teamDAO;
    @Resource
    private TeamMemberDAO teamMemberDAO;

    @Override
    public EnumKeyWord get() {
        return EnumKeyWord.GROUP_BAOMING;
    }

    @Override
    public void execute(Contact sender, Contact group, Message message) {

        At at = new At(sender.getId());
        // 检查群内是否开团
        TeamQuery query = new TeamQuery();
        query.setGroupId(group.getId());
        List<TeamDO> teamDOS = teamDAO.queryByCondition(query);
        if (CollectionUtils.isEmpty(teamDOS)) {
            SendHelper.sendSing(group,at.plus("暂无有效团队,请确认。"));
            return ;
        }
        String content = message.contentToString();
        // 检查职业
        String position = MessageUtil.getKeybyWord(content, 2);
        EnumPosition enumPosition = EnumPosition.get(position);
        if (enumPosition == null) {
            SendHelper.sendSing(group,at.plus("请输入口令：介绍"));
            return ;
        }
        // 检查角色名、位置
        String threeStr = MessageUtil.getKeybyWord(content, 3);
        // 角色名、位置
        Long location = null;
        String memberName = null;
        // 填了第三个
        if (StringUtils.isNotBlank(threeStr)) {
            try {
                location = Long.valueOf(threeStr);
                // 填写了队伍位置,未填写昵称
                if (GroupMemberUtil.checkLocation(location)) {
                    memberName = ((Member) sender).getNameCard();
                }
            } catch (Exception e) {
                memberName = threeStr;
            }
            // 填了第四个
            String fourStr = MessageUtil.getKeybyWord(content, 4);
            if (StringUtils.isNotBlank(fourStr)) {
                try {
                    location = Long.valueOf(fourStr);
                } catch (Exception e) {}
                // 检查位置正确性
                if (!GroupMemberUtil.checkLocation(location)) {
                    SendHelper.sendSing(group,at.plus("请输入正确队伍位置。"));
                    return ;
                }
            } else {
                // 没填第四个
            }
        } else {
            // 没填第三个
            memberName = ((Member) sender).getNameCard();
        }

        TeamDO teamDO = teamDOS.get(0);
        // 检查位置重复
        TeamMemberQuery memberQuery = new TeamMemberQuery();
        memberQuery.setTeamId(teamDO.getId());
        List<TeamMemberDO> teamMemberDOS = teamMemberDAO.queryByCondition(memberQuery);
        List<Long> locationList = teamMemberDOS.stream().map(TeamMemberDO::getLocation).collect(Collectors.toList());
        if (location != null) {
            if (locationList.contains(location)) {
                SendHelper.sendSing(group,at.plus("报名位置重复,请重新选择或联系团长调整。"));
                return ;
            }
        } else {
            location = GroupMemberUtil.getLocation(locationList);
        }
        // 团队人数已满
        if (location == null) {
            SendHelper.sendSing(group,at.plus("当前团队已满,请联系团长调整。"));
            return ;
        }

        // 报名
        TeamMemberDO insertDO = new TeamMemberDO();
        insertDO.setTeamId(teamDO.getId());
        insertDO.setLocation(location);
        insertDO.setPosition(enumPosition.position.split(" ")[0]);
        insertDO.setColor(enumPosition.color);
        insertDO.setMemberName(memberName);
        insertDO.setQq(sender.getId());
        teamMemberDAO.insertSelective(insertDO);

        SendHelper.sendSing(group,at.plus("报名成功。"));
    }
}
