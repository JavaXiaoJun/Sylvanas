package com.study.monitor.core.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by lf52 on 2018/7/7.
 */
public class CommonUtils {

    /**
     * 获取当前服务的host + port
     */
    public static String getHost() throws UnknownHostException {
        String host = InetAddress.getLocalHost().getHostAddress();
        return host;
    }

    /**
     *  ms to day
     * @param ms
     * @return
     */
    public static String formatTime(Long ms) {
        Integer ss = 1000;
        Integer mi = ss * 60;
        Integer hh = mi * 60;
        Integer dd = hh * 24;

        Long day = ms / dd;
        Long hour = (ms - day * dd) / hh;
        Long minute = (ms - day * dd - hour * hh) / mi;
        Long second = (ms - day * dd - hour * hh - minute * mi) / ss;

        StringBuilder sb = new StringBuilder();
        sb.append(day+":");
        sb.append(hour+":");
        sb.append(minute+":");
        sb.append(second);

        return sb.toString();


    }
}
