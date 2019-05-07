package com.study.monitor.core.business;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * Created by lf52 on 2018/7/7.
 * actuator 监控信息的收集（内部实现）
 */
public interface IActuatorDataCollection {

    /**
     * 获取服务health信息
     *
     * @return
     */
    JSONObject getHealthInfo();

    /**
     * 获取服务metrics信息
     *
     * @return
     */
    JSONObject getMetricsInfo();

    /**
     * 获取每个metrics项实时信息
     *
     * @param info
     * @return
     */
    JSONObject getMetricsDetailInfo(String info);

    /**
     * 获取other actuator信息
     *
     * @param actuator
     * @return
     */
    JSONObject getActuatorInfo(String actuator);

    /**
     * 获取other actuator信息
     *
     * @param actuator
     * @return
     */
    JSONArray getActuatorArrayInfo(String actuator);

}
