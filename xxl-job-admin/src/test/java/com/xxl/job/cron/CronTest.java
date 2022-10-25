package com.xxl.job.cron;

import com.cronutils.builder.CronBuilder;
import com.cronutils.model.Cron;
import com.cronutils.model.definition.CronDefinitionBuilder;
import com.xxl.job.admin.core.cron.CronExpression;
import com.xxl.job.admin.core.cron.MultiCronExpression;
import com.xxl.job.admin.core.util.CronUtils;
import org.codehaus.groovy.transform.EqualsAndHashCodeASTTransformation;
import org.junit.jupiter.api.Test;

import java.sql.Time;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static com.cronutils.model.CronType.QUARTZ;
import static com.cronutils.model.field.expression.FieldExpressionFactory.*;


public class CronTest {
    
    @Test
    public void cronTest() throws ParseException {
        CronExpression expression = new CronExpression("0 0/1 9.5-17.5 * * ?");
        Date now = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        calendar.set(Calendar.HOUR, 18);
        LocalDateTime beginTime = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
        Set<Calendar> holidays = new HashSet<>();
        Calendar testHoliday = Calendar.getInstance();
        
        holidays.add(testHoliday);
        while(true) {
            Date nextDate = expression.getNextValidTimeAfter(calendar.getTime());
            calendar.setTime(nextDate);
            Calendar nextCalendar = Calendar.getInstance();
            nextCalendar.set(nextDate.getYear(), nextDate.getMonth(), nextDate.getDate());
            if (!holidays.contains(nextCalendar)) {
                System.out.println(nextCalendar.getTime());
                break;
            }
            calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + 1);
        }
        System.out.println(calendar.getTime());
    }
    
    @Test
    public void halfHourTest() throws ParseException {
        String cronStrs = "0/10 * 9-14 * * ?|0/10 0-29 15 * * ?";
        Date nextDate = new Date();
        nextDate.setYear(nextDate.getYear() + 3000);
        for (String conStr : cronStrs.split("\\|")) {
            CronExpression cronExpression = new CronExpression(conStr);
            Date date = cronExpression.getNextValidTimeAfter(new Date(new Date().getYear(), Calendar.OCTOBER, 24, 15, 29, 30));
            if (date.before(nextDate)) {
                nextDate = date;
            }
        }
        System.out.println(nextDate.toString());
    }
    
    @Test
    public void test() throws ParseException {
        CronExpression cronExpression = new CronExpression("0/10 * 9-15 * * ?");
        Date now = new Date();
        Date date = new Date(2022 - 1900, Calendar.OCTOBER, 24, 16, 36, 0);
        System.out.println(cronExpression.getNextValidTimeAfter(date));
    }
    
    @Test
    public void multiCronExpressionTest() throws ParseException {
        MultiCronExpression multiCronExpression = new MultiCronExpression("0/5 40-53 * * * ?|0/5 55 * * * ?", "\\|");
        Date nextValidTime = multiCronExpression.getNextValidTimeAfter(new Date());
        System.out.println(nextValidTime);
        for(int i = 0; i < 100; i++) {
            nextValidTime = multiCronExpression.getNextValidTimeAfter(nextValidTime);
            System.out.println(nextValidTime);
        }
    }
    
    @Test
    public void CronUtilsTest() {
        String res = CronUtils.convertFromFixedRateTaskToCRON(1000, 10000, 100000);
        System.out.println(new Date(System.currentTimeMillis() + TimeUnit.SECONDS.toDays(100)));
    }
    
    @Test
    public void parseIntTest() {
        System.out.println(Integer.parseInt("03"));
    }
}
