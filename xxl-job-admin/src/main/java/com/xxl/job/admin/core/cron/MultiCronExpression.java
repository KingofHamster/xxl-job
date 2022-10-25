package com.xxl.job.admin.core.cron;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.concurrent.TimeUnit;

public class MultiCronExpression {
    ArrayList<CronExpression> cronExpressions;
    static HashSet<String> nonTradingDays;
    
    public ArrayList<CronExpression> getCronExpressions() {
        return cronExpressions;
    }

    public void setCronExpressions(ArrayList<CronExpression> cronExpressions) {
        this.cronExpressions = cronExpressions;
    }
    
    public MultiCronExpression(String[] cronStrs) throws ParseException {
        cronExpressions = new ArrayList<>();
        for (String cronStr : cronStrs) {
            if (cronStr == null) {
                throw new NullPointerException("Illegal Cron String in Creating MultiCronExpression");
            }
            cronExpressions.add(new CronExpression(cronStr));
        }
    }

    public MultiCronExpression(String multiCronStr, String seperator) throws ParseException {
        this(multiCronStr.split(seperator));
    }
    
    public Date getNextValidTimeAfter(Date date) throws ParseException {
        Date earliestNextValidTime = null;
        // traverse different CronExpressions to calculate the earliest valid time from now
        for (CronExpression cronExpression : cronExpressions) {
            Date nextValidTime = cronExpression.getNextValidTimeAfter(date);
            if (earliestNextValidTime == null || nextValidTime.before(earliestNextValidTime)) {
                earliestNextValidTime = nextValidTime;
            }
        }
        return earliestNextValidTime;
    }
    
    public Date getNextValidTradingTimeAfter(Date date) throws ParseException {
        Date earliestNextValidTradingTime = null;
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        earliestNextValidTradingTime = getNextValidTimeAfter(date);
        
        while (earliestNextValidTradingTime != null && nonTradingDays.contains(dateFormat.format(earliestNextValidTradingTime))) {
            earliestNextValidTradingTime.setTime(earliestNextValidTradingTime.getTime() + 24 * 60 * 60 * 1000);
        }
        return earliestNextValidTradingTime;
    }
}
