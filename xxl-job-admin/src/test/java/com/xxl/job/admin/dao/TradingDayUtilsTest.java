package com.xxl.job.admin.dao;


import com.xxl.job.admin.core.fixrate.FixRateConf;
import com.xxl.job.admin.core.util.TradingDayUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TradingDayUtilsTest {
    @Test
    public void isTradingDayTest() {
        Date date = new Date();
        Date lastTradingDay = date;
        for(int i = 0; i < 100; i++) {
            lastTradingDay = TradingDayUtils.getNextTradingDayZeroByTreeMap(lastTradingDay);
            System.out.println(lastTradingDay);
        }
    }

    @Test
    public void nextTriggerTimeTest() throws Exception {
        int minuteSeconds = 60;
        int hourSeconds = minuteSeconds * 60;
        int beginTime = 14 * hourSeconds + 0 * minuteSeconds;
        int endTime = 15 * hourSeconds + 0 * minuteSeconds;
        int interval = 0 * hourSeconds + 10 * minuteSeconds;
        String fixRateConfString = beginTime + " " + endTime + " " + interval + " " + FixRateConf.TRADING_DAY;
        System.out.println("fixRateConfString = " + fixRateConfString);
//        FixRateConf fixRateConf = new FixRateConf(beginTime, endTime, minuteSeconds);
        FixRateConf fixRateConf = new FixRateConf(fixRateConfString);
        Date lastTime = new Date();


        for (int i = 0; i < 100; i++) {
            lastTime = fixRateConf.getNextValidTimeAfter(lastTime);
            System.out.println(lastTime);
        }
    }
}
