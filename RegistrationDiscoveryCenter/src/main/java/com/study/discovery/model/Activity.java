package com.study.discovery.model;

/**
 * Created by lf52 on 2018/6/28.
 */
public class Activity {

    String username;
    String description;
    String time;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Activity{" +
                "username='" + username + '\'' +
                ", description='" + description + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
