package com.xxl.job.admin.core.fixrate;

import com.xxl.job.admin.core.fixrate.dayhelper.DayHelper;
import com.xxl.job.admin.core.util.TimeConstants;
import com.xxl.job.core.biz.model.ReturnT;

import java.util.ArrayList;
import java.util.Date;

public class MultiFixRateConf {
    ArrayList<FixRateConf> fixRateConfs;
    public static ReturnT<String> checkFormat(String multiConfStr){
        String[] confStrs = multiConfStr.split("\\|");
        ArrayList<String> msgs = new ArrayList<>();
        for (int i = 0; i < confStrs.length; i++) {
            String confStr = confStrs[i];
            String[] segs = confStr.split(" ");
            
            if (segs.length < 4) {
                msgs.add(String.format("第%d个配置 %s", i + 1, "参数过少"));
            }
            
            int beginTimeOffset = TimeConstants.hhmmssToSeconds(segs[0]);
            if (beginTimeOffset < 0 || beginTimeOffset > 24 * 60 * 60) {
                msgs.add(String.format("第%d个配置 %s", i + 1, "非法开始时间;"));
            }
            
            int endTimeOffset = TimeConstants.hhmmssToSeconds(segs[1]);
            if (endTimeOffset < 0 || endTimeOffset > 24 * 60 * 60) {
                msgs.add(String.format("第%d个配置 %s", i + 1, "非法结束时间;"));
            }
            
            int interval = Integer.parseInt(segs[2]);
            if (interval < 0) {
                msgs.add(String.format("第%d个配置 %s", i + 1, "非法时间间隔;"));
            }
            
            int actionType = Integer.parseInt(segs[3]); // determine using which DayHelper
            if (actionType < 0 || actionType > 4) {
                msgs.add(String.format("第%d个配置 %s", i + 1, "非法调度类型;"));
            }

            String actionDays = segs.length >= 5 ? segs[4] : "";
            
            if (actionType == 3) {
                try {
                    int weekday = Integer.parseInt(actionDays);
                    if (weekday < 0 || weekday > 7) {
                        throw new RuntimeException("");
                    }
                } 
                catch (Exception e) {
                    msgs.add(String.format("第%d个配置 %s", i + 1, "非法星期配置;"));
                }
            }

            if (actionType == 4) {
                try {
                    int monthDay = Integer.parseInt(actionDays);
                    if (monthDay < 0 || monthDay > 7) {
                        throw new RuntimeException("");
                    }
                }
                catch (Exception e) {
                    msgs.add(String.format("第%d个配置 %s", i + 1, "非法月配置;"));
                }
            }
        }
        
        StringBuilder returnMsgSB = new StringBuilder();
        for (String msg : msgs) {
            returnMsgSB.append(msg);
        }
        
        if (returnMsgSB.length() == 0) {
            return new ReturnT<>(ReturnT.SUCCESS_CODE, returnMsgSB.toString());
        } else {
            return new ReturnT<>(ReturnT.FAIL_CODE, returnMsgSB.toString());
        }
    }
    

    public MultiFixRateConf(String[] confStrs) {
        fixRateConfs = new ArrayList<>();
        for (String confStr : confStrs) {
//            fixRateConfs.add(new FixRateConf(confStr.trim()));
            fixRateConfs.add(FixRateConf.parseConfString(confStr));
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
