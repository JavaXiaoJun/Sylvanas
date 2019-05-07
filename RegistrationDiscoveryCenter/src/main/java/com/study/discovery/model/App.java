package com.study.discovery.model;

/**
 * Created by jn50 on 2018/2/9.
 */
public class App {

    private String name;

    private String type;

    private String teamId;

    private String teamName;

    private String collectionId;

    private int collected = 0;

    private int availableNodes;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getCollectionId() {
        return collectionId;
    }

    public void setCollectionId(String collectionId) {
        this.collectionId = collectionId;
    }

    public int getCollected() {
        return collected;
    }

    public void setCollected(int collected) {
        this.collected = collected;
    }

    public int getAvailableNodes() {
        return availableNodes;
    }

    public void setAvailableNodes(int availableNodes) {
        this.availableNodes = availableNodes;
    }


    @Override
    public String toString() {
        return "App{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", teamId='" + teamId + '\'' +
                ", teamName='" + teamName + '\'' +
                ", collectionId='" + collectionId + '\'' +
                ", collected=" + collected +
                ", availableNodes=" + availableNodes +
                '}';
    }
}
