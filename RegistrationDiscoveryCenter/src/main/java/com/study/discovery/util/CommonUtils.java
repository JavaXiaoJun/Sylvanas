package com.study.discovery.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * @author Jay.H.Zou
 * @date 2018/8/1
 */
public class CommonUtils {
    private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private CommonUtils(){}

    public static String getUUID() {
        return  UUID.randomUUID().toString();
    }

    public static String getNowFormatTime() {
        Date nowTime = new Date();
        return format.format(nowTime);
    }

    public static Timestamp getNowTimestamp() {
        return new Timestamp(System.currentTimeMillis());
    }

    public static String formatTimestamp(Timestamp timestamp) {
        return format.format(timestamp);
    }
}
