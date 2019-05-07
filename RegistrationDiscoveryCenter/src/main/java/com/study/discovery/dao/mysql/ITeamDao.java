package com.study.discovery.dao.mysql;


import com.study.discovery.model.Team;

import java.util.List;

/**
 * Created by jn50 on 2018/5/4.
 */
public interface ITeamDao {

    void addTeam(Team team);

    void  updateTeam(Team team);

    void deleteTeam(String teamId);

    Team getTeam(String teamId);

    List<Team> getTeamList();
}
