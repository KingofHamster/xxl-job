package com.xxl.job.admin.core.fixrate.dayhelper;

import com.xxl.job.admin.core.cron.CronExpression;

import java.text.ParseException;
import java.util.Date;

public class DayHelperEveryWeek implements DayHelper{
    CronExpression cronExpression;
    
    public DayHelperEveryWeek(String actionDays) {
        try {
            cronExpression = new CronExpression(String.format("0 0 0 ? * %s", actionDays));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public boolean isValidDay(Date date) {
        return cronExpression.isSatisfiedBy(getDayZero(date));
    }

    @Override
    public Date nextValidDayZero(Date fromDate) {
        return cronExpression.getNextValidTimeAfter(fromDate);
    }
}
