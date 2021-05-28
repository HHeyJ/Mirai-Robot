package com.hyq.robot.facade.message.team;

import com.hyq.robot.DO.TeamDO;
import com.hyq.robot.DO.TeamMemberDO;
import com.hyq.robot.enums.EnumKeyWord;
import com.hyq.robot.facade.message.MessageFacade;
import com.hyq.robot.helper.SendHelper;
import com.hyq.robot.utils.GroupMemberUtil;
import gui.ava.html.image.generator.HtmlImageGenerator;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.message.data.At;
import net.mamoe.mirai.message.data.Image;
import net.mamoe.mirai.message.data.Message;
import net.mamoe.mirai.message.data.PlainText;
import net.mamoe.mirai.utils.ExternalResource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * @author nanke
 * @date 2020/7/22 下午11:03
 */
@Component
public class ChaKanTuanDuiFacade extends TeamFacade implements MessageFacade {

    @Override
    public EnumKeyWord get() {
        return EnumKeyWord.GROUP_SEE_KAITUAN;
    }

    @Override
    public void execute(Contact sender, Contact group, Message message) throws IOException {

        At at = new At(sender.getId());
        // 检查群内是否开团
        String errorMsg = checkTeam(group.getId());
        if (StringUtils.isNotBlank(errorMsg)) {
            SendHelper.sendSing(group,at.plus(errorMsg));
            return ;
        }

        TeamDO teamDO = getTeam(group.getId());
        List<TeamMemberDO> teamMemberDOS = getTeamMember(teamDO.getId(),null);
        // 渲染HTML
        String htmlStr = GroupMemberUtil.replaceMember(teamDO.getTeamName(), teamMemberDOS);
        HtmlImageGenerator generator = new HtmlImageGenerator();
        generator.loadHtml(GroupMemberUtil.replaceInit("",htmlStr));

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ImageIO.write(generator.getBufferedImage(),"png",out);

        byte[] bytes = out.toByteArray();

        ExternalResource externalResource = ExternalResource.create(bytes);
        Image image = group.uploadImage(externalResource);
        SendHelper.sendSing(group,new PlainText("团队详情:\n").plus(image));
    }
}
