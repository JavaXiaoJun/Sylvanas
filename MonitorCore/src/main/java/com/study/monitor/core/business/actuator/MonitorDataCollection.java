package com.study.monitor.core.business.actuator;

import com.alibaba.fastjson.JSONObject;
import com.study.monitor.core.business.AbstractMonitorDataCollection;

/**
 * Created by lf52 on 2018/7/13.
 */
public class MonitorDataCollection extends AbstractMonitorDataCollection {

    @Override
    public JSONObject getMonitorData() {
        JSONObject result = new JSONObject();
        JSONObject object = new JSONObject();
        object.put("No Customize Info","");
        result.put("User Customize Info", object);
        return result;
    }

}
