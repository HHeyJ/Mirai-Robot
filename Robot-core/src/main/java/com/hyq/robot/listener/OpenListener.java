package com.hyq.robot.listener;

import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.Subscribe;
import com.hyq.robot.DO.MainServiceDO;
import com.hyq.robot.DO.OpenServiceSubscribeDO;
import com.hyq.robot.DO.ServiceStatusRecordDO;
import com.hyq.robot.dao.MainServiceDAO;
import com.hyq.robot.dao.OpenServiceSubscribeDAO;
import com.hyq.robot.dao.ServiceStatusRecordDAO;
import com.hyq.robot.enums.EnumOpenStatus;
import com.hyq.robot.helper.SendHelper;
import com.hyq.robot.query.MainServiceQuery;
import com.hyq.robot.query.OpenServiceSubscribeQuery;
import com.hyq.robot.query.ServiceStatusRecordQuery;
import com.hyq.robot.star.RobotStar;
import com.hyq.robot.utils.DateUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.message.data.Message;
import net.mamoe.mirai.message.data.PlainText;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author nanke
 * @date 2021/1/5 下午9:24
 * 致终于来到这里的勇敢的人:
 * 永远不要放弃！永远不要对自己失望！永远不要逃走辜负了自己。
 * 永远不要哭啼！永远不要说再见！永远不要说慌来伤害目己。
 */
@Component
@Slf4j
public class OpenListener {

    @Resource
    private AsyncEventBus asyncEventBus;
    @Resource
    private MainServiceDAO mainServiceDAO;
    @Resource
    private ServiceStatusRecordDAO serviceStatusRecordDAO;
    @Resource
    private OpenServiceSubscribeDAO openServiceSubscribeDAO;

    @PostConstruct
    public void register() {
        asyncEventBus.register(this);
    }

    @Subscribe
    @AllowConcurrentEvents
    public void sendOpenInform(OpenEvent event) {

        Long mainId = event.getMainId();
        // 查询订阅列表
        OpenServiceSubscribeQuery subscribeQuery = new OpenServiceSubscribeQuery();
        subscribeQuery.setMainId(mainId);
        List<OpenServiceSubscribeDO> subscribeDOS = openServiceSubscribeDAO.queryByCondition(subscribeQuery);
        if (CollectionUtils.isEmpty(subscribeDOS)) {
            return ;
        }

        // 服务器ID、真实的状态
        MainServiceQuery mainQuery = new MainServiceQuery();
        mainQuery.setMainId(mainId);
        List<MainServiceDO> mainServiceDOS = mainServiceDAO.queryByCondition(mainQuery);
        if (CollectionUtils.isEmpty(mainServiceDOS)) {
            return ;
        }

        MainServiceDO mainDO = mainServiceDOS.get(0);

        ServiceStatusRecordQuery recordQuery = new ServiceStatusRecordQuery();
        recordQuery.setMainId(mainId);
        recordQuery.setOpenStatus(EnumOpenStatus.OPEN.status);
        recordQuery.setPageNo(1);
        recordQuery.setPageSize(5);
        List<ServiceStatusRecordDO> openRecordDOS = serviceStatusRecordDAO.queryByCondition(recordQuery);

        recordQuery.setOpenStatus(EnumOpenStatus.CLOSE.status);
        List<ServiceStatusRecordDO> closeRecordDOS = serviceStatusRecordDAO.queryByCondition(recordQuery);

        String sendMessage = getSendMessage(mainDO, openRecordDOS, closeRecordDOS);
        Message message = new PlainText(sendMessage);
        // 循环发送
        List<Long> groupIdList = subscribeDOS.stream().map(OpenServiceSubscribeDO::getGroupId).collect(Collectors.toList());
        SendHelper.sendGroupBatch(RobotStar.bot,groupIdList, Collections.singletonList(message));
    }

    /**
     * 获取开服查询回复信息
     * @param mainDO
     * @param openRecordDOS
     * @param closeRecordDOS
     * @return
     */
    private String getSendMessage(MainServiceDO mainDO, List<ServiceStatusRecordDO> openRecordDOS,
                                  List<ServiceStatusRecordDO> closeRecordDOS) {
        String sendMessage = String.format("【%s】%s啦～\n", mainDO.getMainServiceName(),EnumOpenStatus.get(mainDO.getOpenStatus()).desc);
        sendMessage += "近五次开服记录：\n";
        if (CollectionUtils.isEmpty(openRecordDOS)) {
            sendMessage += "暂无开服记录\n";
        } else {
            for (ServiceStatusRecordDO recordDO : openRecordDOS) {
                sendMessage += DateUtil.toYMDHMS(recordDO.getGmtCreate()) + "\n";
            }
        }
        sendMessage += "近五次关服记录：\n";
        if (CollectionUtils.isEmpty(closeRecordDOS)) {
            sendMessage += "暂无关服记录\n";
        } else {
            for (ServiceStatusRecordDO recordDO : closeRecordDOS) {
                sendMessage += DateUtil.toYMDHMS(recordDO.getGmtCreate()) + "\n";
            }
        }
        return sendMessage.substring(0,sendMessage.length() - 1);
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class OpenEvent {

        /**
         * 主服务器ID
         */
        private Long mainId;
        /**
         * 状态
         */
        private Integer status;

    }
}
