package com.hyq.robot.facade.factory.message;

import com.hyq.robot.DO.TeamDO;
import com.hyq.robot.dao.TeamDAO;
import com.hyq.robot.enums.EnumKeyWord;
import com.hyq.robot.helper.SendHelper;
import com.hyq.robot.query.TeamQuery;
import com.hyq.robot.utils.MessageUtil;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.contact.Member;
import net.mamoe.mirai.message.data.At;
import net.mamoe.mirai.message.data.Message;
import net.mamoe.mirai.message.data.PlainText;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author nanke
 * @date 2020/7/22 下午7:21
 */
@Component
public class CancelKaiTuanFacade implements MessageFacade {

    @Resource
    private TeamDAO teamDAO;

    @Override
    public EnumKeyWord get() {
        return EnumKeyWord.GROUP_CANCEL_KAITUAN;
    }

    @Override
    public void execute(Contact sender, Contact group, Message message) {
        // TODO sender开团权限校验
        At at = new At((Member) sender);

        TeamQuery query = new TeamQuery();
        query.setGroupId(group.getId());
        List<TeamDO> teamDOS = teamDAO.queryByCondition(query);

        if (CollectionUtils.isEmpty(teamDOS)) {
            SendHelper.sendSing(group,at.plus(new PlainText("暂无有效团队,请确认。")));
            return ;
        }

        String content = message.contentToString();
        // 团队序号校验
        String teamNumStr = MessageUtil.getKeybyWord(content, 2);
        Integer teamNum = MessageUtil.checkTeamNum(teamDOS.size(),teamNumStr);
        if (teamNum.equals(0) || teamNum.equals(-1)) {
            SendHelper.sendSing(group,at.plus(new PlainText("请使用口令【查看团队】后选择正确团队序号,从上至下1,2,...,n。")));
            return ;
        }
        // 取消团队
        TeamDO updateDO = new TeamDO();
        updateDO.setId(teamDOS.get(teamNum - 1).getId());
        updateDO.setDelete(1);
        teamDAO.updateById(updateDO);
        SendHelper.sendSing(group,at.plus("取消成功。"));
    }
}
