package com.example.communityfragment.utils;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TimeUtils {
    public static String getFormatTime(String createdTime) {
        String regex = "(\\d{4})-(\\d{2})-(\\d{2}) (\\d{2}):(\\d{2}):(\\d{2})";
//        2025-03-20 17:16:46
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(createdTime);
        if (matcher.find()) {
            String year = matcher.group(1);
            String month = matcher.group(2);
            String day = matcher.group(3);
            String hour = matcher.group(4);
            String minute = matcher.group(5);

            return "发布于 " + year + "." + month + "." + day + " " + hour + ":" + minute;
        } else {
            return "未知";
        }
    }

    public static String getRelativeTime(String timeStr) {
        String regex = "(\\d{4})-(\\d{2})-(\\d{2}) (\\d{2}):(\\d{2}):(\\d{2})";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(timeStr);
        if (!matcher.matches()) {
            return timeStr;
        }
        int year = Integer.parseInt(matcher.group(1));
        int month = Integer.parseInt(matcher.group(2));
        int day = Integer.parseInt(matcher.group(3));
        int hour = Integer.parseInt(matcher.group(4));
        int minute = Integer.parseInt(matcher.group(5));
        int second = Integer.parseInt(matcher.group(6));

        LocalDateTime parsedTime = LocalDateTime.of(year, month, day, hour, minute,second);
        LocalDateTime now = LocalDateTime.now();

        Duration duration = Duration.between(parsedTime, now);
        long diffSeconds = duration.getSeconds();

        if (diffSeconds < 0) {
            return timeStr;
        }

        long diffMinutes = diffSeconds / 60;
        long diffHours = diffSeconds / 3600;
        long diffDays = diffSeconds / (3600 * 24);

        if (diffSeconds < 60) {
            return "刚刚";
        } else if (diffSeconds < 3600) {
            return diffMinutes + "分钟前";
        } else if (diffSeconds < 3600 * 24) {
            return diffHours + "小时前";
        } else if (diffDays <= 1) {
            return diffDays + "天前";
        } else if (diffDays < 365) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M.d H:m:ss");
            return parsedTime.format(formatter);
        } else {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.M.d H:m:ss");
            return parsedTime.format(formatter);
        }
    }

    public static String getTimeNormal() {
        LocalTime now = LocalTime.now();
        int hour = now.getHour();
        if (hour >= 5 && hour < 12) {
            return "早安";
        } else if (hour >= 12 && hour < 14) {
            return "午安";
        } else if (hour >= 14 && hour < 18) {
            return "下午好";
        } else if (hour >= 18 && hour < 22) {
            return "晚上好";
        } else {
            return "晚安";
        }
    }

    public static String getTimeStandardOnlyYMD(long time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return format.format(time);
    }

}
