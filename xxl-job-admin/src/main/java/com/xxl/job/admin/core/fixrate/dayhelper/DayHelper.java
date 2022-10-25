package com.xxl.job.admin.core.fixrate.dayhelper;

import java.util.Calendar;
import java.util.Date;

public interface DayHelper {
    /**
     * DayHelper Code:
     * 1 - DayHelperTradingDay
     * 2 - DayHelperEveryDay 
     * 3 - DayHelperEveryMonth
     * 4 - DayHelperEveryWeek 
     * @param date
     * @return
     */
    
    public static final int DayHelperTradingDay = 1;
    public static final int DayHelperEveryDay = 2;
    public static final int DayHelperEveryWeek = 3;
    public static final int DayHelperEveryMonth = 4;
    
    boolean isValidDay(Date date);
    
    Date nextValidDayZero(Date fromDate);
    
    default DayHelper getHelperByCode(int code){
        return new DayHelperEveryDay();
    }
    
    default Date getDayZero(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    static DayHelper getInstanceByCode(int code, String actionDays) {
        if (code == DayHelperTradingDay) {
            return new DayHelperTradingDay();
        }
        if (code == DayHelperEveryDay) {
            return new DayHelperEveryDay();
        }
        if (code == DayHelperEveryWeek) {
            return new DayHelperEveryWeek(actionDays);
        }
        if (code == DayHelperEveryMonth) {
            return new DayHelperEveryMonth(actionDays);
        }
        return null;
    }
}
