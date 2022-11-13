package com.xxl.job.fixrate;

import com.xxl.job.admin.XxlJobAdminApplication;
import com.xxl.job.admin.core.fixrate.FixRateConf;
import com.xxl.job.admin.core.fixrate.dayhelper.DayHelper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest(classes = XxlJobAdminApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FixRateConfTest {
    
    @Test
    public void nextTriggerTimeTest() throws Exception {
        int minuteSeconds = 60;
        int hourSeconds = minuteSeconds * 60;
        int beginTimeOffset = 9 * hourSeconds + 30 * minuteSeconds;
        int endTimeOffset = 12 * hourSeconds + 0 * minuteSeconds;
        int interval = 1 * hourSeconds + 0 * minuteSeconds;
        String fixRateConfString = beginTimeOffset + " " + endTimeOffset + " " + interval + " " + DayHelper.DayHelperTradingDay + " " + "1";
        System.out.println("fixRateConfString = " + fixRateConfString);
//        FixRateConf fixRateConf = new FixRateConf(beginTimeOffset, endTimeOffset, interval, DayHelper.DayHelperTradingDay, "7");
        FixRateConf fixRateConf = new FixRateConf(fixRateConfString);
        Date lastTime = new Date();
        System.out.println(fixRateConf);
        
        for (int i = 0; i < 500; i++) {
            lastTime = fixRateConf.getNextValidTimeAfterByDayHelper(lastTime);
            System.out.println(lastTime);
        }
    }

    @Test
    public void nextTriggerTimeTestUsingParser() throws Exception {
        int minuteSeconds = 60;
        int hourSeconds = minuteSeconds * 60;
        int beginTimeOffset = 9 * hourSeconds + 30 * minuteSeconds;
        int endTimeOffset = 12 * hourSeconds + 0 * minuteSeconds;
        int interval = 0 * hourSeconds + 30 * minuteSeconds;
        String fixRateConfString = beginTimeOffset + " " + endTimeOffset + " " + interval + " " + DayHelper.DayHelperEveryMonth + " " + "1";
        System.out.println("fixRateConfString = " + fixRateConfString);
        fixRateConfString = "09:30:00 10:00:00 3600 1";
        FixRateConf fixRateConf = FixRateConf.parseConfString(fixRateConfString);
        Date lastTime = new Date();
        System.out.println(fixRateConf);

        for (int i = 0; i < 500; i++) {
            lastTime = fixRateConf.getNextValidTimeAfterByDayHelper(lastTime);
            System.out.println(lastTime);
        }
    }
}
