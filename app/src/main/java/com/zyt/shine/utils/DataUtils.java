package com.zyt.shine.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DataUtils {
    // 获取当前时间 年月日时分秒毫秒
    public static String getDate() {
        Date date = new Date();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = format.format(date);
        return time;
    }

    public static String getTime() {
        Date date = new Date();
        DateFormat format = new SimpleDateFormat("HH:mm");
        String time = format.format(date);
        return time;
    }
}
