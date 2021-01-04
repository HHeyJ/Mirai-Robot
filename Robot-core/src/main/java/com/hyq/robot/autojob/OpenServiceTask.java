package com.hyq.robot.autojob;

import com.hyq.robot.DO.MainServiceDO;
import com.hyq.robot.DO.ServiceStatusRecordDO;
import com.hyq.robot.dao.MainServiceDAO;
import com.hyq.robot.dao.ServiceStatusRecordDAO;
import com.hyq.robot.query.MainServiceQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.List;

/**
 * @author nanke
 * @date 2020/11/12 2:35 下午
 * 致终于来到这里的勇敢的人:
 * 永远不要放弃！永远不要对自己失望！永远不要逃走辜负了自己。
 * 永远不要哭啼！永远不要说再见！永远不要说慌来伤害目己。
 */
@Configuration
@EnableScheduling
@Slf4j
public class OpenServiceTask {

    @Resource
    private MainServiceDAO mainServiceDAO;
    @Resource
    private ServiceStatusRecordDAO serviceStatusRecordDAO;

    @Scheduled(cron = "0/10 * * * * ? ")
    public void task() {

        List<MainServiceDO> mainServiceDOS = mainServiceDAO.queryByCondition(new MainServiceQuery());
        for (MainServiceDO serviceDO : mainServiceDOS) {
            boolean needRecord = singleService(serviceDO);
            if (needRecord) {
                // TODO 事务
                MainServiceDO updateServiceDO = new MainServiceDO();
                updateServiceDO.setMainId(serviceDO.getMainId());
                // 设置相反服务器状态
                Integer openStatus = serviceDO.getOpenStatus().equals(0) ? 1 : 0;
                updateServiceDO.setOpenStatus(openStatus);
                mainServiceDAO.updateById(updateServiceDO);
                // 记录状态
                ServiceStatusRecordDO insertRecordDO = new ServiceStatusRecordDO();
                insertRecordDO.setMainId(serviceDO.getMainId());
                insertRecordDO.setMainServiceName(serviceDO.getMainServiceName());
                insertRecordDO.setOpenStatus(openStatus);
                insertRecordDO.setOpenStatusMsg(openStatus.equals(0) ? "关服" : "开服");
                serviceStatusRecordDAO.insertSelective(insertRecordDO);
            }
        }
    }



    /**
     * 单个服务器是否需要变更开服状态
     * @param serviceDO
     * @return
     */
    private static boolean singleService(MainServiceDO serviceDO) {

        try {
            Socket socket = new Socket();
            socket.connect(new InetSocketAddress(serviceDO.getIpAddress(),serviceDO.getPort()),10000);
            if (socket.isConnected() && serviceDO.getOpenStatus().equals(0)) {
                // 已经开服,但数据库为关服
                return true;
            }
            if (!socket.isConnected() && serviceDO.getOpenStatus().equals(1)) {
                // 已经关服,但数据库为开服
                return true;
            }
        } catch (Exception e) {
            log.error("链接剑三服务器发生异常,ip:{}",serviceDO.getIpAddress(),e);
        }
        return false;
    }

}
