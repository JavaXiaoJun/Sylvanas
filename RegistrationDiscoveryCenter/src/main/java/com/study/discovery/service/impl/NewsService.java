package com.study.discovery.service.impl;

import com.newegg.ec.base.util.UUIDUtil;
import com.study.discovery.dao.mysql.INewsDao;
import com.study.discovery.model.News;
import com.study.discovery.model.PageQuery;
import com.study.discovery.service.INewsService;
import com.study.discovery.util.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @author Jay.H.Zou
 * @date 2018/8/3
 */
@Service
public class NewsService implements INewsService {

    @Autowired
    private INewsDao newsDao;

    @Override
    public int addNews(News news) {
        int row = 0;
       if(news != null){
           news.setUpdateTime(CommonUtils.getNowTimestamp());
           news.setNewsId(UUIDUtil.getUUID());
           row = newsDao.addNews(news);
       }
       return row;
    }

    @Override
    public int updateNews(News news) {
        int row = 0;
        if (news != null) {
            news.setUpdateTime(CommonUtils.getNowTimestamp());
            row = newsDao.updateNews(news);
        }
        return row;
    }

    @Override
    public int deleteNews(String newsId) {
        int row = 0;
        if (!StringUtils.isEmpty(newsId)) {
            row = newsDao.deleteNews(newsId);
        }
        return row;
    }

    @Override
    public News getNewsById(String newsId) {
        News news = null;
        if (!StringUtils.isEmpty(newsId)) {
            news = newsDao.getNewsById(newsId);
        }
        return news;
    }

    @Override
    public List<News> getNewsList(PageQuery pageQuery) {
        return newsDao.getNewsList(pageQuery);
    }

    @Override
    public List<News> getRecentNewsList() {
        return newsDao.getRecentNewsList();
    }

    @Override
    public int getNewsCount() {
        return newsDao.getNewsCount();
    }
}
