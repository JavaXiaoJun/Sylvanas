package com.study.discovery.dao;

import java.util.Map;

/**
 * Created by jn50 on 2018/2/2.
 */
public interface IServiceDao {

    public String get(String url) throws Exception;

    public String post(String url) throws Exception;

    public String doGet(String url, Map<String, Object> map) throws Exception;

    public String doPost(String url, Map<String, Object> map) throws Exception;

}
