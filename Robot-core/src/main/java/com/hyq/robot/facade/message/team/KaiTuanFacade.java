package com.hyq.robot.facade.message.team;

import com.hyq.robot.DO.TeamDO;
import com.hyq.robot.constants.CommonConstant;
import com.hyq.robot.dao.TeamDAO;
import com.hyq.robot.enums.EnumKeyWord;
import com.hyq.robot.facade.message.MessageFacade;
import com.hyq.robot.helper.SendHelper;
import com.hyq.robot.query.TeamQuery;
import com.hyq.robot.utils.GroupMemberUtil;
import com.hyq.robot.utils.MessageUtil;
import gui.ava.html.image.generator.HtmlImageGenerator;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.contact.Member;
import net.mamoe.mirai.message.data.At;
import net.mamoe.mirai.message.data.Image;
import net.mamoe.mirai.message.data.Message;
import net.mamoe.mirai.message.data.PlainText;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;


/**
 * @author nanke
 * @date 2020/7/22 下午4:53
 */
@Component
public class KaiTuanFacade implements MessageFacade {

    @Resource
    private TeamDAO teamDAO;

    @Override
    public EnumKeyWord get() {
        return EnumKeyWord.GROUP_KAITUAN;
    }

    @Override
    public void execute(Contact sender, Contact group, Message message) {

        At at = new At((Member) sender);

        TeamQuery query = new TeamQuery();
        query.setGroupId(group.getId());
        List<TeamDO> teamDOS = teamDAO.queryByCondition(query);
        if (!CollectionUtils.isEmpty(teamDOS)) {
            SendHelper.sendSing(group,at.plus(new PlainText("请勿重复开团。")));
            return ;
        }

        String content = message.contentToString();
        String teamName = MessageUtil.getKeybyWord(content, 2);
        if (StringUtils.isBlank(teamName)) {
            SendHelper.sendSing(group,at.plus(new PlainText("请输入团名。")));
            return ;
        }

        TeamDO insetDO = new TeamDO();
        insetDO.setGroupId(group.getId());
        insetDO.setTeamName(teamName);
        teamDAO.insertSelective(insetDO);

        HtmlImageGenerator generator = new HtmlImageGenerator();
        generator.loadHtml(GroupMemberUtil.replaceInit(teamName,CommonConstant.htmlStr));

        Image image = group.uploadImage(generator.getBufferedImage());
        SendHelper.sendSing(group,at.plus("开团成功。").plus(image));
    }

}
