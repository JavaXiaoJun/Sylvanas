package com.study.monitor.core.utils;

import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

/**
 * Created by lf52 on 2018/7/7.
 */
public class CommonUtils {

    private static DecimalFormat df = new DecimalFormat("#.00");

    private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 获取当前服务的host + port
     */
    public static String getHost() throws UnknownHostException {
        String host = InetAddress.getLocalHost().getHostAddress();
        return host;
    }

    /**
     * time to day
     *
     * @param time
     * @return
     */
    public static String formatTime(long time) {

        Integer ss = 1000;
        Integer mi = ss * 60;
        Integer hh = mi * 60;
        Integer dd = hh * 24;

        Long day = time / dd;
        Long hour = (time - day * dd) / hh;
        Long minute = (time - day * dd - hour * hh) / mi;
        Long second = (time - day * dd - hour * hh - minute * mi) / ss;

        StringBuilder sb = new StringBuilder();

        String dayStr = (day <= 1) ? day + " day " : day + " days ";
        String hourStr = ((hour < 10) ? ("0" + hour) : hour.toString()) + ":";
        String minuteStr = ((minute < 10) ? ("0" + minute) : minute.toString()) + ":";
        String secondStr = (second < 10) ? ("0" + second) : second.toString();
        sb.append(dayStr);
        sb.append(hourStr);
        sb.append(minuteStr);
        sb.append(secondStr);

        return sb.toString();

    }

    public static String formatTimestamp(Long timestamp) {
        return format.format(timestamp);
    }

    public static String formatNumber(Float num) {
        return df.format(num);
    }

    public static void main(String[] args) {
        System.out.println(formatTimestamp(BigDecimal.valueOf(Double.valueOf(1534406449.934) * 1000).longValue()));

        System.out.println(formatTime(1535));

        System.out.println(Float.parseFloat("0.58"));
        System.out.println(Double.parseDouble("0.581"));
    }
}
