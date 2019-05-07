package com.study.discovery.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by lf52 on 2018/6/28.
 */
public class DateUtils {
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static String formatDate(Date date){
        return  sdf.format(date);
    }
}
