package com.study.discovery.service;

import com.newegg.ec.base.model.User;
import com.study.discovery.model.Team;

import java.util.List;

/**
 * Created by jn50 on 2018/5/4.
 */
public interface ITeamService {

    void addOrUpdate(Team team);

    void deleteTeam(String id);

    List<Team> getTeamList();

    /**
     *  select users in one team
     *
     * @param teamId
     * @return
     */
    List<User> getUserListByTeamId(String teamId);

    void updateUsersOfTeam(String teamId, List<String> userIdList);

}
