package com.study.discovery.dao.mysql;

import com.study.discovery.model.App;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by jn50 on 2018/2/9.
 */
@Repository
public interface IAppDao {

    void addApp(App app);

    void deleteApp(String appName);

    App getApp(String appName);

    App getTeamIdByAppName(String appName);

    List<App> getAppCollectedList(@Param("teamIdList") List<String> teamIdList);

    List<App> getAppList(@Param("teamIdList") List<String> teamIdList);

    int getAppCount();
}
