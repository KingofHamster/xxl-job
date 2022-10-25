package com.xxl.job.admin.core.util;

public interface TimeConstants {
    int minuteSeconds = 60;
    int hourSeconds = 60 * minuteSeconds;
    int daySeconds = 24 * hourSeconds;
    
    static int toSeconds(int hour, int minute) {
        return hour * hourSeconds + minute * minuteSeconds;
    }

    static int toSeconds(int hour, int minute, int second) {
        return hour * hourSeconds + minute * minuteSeconds + second;
    }
}
