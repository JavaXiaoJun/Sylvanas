package com.study.discovery.service.impl;

import com.newegg.ec.base.model.User;
import com.newegg.ec.base.util.UUIDUtil;
import com.study.discovery.dao.mysql.ITeamDao;
import com.study.discovery.dao.mysql.ITeamUserDao;
import com.study.discovery.model.Team;
import com.study.discovery.model.TeamUser;
import com.study.discovery.service.ITeamService;
import com.study.discovery.util.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by jn50 on 2018/5/4.
 */
@Service
public class TeamService implements ITeamService {

    @Autowired
    private ITeamDao teamDao;

    @Autowired
    private ITeamUserDao teamUserDao;

    @Override
    public void addOrUpdate(Team team) {
        if(team != null) {
            if (StringUtils.isEmpty(team.getId())){
                team.setId(UUIDUtil.getUUID());
                teamDao.addTeam(team);
            }else{
                teamDao.updateTeam(team);
            }
        }
    }

    @Override
    public void deleteTeam(String id) {
        teamDao.deleteTeam(id);
    }

    @Override
    public List<Team> getTeamList() {
        return teamDao.getTeamList();
    }

    @Override
    public List<User> getUserListByTeamId(String teamId) {
        List<User> userList = null;
        if (!StringUtils.isEmpty(teamId)) {
            userList = teamUserDao.getUserByTeamId(teamId);
        }
        return userList;
    }

    @Override
    public void updateUsersOfTeam(String teamId, List<String> userIdList) {
        if (StringUtils.isEmpty(teamId)) {
            return;
        }

        if (userIdList == null || userIdList.isEmpty()) {
            teamUserDao.deleteAllUserInTeam(teamId);
            return;
        }

        List<TeamUser> addList = new ArrayList<>();
        List<User> userByTeamId = teamUserDao.getUserByTeamId(teamId);
        List<String> oldUserIdList = new ArrayList<>();
        for (User user : userByTeamId) {
            oldUserIdList.add(user.getId());
        }
        for (String userId : userIdList) {
            if (oldUserIdList.contains(userId)) {
                oldUserIdList.remove(userId);
            } else {
                TeamUser teamUser = new TeamUser(CommonUtils.getUUID(), teamId, userId);
                addList.add(teamUser);
            }
        }
        if (addList.size() > 0) {
            teamUserDao.addTeamUserBatch(addList);
        }
        if (oldUserIdList.size() > 0) {
            teamUserDao.deleteTeamUserBatch(teamId, oldUserIdList);
        }
    }
}
