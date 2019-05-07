package com.study.monitor.core.business.actuator;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.study.monitor.core.business.IActuatorDataCollection;
import com.study.monitor.core.utils.CommonUtils;
import com.study.monitor.core.utils.HttpClientUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


/**
 * Created by lf52 on 2018/7/7.
 */
@Component
public class ActuatorDataCollection implements IActuatorDataCollection {

    private static final Log logger = LogFactory.getLog(ActuatorDataCollection.class);

    @Value("${server.port}")
    private String port;

    @Override
    public JSONObject getHealthInfo(){
        JSONObject response = null;
        try {
            String host = CommonUtils.getHost();
            String url = "http://"+host+":"+port+"/health";
            response = JSON.parseObject(HttpClientUtil.getGetResponse(url, null));
        } catch (Exception e) {
            logger.error("Get Health Info Error",e);
        }
       return response;
    }

    @Override
    public JSONObject getMetricsInfo(){

        JSONObject response = null;
        try {
            String host = CommonUtils.getHost();
            String url = "http://"+host+":"+port+"/metrics";
            response = JSON.parseObject(HttpClientUtil.getGetResponse(url, null));
        } catch (Exception e) {
            logger.error("Get Metrics Info Error", e);
        }
        return response;
    }

    @Override
    public JSONObject getActuatorInfo(String actuator) {
        JSONObject response = null;
        try {
            String host = CommonUtils.getHost();
            String url = "http://"+host+":"+port+"/"+actuator;
            response = JSON.parseObject(HttpClientUtil.getGetResponse(url, null));
        } catch (Exception e) {
            logger.error("Get " + actuator + " Info Error", e);
        }
        return response;
    }

    @Override
    public JSONArray getActuatorArrayInfo(String actuator) {
        JSONArray response = null;
        try {
            String host = CommonUtils.getHost();
            String url = "http://"+host+":"+port+"/"+actuator;
            response = JSONArray.parseArray(HttpClientUtil.getGetResponse(url, null));
        } catch (Exception e) {
            logger.error("Get " + actuator + " Info Error", e);
        }
        return response;
    }

}
