package com.hyq.robot.facade.trans;

import com.hyq.robot.DO.MainServiceDO;
import com.hyq.robot.DO.ServiceStatusRecordDO;
import com.hyq.robot.dao.MainServiceDAO;
import com.hyq.robot.dao.ServiceStatusRecordDAO;
import com.hyq.robot.enums.EnumOpenStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author nanke
 * @date 2021/1/6 上午11:18
 * 致终于来到这里的勇敢的人:
 * 永远不要放弃！永远不要对自己失望！永远不要逃走辜负了自己。
 * 永远不要哭啼！永远不要说再见！永远不要说慌来伤害目己。
 */
@Component
public class OpenServiceTrans {

    @Resource
    private MainServiceDAO mainServiceDAO;
    @Resource
    private ServiceStatusRecordDAO serviceStatusRecordDAO;


    /**
     * 服务器状态发生变更,进行修改状态与插入变更记录事务操作
     * @param mainId
     * @param status
     * @param serviceName
     */
    @Transactional
    public void changeStatus(Long mainId, Integer status, String serviceName) {
        // 本不该在事务里包装东西的,可以我实在是太懒了
        MainServiceDO updateServiceDO = new MainServiceDO();
        updateServiceDO.setMainId(mainId);
        // 设置相反服务器状态
        updateServiceDO.setOpenStatus(status);
        // 记录状态
        ServiceStatusRecordDO insertRecordDO = new ServiceStatusRecordDO();
        insertRecordDO.setMainId(mainId);
        insertRecordDO.setMainServiceName(serviceName);
        insertRecordDO.setOpenStatus(status);
        insertRecordDO.setOpenStatusMsg(EnumOpenStatus.get(status).desc);
        // 操作
        mainServiceDAO.updateById(updateServiceDO);
        serviceStatusRecordDAO.insertSelective(insertRecordDO);

    }


}
