package com.xxl.job.admin.core.fixrate.dayhelper;

import java.util.Date;

public class DayHelperEveryDay implements DayHelper{
    @Override
    public boolean isValidDay(Date date) {
        return true;
    }

    @Override
    public Date nextValidDayZero(Date fromDate) {
        Date fromDateZero = getDayZero(fromDate);
        System.out.println(fromDateZero);
        return new Date(fromDateZero.getTime() + 24 * 60 * 60 * 1000L);
    }
}
