package com.study.monitor.core.entity;

import java.util.Map;

/**
 * Created by lf52 on 2018/7/10.
 */
public class MetricsInfo {
    /**
     * 系统信息
     */
    Map<String,Object> systemInfo;

    /**
     * 内存信息
     */
    Map<String,Object> memoryInfo;

    /**
     * 堆信息
     */
    Map<String,Object> heapInfo;

    /**
     * 非堆信息（方法区信息）
     */
    Map<String,Object> nonHeapInfo;


    /**
     * 线程信息
     */
    Map<String,Object> threadInfo;

    /**
     * 类加载信息
     */
    Map<String,Object> classesInfo;

    /**
     * gc信息
     */
    Map<String,Object> gcInfo;

    /**
     * httpsession信息
     * 该度量指标信息仅在引入了嵌入式Tomcat作为应用容器的时候才会提供
     */
    Map<String,Object> httpsessionsInfo;

    /**
     * HTTP请求的性能指标之一，它主要用来反映一个绝对数值
     */
    Map<String,Object> gaugeInfo;

    /**
     * HTTP请求的性能指标之一，它主要作为计数器来使用，记录了增加量和减少量
     */
    Map<String,Object> counterInfo;

    public Map<String, Object> getSystemInfo() {
        return systemInfo;
    }

    public void setSystemInfo(Map<String, Object> systemInfo) {
        this.systemInfo = systemInfo;
    }

    public Map<String, Object> getMemoryInfo() {
        return memoryInfo;
    }

    public void setMemoryInfo(Map<String, Object> memoryInfo) {
        this.memoryInfo = memoryInfo;
    }

    public Map<String, Object> getHeapInfo() {
        return heapInfo;
    }

    public void setHeapInfo(Map<String, Object> heapInfo) {
        this.heapInfo = heapInfo;
    }

    public Map<String, Object> getThreadInfo() {
        return threadInfo;
    }

    public void setThreadInfo(Map<String, Object> threadInfo) {
        this.threadInfo = threadInfo;
    }

    public Map<String, Object> getClassesInfo() {
        return classesInfo;
    }

    public void setClassesInfo(Map<String, Object> classesInfo) {
        this.classesInfo = classesInfo;
    }

    public Map<String, Object> getGcInfo() {
        return gcInfo;
    }

    public void setGcInfo(Map<String, Object> gcInfo) {
        this.gcInfo = gcInfo;
    }

    public Map<String, Object> getHttpsessionsInfo() {
        return httpsessionsInfo;
    }

    public void setHttpsessionsInfo(Map<String, Object> httpsessionsInfo) {
        this.httpsessionsInfo = httpsessionsInfo;
    }

    public Map<String, Object> getGaugeInfo() {
        return gaugeInfo;
    }

    public void setGaugeInfo(Map<String, Object> gaugeInfo) {
        this.gaugeInfo = gaugeInfo;
    }

    public Map<String, Object> getCounterInfo() {
        return counterInfo;
    }

    public void setCounterInfo(Map<String, Object> counterInfo) {
        this.counterInfo = counterInfo;
    }

    public Map<String, Object> getNonHeapInfo() {
        return nonHeapInfo;
    }

    public void setNonHeapInfo(Map<String, Object> nonHeapInfo) {
        this.nonHeapInfo = nonHeapInfo;
    }

    @Override
    public String toString() {
        return "MetricsInfo{" +
                "systemInfo=" + systemInfo +
                ", memoryInfo=" + memoryInfo +
                ", heapInfo=" + heapInfo +
                ", nonHeapInfo=" + nonHeapInfo +
                ", threadInfo=" + threadInfo +
                ", classesInfo=" + classesInfo +
                ", gcInfo=" + gcInfo +
                ", httpsessionsInfo=" + httpsessionsInfo +
                ", gaugeInfo=" + gaugeInfo +
                ", counterInfo=" + counterInfo +
                '}';
    }
}
