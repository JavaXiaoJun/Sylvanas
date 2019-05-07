package com.study.discovery.model;

/**
 * app collection
 *
 * @author Jay.H.Zou
 * @date 2018/8/1
 */
public class Collection {

    private String collectionId;

    private String teamId;

    private String appName;

    private int collected = 0;

    public String getCollectionId() {
        return collectionId;
    }

    public void setCollectionId(String collectionId) {
        this.collectionId = collectionId;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public int getCollected() {
        return collected;
    }

    public void setCollected(int collected) {
        this.collected = collected;
    }

    @Override
    public String toString() {
        return "Collection{" +
                "collectionId='" + collectionId + '\'' +
                ", teamId='" + teamId + '\'' +
                ", appName='" + appName + '\'' +
                ", collected=" + collected +
                '}';
    }
}
