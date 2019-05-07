package com.study.discovery.dao.mysql;

import com.newegg.ec.base.model.User;
import com.study.discovery.model.TeamUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Jay.H.Zou
 * @date 2018/8/4
 */
public interface ITeamUserDao {

    List<TeamUser> getTeamIdListByUserId(String userId);

    List<User> getUserByTeamId(String teamId);

    int addTeamUserBatch(@Param("teamUserList") List<TeamUser> teamUserList);

    int deleteTeamUserBatch(@Param("teamId") String teamId, @Param("userIdList") List<String> userIdList);

    int deleteAllUserInTeam(@Param("teamId") String teamId);
}
