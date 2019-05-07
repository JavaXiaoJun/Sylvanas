package com.study.discovery.dao.mysql;



import com.study.discovery.model.Activity;

import java.util.List;

/**
 * Created by lf52 on 2018/6/28.
 */
public interface IActivityDao {

    public void addActivity(Activity activity);

    public List<Activity> getActivityList();

}
