package com.xxl.job.fixrate;

import com.xxl.job.admin.core.fixrate.dayhelper.DayHelper;
import com.xxl.job.admin.core.util.TimeConstants;

import static com.xxl.job.admin.dbmigration.TaskMigration.parseATaskInfoTime;

public class Formatter {
    public static void main(String[] args) {
        int beginTimeOffset = parseATaskInfoTime(92900);
        int endTimeOffset = parseATaskInfoTime(93000);
        int interval = 3;
        int actionType = DayHelper.DayHelperTradingDay;
        String actionDays = "";
        String singleFixConfStr = String.format("%d %d %d %s %s", beginTimeOffset, endTimeOffset, interval, actionType, actionDays);
        System.out.println(singleFixConfStr);
    }
}
