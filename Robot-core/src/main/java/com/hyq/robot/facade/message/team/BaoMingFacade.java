package com.hyq.robot.facade.message.team;

import com.hyq.robot.DO.TeamDO;
import com.hyq.robot.DO.TeamMemberDO;
import com.hyq.robot.dao.TeamMemberDAO;
import com.hyq.robot.enums.EnumKeyWord;
import com.hyq.robot.enums.EnumPosition;
import com.hyq.robot.facade.message.MessageFacade;
import com.hyq.robot.helper.SendHelper;
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
public class BaoMingFacade extends TeamFacade implements MessageFacade {

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
        String errorMsg = checkTeam(group.getId());
        if (StringUtils.isNotBlank(errorMsg)) {
            SendHelper.sendSing(group,at.plus(errorMsg));
            return ;
        }

        String content = message.contentToString();
        // 检查职业
        String position = MessageUtil.getKeybyWord(content, 2);
        EnumPosition enumPosition = EnumPosition.get(position);
        if (enumPosition == null) {
            SendHelper.sendSing(group,at.plus("🙅暗号错误"));
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
                    SendHelper.sendSing(group,at.plus("🙅暗号错误"));
                    return ;
                }
            } else {
                // 没填第四个
            }
        } else {
            // 没填第三个
            memberName = ((Member) sender).getNameCard();
        }
        // 选位
        TeamDO teamDO = getTeam(group.getId());
        List<TeamMemberDO> teamMemberDOS = getTeamMember(teamDO.getId(),location);
        List<Long> locationList = teamMemberDOS.stream().map(TeamMemberDO::getLocation).collect(Collectors.toList());
        if (location != null) {
            if (!CollectionUtils.isEmpty(teamMemberDOS)) {
                location = GroupMemberUtil.getLocation(locationList,enumPosition.type);
            }
        } else {
            location = GroupMemberUtil.getLocation(locationList,enumPosition.type);
        }

        if (location == null) {
            SendHelper.sendSing(group,at.plus("🙅团队已满"));
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

        SendHelper.sendSing(group,at.plus("👌"));
    }
}
