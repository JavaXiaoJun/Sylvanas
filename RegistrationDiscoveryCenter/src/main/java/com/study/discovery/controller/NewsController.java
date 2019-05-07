package com.study.discovery.controller;

import com.newegg.ec.base.model.CommonResponse;
import com.study.discovery.model.News;
import com.study.discovery.model.PageQuery;
import com.study.discovery.service.INewsService;
import com.study.discovery.util.CommonUtils;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author Jay.H.Zou
 * @date 2018/8/3
 */
@Controller
@RequestMapping("/rest/*")
public class NewsController {

    @Autowired
    private INewsService newsService;

    @RequestMapping(value = "/addNews", method = RequestMethod.POST)
    @ResponseBody
    public CommonResponse addNews(@RequestBody News news) {
        CommonResponse response = new CommonResponse();
        int row = newsService.addNews(news);
        response.setStatus(String.valueOf(row));
        return response;
    }

    @RequestMapping(value = "/updateNews", method = RequestMethod.POST)
    @ResponseBody
    public CommonResponse updateNews(@RequestBody News news) {
        CommonResponse response = new CommonResponse();
        int row = newsService.updateNews(news);
        response.setStatus(String.valueOf(row));
        return response;
    }

    @RequestMapping(value = "/deleteNews", method = RequestMethod.POST)
    @ResponseBody
    public CommonResponse deleteNews(String newsId) {
        CommonResponse response = new CommonResponse();
        int row = newsService.deleteNews(newsId);
        response.setStatus(String.valueOf(row));
        return response;
    }

    @RequestMapping(value = "/getNewsById", method = RequestMethod.GET)
    @ResponseBody
    public CommonResponse getNewsById(String newsId) {
        CommonResponse response = new CommonResponse();
        News news = newsService.getNewsById(newsId);
        if (news != null) {
            JSONObject jsonObject = JSONObject.fromObject(news);
            jsonObject.put("updateTime", CommonUtils.formatTimestamp(news.getUpdateTime()));
            response.setStatus("1");
            response.setResult(jsonObject);
        } else {
            response.setStatus("0");
        }
        return response;
    }

    @RequestMapping(value = "/getNewsList", method = RequestMethod.POST)
    @ResponseBody
    public CommonResponse getNewsList(@RequestBody PageQuery pageQuery) {
        CommonResponse response = new CommonResponse();

        List<News> newsList = newsService.getNewsList(pageQuery);
        int newsCount = newsService.getNewsCount();

        Map<String,Object> resultMap = new HashMap();
        List<JSONObject> result = formatNewsListTime(newsList);
        resultMap.put("pageData", result);
        resultMap.put("totalCount", newsCount);
        if(newsCount > 0) {
            response.setStatus("1");
            response.setResult(resultMap);
        } else {
            response.setStatus("0");
        }
        return response;
    }

    @RequestMapping(value = "/getRecentNewsList", method = RequestMethod.POST)
    @ResponseBody
    public CommonResponse getRecentNewsList() {
        CommonResponse response = new CommonResponse();
        List<News> recentNewsList = newsService.getRecentNewsList();
        if (recentNewsList != null && recentNewsList.size() > 0) {
            List<JSONObject> result = formatNewsListTime(recentNewsList);
            response.setStatus("1");
            response.setResult(result);
        } else {
            response.setStatus("0");
        }
        return response;
    }

    private List<JSONObject> formatNewsListTime(List<News> newsList) {
        List<JSONObject> result = new LinkedList<>();
        if (newsList != null &&newsList.size() > 0) {
            for (News news : newsList) {
                String updateTime = CommonUtils.formatTimestamp(news.getUpdateTime());
                JSONObject jsonObject = JSONObject.fromObject(news);
                jsonObject.put("updateTime", updateTime);
                result.add(jsonObject);
            }
        }
        return result;
    }

}
