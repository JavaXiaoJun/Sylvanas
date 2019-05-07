package com.study.monitor.core.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.study.monitor.core.business.IActuatorDataCollection;
import com.study.monitor.core.entity.Constants;
import com.study.monitor.core.service.IMonitorService;
import com.study.monitor.core.utils.CommonUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * Created by lf52 on 2018/7/14.
 */
@Component
public class MonitorService implements IMonitorService {

    private static final Log logger = LogFactory.getLog(MonitorService.class);

    @Autowired
    IActuatorDataCollection actuatorDataCollection;

    @Value("${spring.application.name:DefaultValue}")
    private String name;

    @Override
    public JSONObject getServiceInfo() {

        JSONObject result = new JSONObject();

        JSONObject healthInfo = actuatorDataCollection.getHealthInfo();
        JSONObject processor = actuatorDataCollection.getMetricsDetailInfo(Constants.System_Cpu_Count);
        JSONObject startTime = actuatorDataCollection.getMetricsDetailInfo(Constants.Process_Start_Time);
        JSONObject cpuUsage = actuatorDataCollection.getMetricsDetailInfo(Constants.Process_Cpu_Usage);
        JSONObject upTime = actuatorDataCollection.getMetricsDetailInfo(Constants.Process_Uptime);

        if (healthInfo != null) {
            if (healthInfo.containsKey(Constants.Status)) {
                result.put(Constants.Status, healthInfo.getString(Constants.Status));
            }
        }

        if (processor != null) {
            JSONArray arr = processor.getJSONArray(Constants.Measurements);
            if (arr.size() > 0) {
                result.put(Constants.Processor, arr.getJSONObject(0).getString(Constants.Value));
            }
        }

        if (startTime != null) {
            JSONArray arr = startTime.getJSONArray(Constants.Measurements);
            if (arr.size() > 0) {
                String value = arr.getJSONObject(0).getString(Constants.Value);
                BigDecimal bigDecimal = BigDecimal.valueOf(Double.valueOf(value) * 1000);
                result.put(Constants.StartTime, CommonUtils.formatTimestamp(bigDecimal.longValue()));
            }
        }

        if (cpuUsage != null) {
            JSONArray arr = cpuUsage.getJSONArray(Constants.Measurements);
            if (arr.size() > 0) {
                String value = CommonUtils.formatNumber(Float.parseFloat(arr.getJSONObject(0).getString(Constants.Value)));
                Float f = Float.parseFloat(value);
                result.put(Constants.CpuUsage, f + "%");
            }
        }

        if (upTime != null) {
            JSONArray arr = upTime.getJSONArray(Constants.Measurements);
            if (arr.size() > 0) {
                String value = arr.getJSONObject(0).getString(Constants.Value);
                BigDecimal bigDecimal = BigDecimal.valueOf(Double.valueOf(value) * 1000);
                result.put(Constants.Uptime, CommonUtils.formatTime(bigDecimal.longValue()));
            }
        }

        result.put(Constants.ServiceName, name);

        return result;

    }

}
