package com.study.monitor.core.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.study.monitor.core.adapter.MetricInfoAdapter;
import com.study.monitor.core.business.IActuatorDataCollection;
import com.study.monitor.core.business.actuator.MonitorDataCollection;
import com.study.monitor.core.entity.HealthInfo;
import com.study.monitor.core.entity.MetricsInfo;
import com.study.monitor.core.entity.ServiceBaseInfo;
import com.study.monitor.core.utils.CommonUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.UnknownHostException;

/**
 * @author lf52
 * @date 2018/7/10
 */
@RestController
@RequestMapping("/collection/*")
public class IndexController {

    private static final Log logger = LogFactory.getLog(IndexController.class);

    private static String field = "field";

    @Autowired
    IActuatorDataCollection actuatorDataCollection;

    @Value("${server.port:8080}")
    private String port;
    @Value("${spring.application.name:DefaultValue}")
    private String name;
    @Value("${customize.monitor.class:DefaultValue}")
    private String classpath;

    @RequestMapping(value="/getBaseInfo",method= RequestMethod.POST)
    public ServiceBaseInfo getBaseInfo() {
        ServiceBaseInfo info = new ServiceBaseInfo();
        try {
            info.setHost(CommonUtils.getHost());
        } catch (UnknownHostException e) {
            logger.error("Get Service Host Error",e);
        }
        info.setPort(port);
        info.setName(name);
        return info;
    }

    @RequestMapping(value="/getMonitorData",method= RequestMethod.POST)
    public JSONObject getMonitorData() {

        JSONObject result = null;
        try {
            Class<?> clazz = Class.forName(classpath);
            Method getMonitorData = clazz.getMethod("getMonitorData");//得到方法对象
            getMonitorData.setAccessible(true);
            Object c = clazz.newInstance();
            result = (JSONObject)getMonitorData.invoke(c);

        } catch (ClassNotFoundException e) {
            result =  new MonitorDataCollection().getMonitorData();
        } catch (NoSuchMethodException e) {
            logger.error("", e);
        } catch (InvocationTargetException e) {
            logger.error("", e);
        } catch (IllegalAccessException e) {
            logger.error("", e);
        } catch (InstantiationException e) {
            logger.error("",e);
        }
        return result;
    }

    @RequestMapping(value="/getHealthInfo",method= RequestMethod.POST)
    public HealthInfo getHealthInfo() {
        return MetricInfoAdapter.HealthAdapter(actuatorDataCollection.getHealthInfo());
    }

    @RequestMapping(value="/getMetricsInfo",method= RequestMethod.POST)
    public MetricsInfo getMetricsInfo() {
        return MetricInfoAdapter.MetricsAdapter(actuatorDataCollection.getMetricsInfo());
    }

    @RequestMapping(value="/beans",method= RequestMethod.POST)
    public JSONArray beans(@RequestBody JSONObject json) {
        return actuatorDataCollection.getActuatorArrayInfo(json.getString(field));
    }

    @RequestMapping(value="/env",method= RequestMethod.POST)
    public JSONObject env(@RequestBody JSONObject json) {
        return actuatorDataCollection.getActuatorInfo(json.getString(field));
    }

    @RequestMapping(value="/mappings",method= RequestMethod.POST)
    public JSONObject mappings(@RequestBody JSONObject json) {
        return actuatorDataCollection.getActuatorInfo(json.getString(field));
    }

    @RequestMapping(value="/info",method= RequestMethod.POST)
    public JSONObject info(@RequestBody JSONObject json) {
        return actuatorDataCollection.getActuatorInfo(json.getString(field));
    }

    @RequestMapping(value="/trace",method= RequestMethod.POST)
    public JSONArray trace(@RequestBody JSONObject json) {

        String path = json.getString(field);
        String start = json.getString("start");
        String end = json.getString("end");
        JSONArray result = actuatorDataCollection.getActuatorArrayInfo(path);
        return getTraceInfo(result,start,end);
    }

    @RequestMapping(value="/autoconfig",method= RequestMethod.POST)
    public JSONObject autoconfig(@RequestBody JSONObject json) {
        return actuatorDataCollection.getActuatorInfo(json.getString(field));
    }

    @RequestMapping(value="/configprops",method= RequestMethod.POST)
    public JSONObject configprops(@RequestBody JSONObject json) {
        return actuatorDataCollection.getActuatorInfo(json.getString(field));
    }

    @RequestMapping(value="/health",method= RequestMethod.POST)
    public JSONObject health(@RequestBody JSONObject json) {
        return actuatorDataCollection.getHealthInfo();
    }

    @RequestMapping(value="/metrics",method= RequestMethod.POST)
    public JSONObject metrics(@RequestBody JSONObject json) {
        return actuatorDataCollection.getMetricsInfo();
    }


    private JSONArray getTraceInfo(JSONArray result,String start,String end){

        JSONArray response = new JSONArray();
        start = start == "" ? "0" : start;
        end = end == "" ? "0" : end;
        try{
            if("0".equals(start) && "0".equals(end)){
                int num = 0;
                for(int i = result.size()-1 ; i > 0; i--){
                    num++;
                    if(num <= 10 ){
                        response.add(result.get(i));
                    }else {
                        break;
                    }
                }
            }else{
                final String finalStart = start;
                final String finalEnd = end;
                if(!"0".equals(start) &&  "0".equals(end)){
                    for(int i = 0 ; i < result.size() ; i++){
                        JSONObject jsonObject = result.getJSONObject(i);
                        if(Long.parseLong(jsonObject.getString("timestamp")) > Long.parseLong(finalStart)){
                            response.add(jsonObject);
                        }
                    }
                }else if(!"0".equals(start) && "0".equals(end)){
                    for(int i = 0 ; i < result.size() ; i++){
                        JSONObject jsonObject = result.getJSONObject(i);
                        if(Long.parseLong(jsonObject.getString("timestamp")) < Long.parseLong(finalEnd)){
                            response.add(jsonObject);
                        }
                    }
                }else{
                    for(int i = 0 ; i < result.size() ; i++){
                        JSONObject jsonObject = result.getJSONObject(i);
                        if((Long.parseLong(jsonObject.getString("timestamp")) > Long.parseLong(finalStart)) && (Long.parseLong(jsonObject.getString("timestamp")) < Long.parseLong(finalEnd))){
                            response.add(jsonObject);
                        }
                    }
                }
            }
        }catch (Exception e){
            JSONObject object = new JSONObject();
            object.put("message",e.getMessage());
            response.add(object);
        }

        return response;
    }
}
