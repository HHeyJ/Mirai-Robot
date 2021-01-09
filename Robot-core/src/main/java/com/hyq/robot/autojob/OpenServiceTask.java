package com.hyq.robot.autojob;

import com.google.common.eventbus.AsyncEventBus;
import com.hyq.robot.DO.MainServiceDO;
import com.hyq.robot.dao.MainServiceDAO;
import com.hyq.robot.enums.EnumOpenStatus;
import com.hyq.robot.facade.trans.OpenServiceTrans;
import com.hyq.robot.listener.OpenListener;
import com.hyq.robot.query.MainServiceQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
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
    private OpenServiceTrans openServiceTrans;
    @Resource
    private AsyncEventBus asyncEventBus;

    @Scheduled(cron = "0/10 * * * * ? ")
    public void task() {

        List<MainServiceDO> mainServiceDOS = mainServiceDAO.queryByCondition(new MainServiceQuery());
        for (MainServiceDO serviceDO : mainServiceDOS) {
            boolean needRecord = singleService(serviceDO);
            if (needRecord) {
                // 设置相反服务器状态
                Integer openStatus = serviceDO.getOpenStatus().equals(EnumOpenStatus.CLOSE.status) ?
                        EnumOpenStatus.OPEN.status : EnumOpenStatus.CLOSE.status;
                // 事务
                try {
                    openServiceTrans.changeStatus(serviceDO.getMainId(),openStatus,serviceDO.getMainServiceName());
                    // 触发监听消息通知
                    asyncEventBus.post(new OpenListener.OpenEvent(serviceDO.getMainId(),openStatus));
                } catch (Exception e) {
                    log.error("服务器状态变更事务处理失败,服务器:{},操作前状态:{}",
                            serviceDO.getMainServiceName(),serviceDO.getOpenStatus(),e);
                }
            }
        }
    }



    /**
     * 单个服务器是否需要变更开服状态
     * @param serviceDO
     * @return
     */
    private boolean singleService(MainServiceDO serviceDO) {

        try {
            Socket socket = new Socket();
            socket.connect(new InetSocketAddress(serviceDO.getIpAddress(),serviceDO.getPort()),2000);
            if (socket.isConnected() && serviceDO.getOpenStatus().equals(0)) {
                // 已经开服,但数据库为关服
                return true;
            }
            if (!socket.isConnected() && serviceDO.getOpenStatus().equals(1)) {
                // 已经关服,但数据库为开服
                return true;
            }
        } catch (SocketTimeoutException e) {
            if (serviceDO.getOpenStatus().equals(1)) {
                // 已经关服,但数据库为开服
                return true;
            }
        } catch (Exception e) {
            log.error("链接剑三服务器发生异常,ip:{}",serviceDO.getIpAddress(),e);
        }
        return false;
    }

}
