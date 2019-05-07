package com.study.monitor.core.adapter;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.study.monitor.core.entity.Constants;
import com.study.monitor.core.entity.HealthInfo;
import com.study.monitor.core.entity.MetricsInfo;
import com.study.monitor.core.entity.Status;
import com.study.monitor.core.utils.CommonUtils;


import java.util.HashMap;
import java.util.Map;

/**
 * Created by lf52 on 2018/7/10.
 */
public class MetricInfoAdapter {


    public static final String Services = "services";
    public static final String Total = "total";
    public static final String Free = "free";
    public static final String Threshold = "threshold";

    /**
     * health info adapter
     * @param info
     * @return
     */
    public static HealthInfo HealthAdapter(JSONObject info){

        HealthInfo healthInfo = new HealthInfo();

        if(info == null){
            return healthInfo;
        }

        if(info.containsKey(Constants.Description)){
            healthInfo.setDescription(info.getString(Constants.Description));
        }

        if(info.containsKey(Constants.STATUS)){
            if(info.getString(Constants.STATUS).equals(Constants.UP)){
                healthInfo.setServiceStatus(Status.UP);
            }
            if(info.getString(Constants.STATUS).equals(Constants.DWON)){
                healthInfo.setServiceStatus(Status.DWON);
            }
        }
        if(info.containsKey(Constants.DiscoveryComposite) && info.get(Constants.DiscoveryComposite) instanceof JSONObject){

                JSONObject obj = info.getJSONObject(Constants.DiscoveryComposite);
                Map<String,Object> discoveryComposite = new HashMap();
                discoveryComposite.put(Constants.Description,obj.getString(Constants.Description));
                if(healthInfo.getDescription() == null){
                    healthInfo.setDescription(obj.getString(Constants.Description));
                }

                if(obj.containsKey(Constants.DiscoveryComposite) && obj.get(Constants.DiscoveryClient) instanceof JSONObject){
                    JSONObject obj1 = obj.getJSONObject(Constants.DiscoveryClient);
                    if(obj1.containsKey(Services) && obj1.get(Services) instanceof JSONArray){
                        discoveryComposite.put(Services,  obj1.getJSONArray(Services));
                    }
                }
            healthInfo.setDiscoveryComposite(discoveryComposite);

        }

        if(info.containsKey(Constants.DiskSpace) && info.get(Constants.DiskSpace) instanceof JSONObject){

            JSONObject obj = info.getJSONObject(Constants.DiskSpace);
            Map<String,Object> diskSpace = new HashMap();
            diskSpace.put(Constants.STATUS,obj.getString(Constants.STATUS));
            Float total= Float.parseFloat(obj.getString(Total))/1024/1024/1024;
            diskSpace.put(Total,total.intValue());
            Float free= Float.parseFloat(obj.getString(Free))/1024/1024/1024;
            diskSpace.put(Free,free.intValue());
            diskSpace.put(Threshold,obj.getString(Threshold));

            healthInfo.setDiskSpace(diskSpace);

        }

        if(info.containsKey(Constants.RefreshScope) && info.get(Constants.RefreshScope) instanceof JSONObject){

            JSONObject obj = info.getJSONObject(Constants.RefreshScope);
            if(obj.getString(Constants.STATUS).equals(Constants.UP)){
                healthInfo.setRefreshScope(Status.UP);
            }
            if(info.getString(Constants.STATUS).equals(Constants.DWON)){
                healthInfo.setRefreshScope(Status.DWON);
            }

        }

        if(info.containsKey(Constants.Hystrix) && info.get(Constants.Hystrix) instanceof JSONObject){

            JSONObject obj = info.getJSONObject(Constants.Hystrix);
            if(obj.getString(Constants.STATUS).equals(Constants.UP)){
                healthInfo.setHystrix(Status.UP);
            }
            if(info.getString(Constants.STATUS).equals(Constants.DWON)){
                healthInfo.setHystrix(Status.DWON);
            }

        }

        return healthInfo;

    }

