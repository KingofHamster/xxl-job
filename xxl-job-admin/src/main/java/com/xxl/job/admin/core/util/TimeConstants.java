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
    
    static int hhmmssToSeconds(String hhmmssStr) {
        StringBuilder timeStr = new StringBuilder(String.valueOf(hhmmssStr));
        int seconds = -1;
        try {
            seconds = TimeConstants.toSeconds(Integer.parseInt(timeStr.substring(0, 2)),
                    Integer.parseInt(timeStr.substring(3, 5)),
                    Integer.parseInt(timeStr.substring(6, 8)));
        } catch (NumberFormatException e) {
            seconds = -1;
        }

        return seconds;
    }
}
