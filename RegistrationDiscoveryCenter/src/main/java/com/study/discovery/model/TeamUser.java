package com.study.discovery.model;

/**
 * Team and user relation
 *
 * @author Jay.H.Zou
 * @date 2018/8/4
 */
public class TeamUser {
    private String id;

    private String teamId;

    private String userId;

    public TeamUser() {
    }

    public TeamUser(String id, String teamId, String userId) {
        this.id = id;
        this.teamId = teamId;
        this.userId = userId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "TeamUser{" +
                "id='" + id + '\'' +
                ", teamId='" + teamId + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }
}
