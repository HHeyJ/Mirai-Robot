//package com.hyq.robot.facade.factory.message;
//
//import com.hyq.robot.DO.InformRelationDO;
//import com.hyq.robot.client.CreeperClient;
//import com.hyq.robot.constants.ApiURLConstant;
//import com.hyq.robot.constants.CommonConstant;
//import com.hyq.robot.dao.InformRelationDAO;
//import com.hyq.robot.enums.EnumKeyWord;
//import com.hyq.robot.helper.SendHelper;
//import com.hyq.robot.query.InformRelationQuery;
//import com.hyq.robot.star.RobotStar;
//import net.mamoe.mirai.contact.Contact;
//import net.mamoe.mirai.contact.Member;
//import net.mamoe.mirai.message.data.*;
//import org.jsoup.nodes.Document;
//import org.jsoup.select.Elements;
//import org.springframework.stereotype.Component;
//import org.springframework.util.CollectionUtils;
//
//import javax.annotation.Resource;
//import java.net.URL;
//import java.util.List;
//
///**
// * @author nanke
// * @date 2020/7/22 下午1:24
// */
//@Component
//public class LoveFacade implements MessageFacade {
//
//    @Resource
//    private InformRelationDAO informRelationDAO;
//
//    @Override
//    public EnumKeyWord get() {
//        return EnumKeyWord.GROUP_LOVE;
//    }
//
//    @Override
//    public void execute(Contact sender, Contact group, Message message) {
//
//        try {
//            InformRelationQuery query = new InformRelationQuery();
//            query.setGroupId(group.getId());
//            List<InformRelationDO> informRelationDOS = informRelationDAO.queryByCondition(query);
//            if (!CollectionUtils.isEmpty(informRelationDOS)) {
//                Image image = group.uploadImage(new URL("http://gchat.qpic.cn/gchatpic_new" +
//                        "/1154685452/992565806-2476814672-06D19EF60290A4E968C53E1E2B7DC902/0?term=2"));
//                SendHelper.sendSing(group,new At((Member) sender).plus(new PlainText("播报群不准玩,再玩就给你马一拳")).plus(image));
//                return ;
//            }
//
//            Document htmlDocument = CreeperClient.getHtmlDocument(ApiURLConstant.loveURL, null, null);
//            Elements select = htmlDocument.select("p[id=words]");
//            MessageChain plus = new At((Member) sender).plus(new PlainText(select.text()));
//            SendHelper.sendSing(group,plus);
//        } catch (Exception e) {
//            SendHelper.sendSing(group,new PlainText("网络繁忙,求助群主!!!"));
//            SendHelper.sendSing(RobotStar.bot.getFriend(CommonConstant.errorSendId),new PlainText("LoveURL错误," + e));
//        }
//    }
//}
