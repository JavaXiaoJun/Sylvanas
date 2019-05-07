package com.study.discovery.service;


import com.study.discovery.model.News;
import com.study.discovery.model.PageQuery;

import java.util.List;

/**
 * Created by jn50 on 2018/2/12.
 */
public interface INewsService {

    int addNews(News news);

    int updateNews(News news);

    int deleteNews(String newsId);

    News getNewsById(String newsId);

    List<News> getNewsList(PageQuery pageQuery);

    List<News> getRecentNewsList();

    int getNewsCount();

}
