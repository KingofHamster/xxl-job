package com.xxl.job.admin.core.fixrate;

import com.xxl.job.admin.core.cron.CronExpression;
import com.xxl.job.admin.core.fixrate.FixRateConf;

import java.util.ArrayList;
import java.util.Date;

public class MultiFixRateConf {
    ArrayList<FixRateConf> fixRateConfs;

    public MultiFixRateConf(String[] confStrs) {
        fixRateConfs = new ArrayList<>();
        for (String confStr : confStrs) {
            fixRateConfs.add(new FixRateConf(confStr.trim()));
        }
    }
    
    
    public MultiFixRateConf(String multiFixRateConfStr, String separator) {
        this(multiFixRateConfStr.split(separator.trim()));
    }

    public Date getEarliestNextValidTimeAfter(Date fromDate) {
        Date earliestNextValidTime = null;
        // traverse different CronExpressions to calculate the earliest valid time from fromDate
        for (FixRateConf fixRateConf : fixRateConfs) {
            Date nextValidTime = fixRateConf.getNextValidTimeAfterByDayHelper(fromDate);
            if (earliestNextValidTime == null || nextValidTime.before(earliestNextValidTime)) {
                earliestNextValidTime = nextValidTime;
            }
        }
        return earliestNextValidTime;
    }

    public ArrayList<FixRateConf> getFixRateConfs() {
        return fixRateConfs;
    }

    public void setFixRateConfs(ArrayList<FixRateConf> fixRateConfs) {
        this.fixRateConfs = fixRateConfs;
    }
}
