package com.study.discovery.service.impl;

import com.study.discovery.dao.IServiceDao;
import com.study.discovery.dao.mysql.IAppDao;
import com.study.discovery.dao.mysql.ITeamUserDao;
import com.study.discovery.model.App;
import com.study.discovery.model.TeamUser;
import com.study.discovery.service.IManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by jn50 on 2018/2/2.
 */
@Service
public class ManagerService implements IManagerService {

    @Autowired
    private IServiceDao serviceDao;

    @Autowired
    private IAppDao appDao;

    @Autowired
    private ITeamUserDao teamUserDao;

    @Override
    public List<App> getAppCollectedList(String userId) {
        List<String> teamIdList = getTeamIdList(userId);
        List<App> appList = null;
        if (teamIdList != null) {
           appList = appDao.getAppCollectedList(teamIdList);
        }
        return appList;
    }

    @Override
    public List<App> getApps(String userId) {
        if (StringUtils.isEmpty(userId)) {
            return appDao.getAppList(null);
        }
        List<String> teamIdList = getTeamIdList(userId);
        List<App> appList = null;
        if (teamIdList != null) {
            appList = appDao.getAppList(teamIdList);
        }
        return appList;
    }

    /**
     * 通过 userId 查询该user所在team 列表
     * @param userId
     * @return
     */
    private List<String> getTeamIdList(String userId) {
        List<String> teamIdList = null;
        List<TeamUser> teamUserList = teamUserDao.getTeamIdListByUserId(userId);
        if (teamUserList != null && teamUserList.size() > 0) {
            teamIdList = new LinkedList<>();
            for (int i = 0, len = teamUserList.size(); i < len; i++) {
                TeamUser teamUser = teamUserList.get(i);
                teamIdList.add(teamUser.getTeamId());
            }
        }
        return teamIdList;
    }

    @Override
    public App getApp(String appName) {
        App app = appDao.getApp(appName);
        return app;
    }

    @Override
    public void addApp(App app) {
        appDao.addApp(app);
    }

    @Override
    public void dropApp(String appName) {
        appDao.deleteApp(appName);
    }

    @Override
    public void appShutDown(String theUrl) {
        try {
            serviceDao.post(theUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getAppCount() {
        return appDao.getAppCount();
    }
}