    public static MetricsInfo MetricsAdapter(JSONObject info){

        MetricsInfo metricsInfo = new MetricsInfo();

        if(info == null){
            return metricsInfo;
        }

        /**
         * system info
         */
        Map<String,Object> systemInfo = new HashMap();
        if(info.containsKey(Constants.Processors)){
            systemInfo.put(Constants.Processors, info.getString(Constants.Processors));
        }
        if(info.containsKey(Constants.Uptime)){
            systemInfo.put(Constants.Uptime, CommonUtils.formatTime(Long.parseLong(info.getString(Constants.Uptime))));
        }
        if(info.containsKey(Constants.Instance_Uptime)){
            systemInfo.put(Constants.InstanceUptime, CommonUtils.formatTime(Long.parseLong(info.getString(Constants.Instance_Uptime))));
        }
        if(info.containsKey(Constants.Systemload_Average)){
            systemInfo.put(Constants.SystemloadAverage, info.getString(Constants.Systemload_Average));
        }
        metricsInfo.setSystemInfo(systemInfo);

        /**
         * mem info
         */
        Map<String,Object> memoryInfo = new HashMap();
        Float mem = 0.00f;
        Float mem_free = 0.00f;
        if(info.containsKey(Constants.Mem)){
            mem = Float.parseFloat(info.getString(Constants.Mem))/1024;
            memoryInfo.put(Constants.Mem, mem.intValue());
        }
        if(info.containsKey(Constants.Mem_Free)){
            mem_free= Float.parseFloat(info.getString(Constants.Mem_Free))/1024;
            memoryInfo.put(Constants.MemFree, mem_free.intValue());
        }
        memoryInfo.put(Constants.Per,(mem_free/mem ) * 100);
        metricsInfo.setMemoryInfo(memoryInfo);

        /**
         * heap info
         */
        Map<String,Object> heapInfo = new HashMap();
        Float heap_commit = 0.00f;
        Float heap_used = 0.00f;
        if(info.containsKey(Constants.Heap)){
            Float heap = Float.parseFloat(info.getString(Constants.Heap))/1024;
            heapInfo.put(Constants.Heap, heap.intValue());
        }
        if(info.containsKey(Constants.Heap_Committed)){
            heap_commit = Float.parseFloat(info.getString(Constants.Heap_Committed))/1024;
            heapInfo.put(Constants.HeapCommitted, heap_commit.intValue());
        }
        if(info.containsKey(Constants.Heap_Init)){
            Float Heap_init = Float.parseFloat(info.getString(Constants.Heap_Init))/1024;
            heapInfo.put(Constants.HeapInit, Heap_init.intValue());
        }
        if(info.containsKey(Constants.Heap_Used)){
            heap_used = Float.parseFloat(info.getString(Constants.Heap_Used))/1024;
            heapInfo.put(Constants.HeapUsed, heap_used.intValue());
        }
        heapInfo.put(Constants.Per,(heap_used/heap_commit ) * 100);
        metricsInfo.setHeapInfo(heapInfo);

        /**
         * non heap info
         */
        Map<String,Object> nonHeapInfo = new HashMap();
        Float nonheap_commit = 0.00f;
        Float nonheap_used = 0.00f;
        if(info.containsKey(Constants.Nonheap)){
            if (info.getString(Constants.Nonheap).equals("0")){
                nonHeapInfo.put(Constants.Nonheap, "unbounded");
            }else{
                nonHeapInfo.put(Constants.Nonheap, info.getString(Constants.Nonheap));
            }

        }
        if(info.containsKey(Constants.Nonheap_Committed)){
            nonheap_commit = Float.parseFloat(info.getString(Constants.Nonheap_Committed))/1024;
            nonHeapInfo.put(Constants.NonheapCommitted, nonheap_commit.intValue());
        }
        if(info.containsKey(Constants.Nonheap_Init )){
            Float nonheap_init = Float.parseFloat(info.getString(Constants.Nonheap_Init))/1024;
            nonHeapInfo.put(Constants.NonheapInit , nonheap_init.intValue());
        }
        if(info.containsKey(Constants.Nonheap_Used)){
            nonheap_used = Float.parseFloat(info.getString(Constants.Nonheap_Used))/1024;
            nonHeapInfo.put(Constants.NonheapUsed, nonheap_used.intValue());
        }
        nonHeapInfo.put(Constants.Per,(nonheap_used/nonheap_commit ) * 100);
        metricsInfo.setNonHeapInfo(nonHeapInfo);

        /**
         * thread info
         */
        Map<String,Object> threadInfo = new HashMap();
        if(info.containsKey(Constants.Threads)){
            threadInfo.put(Constants.Threads, info.getString(Constants.Threads));
        }
        if(info.containsKey(Constants.Threads_Peak)){
            threadInfo.put(Constants.ThreadsPeak, info.getString(Constants.Threads_Peak));
        }
        if(info.containsKey(Constants.Threads_Daemon)){
            threadInfo.put(Constants.ThreadsDaemon, info.getString(Constants.Threads_Daemon));
        }
        if(info.containsKey(Constants.Threads_TotalStarted)){
            threadInfo.put(Constants.ThreadsTotalStarted, info.getString(Constants.Threads_TotalStarted));
        }
        metricsInfo.setThreadInfo(threadInfo);

        /**
         * class load info
         */
        Map<String,Object> classesInfo = new HashMap();
        if(info.containsKey(Constants.Classes)){
            classesInfo.put(Constants.Classes, info.getString(Constants.Classes));
        }
        if(info.containsKey(Constants.Classes_Loaded)){
            classesInfo.put(Constants.ClassesLoaded, info.getString(Constants.Classes_Loaded));
        }
        if(info.containsKey(Constants.Classes_Unloaded)){
            classesInfo.put(Constants.ClassesUnloaded, info.getString(Constants.Classes_Unloaded));
        }
        metricsInfo.setClassesInfo(classesInfo);

        /**
         * class load info
         */
        Map<String,Object> gcInfo = new HashMap();
        if(info.containsKey(Constants.Gc_Ps_Scavenge_Count)){
            gcInfo.put(Constants.GcPsScavengeCount, info.getString(Constants.Gc_Ps_Scavenge_Count));
        }
        if(info.containsKey(Constants.Gc_Ps_Scavenge_Time)){
            gcInfo.put(Constants.GcPsScavengeTime, info.getString(Constants.Gc_Ps_Scavenge_Time));
        }
        if(info.containsKey(Constants.Gc_Ps_Marksweep_Count)){
            gcInfo.put(Constants.GcPsMarksweepCount, info.getString(Constants.Gc_Ps_Marksweep_Count));
        }
        if(info.containsKey(Constants.Gc_Ps_Marksweep_Time)){
            gcInfo.put(Constants.GcPsMarksweepTime, info.getString(Constants.Gc_Ps_Marksweep_Time));
        }
        metricsInfo.setGcInfo(gcInfo);

        /**
         * httpsession info
         */
        Map<String,Object> httpsessionsInfo = new HashMap();;
        if(info.containsKey(Constants.Httpsessions_Max)){
            httpsessionsInfo.put(Constants.HttpsessionsMax, info.getString(Constants.Httpsessions_Max));
        }
        if(info.containsKey(Constants.Httpsessions_Active)){
            httpsessionsInfo.put(Constants.HttpsessionsActive, info.getString(Constants.Httpsessions_Active));
        }
        metricsInfo.setHttpsessionsInfo(httpsessionsInfo);

        Map<String,Object> gaugeInfo = new HashMap();
        Map<String,Object> counterInfo = new HashMap();
        info.keySet().forEach(key ->{
            if(key.contains(Constants.Gauge_Response)){
                gaugeInfo.put(key.replace(".","_"),info.getString(key));
            }else if(key.contains(Constants.Gauge_Servo_Response)){
                gaugeInfo.put(key.replace(".","_"),info.getString(key));
            }else if(key.contains(Constants.Counter_Status)){
                counterInfo.put(key.replace(".","_"),info.getString(key));
            }else if(key.contains(Constants.Counter_Servo)){
                counterInfo.put(key.replace(".","_"),info.getString(key));
            }
        });
        metricsInfo.setGaugeInfo(gaugeInfo);
        metricsInfo.setCounterInfo(counterInfo);
        return metricsInfo;

    }

}
