package com.xxl.job.fixrate;

import com.xxl.job.admin.XxlJobAdminApplication;
import com.xxl.job.admin.core.fixrate.dayhelper.DayHelper;
import com.xxl.job.admin.core.fixrate.dayhelper.DayHelperEveryDay;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Calendar;
import java.util.Date;

@SpringBootTest(classes = XxlJobAdminApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DayHelperTest {
    @Test
    public void DayHelperEveryDayTest() {
        DayHelper dayHelper = DayHelper.getInstanceByCode(DayHelper.DayHelperEveryDay, "");
        System.out.println(dayHelper.isValidDay(new Date()));
        Calendar calendar = Calendar.getInstance();
        calendar.set(2022, Calendar.OCTOBER, 29, 3, 30);
        System.out.println(dayHelper.nextValidDayZero(calendar.getTime()));
    }
    
    @Test
    public void DayHelperTradingDayTest() {
        DayHelper dayHelper = DayHelper.getInstanceByCode(DayHelper.DayHelperTradingDay, "");
        System.out.println(dayHelper.isValidDay(new Date()));
        System.out.println(dayHelper.nextValidDayZero(new Date()));
    }

    @Test
    public void DayHelperEveryWeekTest() {
        DayHelper dayHelper = DayHelper.getInstanceByCode(DayHelper.DayHelperEveryWeek, "7");
        System.out.println(dayHelper.isValidDay(new Date()));
        System.out.println(dayHelper.nextValidDayZero(new Date()));
    }

    @Test
    public void DayHelperEveryMonthTest() {
        DayHelper dayHelper = DayHelper.getInstanceByCode(DayHelper.DayHelperEveryMonth, "29");
        System.out.println(dayHelper.isValidDay(new Date()));
        System.out.println(dayHelper.nextValidDayZero(new Date()));
    }
}
