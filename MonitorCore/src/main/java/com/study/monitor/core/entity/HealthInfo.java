package com.study.monitor.core.entity;

import java.util.Map;

/**
 * Created by lf52 on 2018/7/10.
 */
public class HealthInfo {

    /**
     * service 描述
     */
    String description;

    /**
     * service 状态
     */
    Status serviceStatus;

    /**
     * service 注册发现的信息
     */
    Map<String,Object> discoveryComposite;

    /**
     * service disk信息
     */
    Map<String,Object> diskSpace;

    /**
     * refreshScope 状态
     */
    Status refreshScope;

    /**
     * hystrix 状态
     */
    Status hystrix;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Status getServiceStatus() {
        return serviceStatus;
    }

    public void setServiceStatus(Status serviceStatus) {
        this.serviceStatus = serviceStatus;
    }

    public Map<String, Object> getDiscoveryComposite() {
        return discoveryComposite;
    }

    public void setDiscoveryComposite(Map<String, Object> discoveryComposite) {
        this.discoveryComposite = discoveryComposite;
    }

    public Map<String, Object> getDiskSpace() {
        return diskSpace;
    }

    public void setDiskSpace(Map<String, Object> diskSpace) {
        this.diskSpace = diskSpace;
    }

    public Status getRefreshScope() {
        return refreshScope;
    }

    public void setRefreshScope(Status refreshScope) {
        this.refreshScope = refreshScope;
    }

    public Status getHystrix() {
        return hystrix;
    }

    public void setHystrix(Status hystrix) {
        this.hystrix = hystrix;
    }

    @Override
    public String toString() {
        return "HealthInfo{" +
                "description='" + description + '\'' +
                ", serviceStatus=" + serviceStatus +
                ", discoveryComposite=" + discoveryComposite +
                ", diskSpace=" + diskSpace +
                ", refreshScope=" + refreshScope +
                ", hystrix=" + hystrix +
                '}';
    }
}
