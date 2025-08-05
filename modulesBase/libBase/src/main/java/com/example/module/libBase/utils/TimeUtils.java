package com.example.module.libBase.utils;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class TimeUtils {
    public static String getFormattedTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("a h:mm", Locale.CHINA);
        return LocalTime.now().format(formatter);
    }

    public static String getTimeNormal() {
        LocalTime now = LocalTime.now();
        int hour = now.getHour();
        if (hour >= 5 && hour < 12) {
            return "早上";
        } else if (hour >= 12 && hour < 14) {
            return "中午";
        } else if (hour >= 14 && hour < 18) {
            return "下午";
        } else if (hour >= 18 && hour < 22) {
            return "晚上";
        } else {
            return "深夜";
        }
    }

    // 返回年月日的时间格式
    public static String getTimeStandardOnlyYMD(long time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return format.format(time);
    }
}
