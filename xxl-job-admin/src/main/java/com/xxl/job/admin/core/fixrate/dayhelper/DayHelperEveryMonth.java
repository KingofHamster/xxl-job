package com.xxl.job.admin.core.fixrate.dayhelper;

import com.xxl.job.admin.core.cron.CronExpression;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

public class DayHelperEveryMonth implements DayHelper{
    private CronExpression cronExpression;
    
    public DayHelperEveryMonth(String actionDays) {
        try {
            cronExpression = new CronExpression(String.format("0 0 0 %s * ?", actionDays));
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
