package com.study.discovery.dao.mysql;

import com.study.discovery.model.News;
import com.study.discovery.model.PageQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Jay.H.Zou
 * @date 2018/8/3
 */
@Repository
public interface INewsDao {

    int addNews(News news);

    int  updateNews(News news);

    int deleteNews(String newsId);

    News getNewsById(String newsId);

    List<News> getNewsList(PageQuery pageQuery);

    List<News> getRecentNewsList();

    int getNewsCount();
}
