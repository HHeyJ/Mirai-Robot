package com.hyq.robot.facade.message.kaifujiankong;

import com.hyq.robot.DO.MainServiceDO;
import com.hyq.robot.DO.ServiceStatusRecordDO;
import com.hyq.robot.DO.SubServiceDO;
import com.hyq.robot.dao.MainServiceDAO;
import com.hyq.robot.dao.ServiceStatusRecordDAO;
import com.hyq.robot.dao.SubServiceDAO;
import com.hyq.robot.enums.EnumKeyWord;
import com.hyq.robot.enums.EnumOpenStatus;
import com.hyq.robot.facade.message.MessageFacade;
import com.hyq.robot.helper.SendHelper;
import com.hyq.robot.query.MainServiceQuery;
import com.hyq.robot.query.ServiceStatusRecordQuery;
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
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @author nanke
 * @date 2021/1/6 下午2:42
 * 致终于来到这里的勇敢的人:
 * 永远不要放弃！永远不要对自己失望！永远不要逃走辜负了自己。
 * 永远不要哭啼！永远不要说再见！永远不要说慌来伤害目己。
 */
@Component
public class KaiFuChaXunFacade implements MessageFacade {

    private static final SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Resource
    private SubServiceDAO subServiceDAO;
    @Resource
    private MainServiceDAO mainServiceDAO;
    @Resource
    private ServiceStatusRecordDAO serviceStatusRecordDAO;

    @Override
    public EnumKeyWord get() {
        return EnumKeyWord.GROUP_KFCX;
    }

    @Override
    public void execute(Contact sender, Contact group, Message message) {

        String content = message.contentToString();
        At at = new At((Member) sender);

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
            SendHelper.sendSing(group,at.plus("请选择服务器：" + errorStr));
            return ;
        }
        // 查询主服务器
        SubServiceDO subServiceDO = subServiceDOS.get(0);
        Long mainId = subServiceDO.getMainId();
        MainServiceQuery mainQuery = new MainServiceQuery();
        mainQuery.setMainId(mainId);
        List<MainServiceDO> mainServiceDOS = mainServiceDAO.queryByCondition(mainQuery);
        // 查询开服记录
        ServiceStatusRecordQuery recordQuery = new ServiceStatusRecordQuery();
        recordQuery.setMainId(mainId);
        recordQuery.setOpenStatus(EnumOpenStatus.OPEN.status);
        recordQuery.setPageNo(1);
        recordQuery.setPageSize(5);
        List<ServiceStatusRecordDO> recordDOS = serviceStatusRecordDAO.queryByCondition(recordQuery);
        // 获取回复消息
        String sendMessage = getSendMessage(mainServiceDOS.get(0), subServiceDO, recordDOS);
        // 发送
        SendHelper.sendSing(group,at.plus(sendMessage));
    }

    /**
     * 获取开服查询回复信息
     * @param mainDO
     * @param subDO
     * @param recordDOS
     * @return
     */
    private String getSendMessage(MainServiceDO mainDO, SubServiceDO subDO, List<ServiceStatusRecordDO> recordDOS) {
        String sendMessage = String.format("主服务器【%s】子服务器【%s】", mainDO.getMainServiceName(),subDO.getSubServiceName());
        sendMessage += String.format("\n服务器状态【%s】", EnumOpenStatus.get(mainDO.getOpenStatus()).desc);
        sendMessage += "\n开服记录：\n";
        if (CollectionUtils.isEmpty(recordDOS)) {
            sendMessage += "暂无开服记录";
        } else {
            for (ServiceStatusRecordDO recordDO : recordDOS) {
                sendMessage += format.format(recordDO.getGmtCreate()) + "\n";
            }
        }
        return sendMessage;
    }
}
