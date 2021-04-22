package com.hyq.robot.facade.message.kaifujiankong;

import com.hyq.robot.DO.OpenServiceSubscribeDO;
import com.hyq.robot.DO.SubServiceDO;
import com.hyq.robot.dao.MainServiceDAO;
import com.hyq.robot.dao.OpenServiceSubscribeDAO;
import com.hyq.robot.dao.SubServiceDAO;
import com.hyq.robot.enums.EnumKeyWord;
import com.hyq.robot.facade.message.MessageFacade;
import com.hyq.robot.helper.SendHelper;
import com.hyq.robot.query.OpenServiceSubscribeQuery;
import com.hyq.robot.query.SubServiceQuery;
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
 * @date 2021/1/6 下午2:42
 * 致终于来到这里的勇敢的人:
 * 永远不要放弃！永远不要对自己失望！永远不要逃走辜负了自己。
 * 永远不要哭啼！永远不要说再见！永远不要说慌来伤害目己。
 */
@Component
public class QuXiaoJianKongFacade implements MessageFacade {

    @Resource
    private SubServiceDAO subServiceDAO;
    @Resource
    private OpenServiceSubscribeDAO openServiceSubscribeDAO;

    @Override
    public EnumKeyWord get() {
        return EnumKeyWord.GROUP_QXJK;
    }

    @Override
    public void execute(Contact sender, Contact group, Message message) {

        String content = message.contentToString();
        At at = new At(sender.getId());

        String serviceName = MessageUtil.getKeybyWord(content, 2);
        if (StringUtils.isBlank(serviceName)) {
            SendHelper.sendSing(group,at.plus("请输入服务器。"));
            return ;
        }

        SubServiceQuery subQuery = new SubServiceQuery();
        subQuery.setSubServiceName(serviceName);
        List<SubServiceDO> subServiceDOS = subServiceDAO.queryByCondition(subQuery);
        if (CollectionUtils.isEmpty(subServiceDOS)) {
            SendHelper.sendSing(group,at.plus(String.format("服务器【%s】不存在,请确认", serviceName)));
            return ;
        }
        // 不止一个服务器
        if (subServiceDOS.size() > 1) {
            String errorStr = "\n";
            for (SubServiceDO subServiceDO : subServiceDOS) {
                errorStr += String.format("【%s】\n", subServiceDO.getSubServiceName());
            }
            errorStr = errorStr.substring(0,errorStr.length() - 1);
            SendHelper.sendSing(group,at.plus("请选择服务器：" + errorStr));
            return ;
        }

        SubServiceDO subServiceDO = subServiceDOS.get(0);
        Long mainId = subServiceDO.getMainId();
        // 是否已经订阅过
        OpenServiceSubscribeQuery subscribeQuery = new OpenServiceSubscribeQuery();
        subscribeQuery.setMainId(mainId);
        subscribeQuery.setGroupId(group.getId());
        List<OpenServiceSubscribeDO> subscribeDOS = openServiceSubscribeDAO.queryByCondition(subscribeQuery);
        if (CollectionUtils.isEmpty(subscribeDOS)) {
            SendHelper.sendSing(group,at.plus("未订阅"));
            return ;
        }

        // 取消订阅
        OpenServiceSubscribeDO subscribeDO = subscribeDOS.get(0);
        OpenServiceSubscribeDO updateDO = new OpenServiceSubscribeDO();
        updateDO.setId(subscribeDO.getId());
        updateDO.setDelete(1);
        openServiceSubscribeDAO.updateById(updateDO);
        SendHelper.sendSing(group,at.plus("取消订阅"));
    }
}
