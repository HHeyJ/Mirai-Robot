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
import gui.ava.html.image.generator.HtmlImageGenerator;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.contact.Member;
import net.mamoe.mirai.message.data.At;
import net.mamoe.mirai.message.data.Image;
import net.mamoe.mirai.message.data.Message;
import net.mamoe.mirai.utils.ExternalResource;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * @author nanke
 * @date 2020/7/22 下午11:03
 */
@Component
public class ChaKanTuanDuiFacade implements MessageFacade {

    @Resource
    private TeamDAO teamDAO;
    @Resource
    private TeamMemberDAO teamMemberDAO;

    @Override
    public EnumKeyWord get() {
        return EnumKeyWord.GROUP_SEE_KAITUAN;
    }

    @Override
    public void execute(Contact sender, Contact group, Message message) throws IOException {

        At at = new At(sender.getId());
        // 查询群内团队
        TeamQuery teamQuery = new TeamQuery();
        teamQuery.setGroupId(group.getId());
        List<TeamDO> teamDOS = teamDAO.queryByCondition(teamQuery);
        if (CollectionUtils.isEmpty(teamDOS)) {
            SendHelper.sendSing(group,at.plus("群内暂无有效团队,请确认。"));
            return ;
        }

        // 查询团队成员
        TeamDO teamDO = teamDOS.get(0);
        TeamMemberQuery memberQuery = new TeamMemberQuery();
        memberQuery.setTeamId(teamDO.getId());
        List<TeamMemberDO> teamMemberDOS = teamMemberDAO.queryByCondition(memberQuery);
        // 渲染HTML
        String htmlStr = GroupMemberUtil.replaceMember(teamDO.getTeamName(), teamMemberDOS);
        HtmlImageGenerator generator = new HtmlImageGenerator();
        generator.loadHtml(GroupMemberUtil.replaceInit("",htmlStr));

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ImageIO.write(generator.getBufferedImage(),"jpg",out);

        byte[] bytes = out.toByteArray();

        ExternalResource externalResource = ExternalResource.create(bytes);
        Image image = group.uploadImage(externalResource);
        SendHelper.sendSing(group,at.plus("团队详情如下:").plus(image));
    }
}
