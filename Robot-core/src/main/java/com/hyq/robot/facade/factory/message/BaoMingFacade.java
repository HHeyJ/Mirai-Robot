package com.hyq.robot.facade.factory.message;

import com.hyq.robot.DO.TeamDO;
import com.hyq.robot.DO.TeamMemberDO;
import com.hyq.robot.dao.TeamDAO;
import com.hyq.robot.dao.TeamMemberDAO;
import com.hyq.robot.enums.EnumKeyWord;
import com.hyq.robot.enums.EnumPosition;
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
        // 检查职业
        String position = MessageUtil.getKeybyWord(content, 2);
        EnumPosition enumPosition = EnumPosition.get(position);
        if (enumPosition == null) {
            SendHelper.sendSing(group,at.plus("职业匹配错误,请输入正确报名格式eg:报名？分山？角色名？位置(几队第几位eg:22)," +
                    "参照以下职业列表:[凌雪,蓬莱,霸道,长歌,奶歌,分山,铁骨,丐帮,焚影,明尊,田螺,惊羽,毒经,奶毒,藏剑,傲雪,铁牢,剑纯,气纯,冰心,奶秀,花间,奶花,易筋,洗髓]"));
            return ;
        }
        // 检查角色名
        String memberName = MessageUtil.getKeybyWord(content, 3);
        if (StringUtils.isBlank(memberName)) {
            SendHelper.sendSing(group,at.plus("请输入正确报名格式eg:报名？分山？角色名？位置(几队第几位eg:22)"));
            return ;
        }
        // 获取位置
        Long location = null;
        String locationStr = MessageUtil.getKeybyWord(content, 4);
        try {
            location = Long.valueOf(locationStr);
        } catch (Exception e) {
            SendHelper.sendSing(group,at.plus("请输入正确报名格式eg:报名？分山？角色名？位置(几队第几位eg:22)"));
            return ;
        }
        // 检查位置正确性
        if (!GroupMemberUtil.checkLocation(location)) {
            SendHelper.sendSing(group,at.plus("请输入正确队伍位置。"));
            return ;
        }
        // 检查位置重复
        TeamMemberQuery memberQuery = new TeamMemberQuery();
        memberQuery.setTeamId(teamDOS.get(0).getId());
        memberQuery.setLocation(location);
        List<TeamMemberDO> teamMemberDOS = teamMemberDAO.queryByCondition(memberQuery);
        if (!CollectionUtils.isEmpty(teamMemberDOS)) {
            SendHelper.sendSing(group,at.plus("报名位置重复,请重新选择或联系团长调整。"));
            return ;
        }
        // 报名
        TeamMemberDO insertDO = new TeamMemberDO();
        insertDO.setTeamId(teamDOS.get(0).getId());
        insertDO.setLocation(location);
        insertDO.setPosition(enumPosition.position);
        insertDO.setColor(enumPosition.color);
        insertDO.setMemberName(memberName);
        insertDO.setQq(sender.getId());
        teamMemberDAO.insertSelective(insertDO);

        SendHelper.sendSing(group,at.plus("报名成功。"));
    }
}
