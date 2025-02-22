package com.example.module.libBase;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class TimeUtils {
    public static String getFormattedTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("a h:mm", Locale.CHINA);
        return LocalTime.now().format(formatter);
    }
}
