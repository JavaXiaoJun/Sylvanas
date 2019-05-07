package com.study.discovery.model;

import java.sql.Timestamp;

/**
 * @author Jay.H.Zou
 * @date 2018/8/3
 */
public class News {
    private String newsId;

    private String title;

    private String content;

    private Timestamp updateTime;

    public News(){}

    public News(String newsId, String title, String content, Timestamp updateTime) {
        this.newsId = newsId;
        this.title = title;
        this.content = content;
        this.updateTime = updateTime;
    }

    public String getNewsId() {
        return newsId;
    }

    public void setNewsId(String newsId) {
        this.newsId = newsId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "News{" +
                "newsId='" + newsId + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", updateTime='" + updateTime + '\'' +
                '}';
    }
}
