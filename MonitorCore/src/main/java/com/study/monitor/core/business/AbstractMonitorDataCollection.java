package com.study.monitor.core.business;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by lf52 on 2018/7/7.
 * 通用的数据收集接口，使用json作为数据传输格式，cilent端需要实现此接口来传输monitor数据
 */
public abstract class AbstractMonitorDataCollection {

   protected abstract JSONObject getMonitorData();

}
