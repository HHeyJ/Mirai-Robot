package com.hyq.robot.facade.message.team;

import com.hyq.robot.DO.TeamDO;
import com.hyq.robot.DO.TeamMemberDO;
import com.hyq.robot.dao.TeamDAO;
import com.hyq.robot.dao.TeamMemberDAO;
import com.hyq.robot.query.TeamMemberQuery;
import com.hyq.robot.query.TeamQuery;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author nanke
 * @date 2021/5/28 上午11:27
 * 致终于来到这里的勇敢的人:
 * 永远不要放弃！永远不要对自己失望！永远不要逃走辜负了自己。
 * 永远不要哭啼！永远不要说再见！永远不要说慌来伤害目己。
 */
@Component
public class TeamFacade {

    @Resource
    private TeamDAO teamDAO;
    @Resource
    private TeamMemberDAO teamMemberDAO;

    /**
     * 检查团队是否存在
     * @param groupId
     * @return
     */
    public String checkTeam(Long groupId) {
        TeamQuery query = new TeamQuery();
        query.setGroupId(groupId);
        List<TeamDO> teamDOS = teamDAO.queryByCondition(query);
        if (CollectionUtils.isEmpty(teamDOS)) {
            return "🙅团长被抓";
        }
        return null;
    }

    /**
     * 获取第一个团队
     * @param groupId
     * @return
     */
    public TeamDO getTeam(Long groupId) {
        TeamQuery query = new TeamQuery();
        query.setGroupId(groupId);
        List<TeamDO> teamDOS = teamDAO.queryByCondition(query);
        return CollectionUtils.isEmpty(teamDOS) ? null : teamDOS.get(0);
    }

    /**
     * 获取团队位置成员
     * @param teamId
     * @return
     */
    public List<TeamMemberDO> getTeamMember(Long teamId, Long location) {
        TeamMemberQuery memberQuery = new TeamMemberQuery();
        memberQuery.setTeamId(teamId);
        memberQuery.setLocation(location);
        return teamMemberDAO.queryByCondition(memberQuery);
    }

}
