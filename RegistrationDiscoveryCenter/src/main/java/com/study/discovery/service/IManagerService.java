package com.study.discovery.service;


import com.study.discovery.model.App;

import java.util.List;

/**
 * Created by jn50 on 2018/2/2.
 */
public interface IManagerService {


    List<App> getAppCollectedList(String userId);

    List<App> getApps(String userId);

    App getApp(String appName);

    void addApp(App app);

    void dropApp(String appName);

    void appShutDown(String theUrl);

    int getAppCount();
}
