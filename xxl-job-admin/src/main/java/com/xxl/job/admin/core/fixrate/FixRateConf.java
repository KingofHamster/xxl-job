package com.xxl.job.admin.core.fixrate;


import com.xxl.job.admin.core.fixrate.dayhelper.DayHelper;
import com.xxl.job.admin.core.fixrate.dayhelper.DayHelperTradingDay;
import com.xxl.job.admin.core.util.TimeConstants;
import com.xxl.job.admin.core.util.TradingDayUtils;

import java.util.Calendar;
import java.util.Date;

public class FixRateConf {
    

    /**
     * A static method for parsing a 'hh:mm:dd' format string to seconds as int;
     * @param confStr
     * @return
     */
    public static FixRateConf parseConfString(String confStr) {
        String[] segs = confStr.split(" ");
        FixRateConf fixRateConf = new FixRateConf();
        fixRateConf.beginTimeOffset = TimeConstants.hhmmssToSeconds(segs[0]);
        fixRateConf.endTimeOffset = TimeConstants.hhmmssToSeconds(segs[1]);
        fixRateConf.interval = Integer.parseInt(segs[2]);
        fixRateConf.actionType = Integer.parseInt(segs[3]); // determine using which DayHelper
        String actionDays = segs.length >= 5 ? segs[4] : "";
        fixRateConf.dayHelper = DayHelper.getInstanceByCode(fixRateConf.actionType, actionDays);
        return fixRateConf;
    }

    /**
     * ActivationType for triggering;
     * 1 TRADING_DAY
     * 2 EVERY_DAY
     * 3 EVERY_WEEK
     * 4 EVERY_MONTH
     */

    

    /**
     * Definitions of time constants;
     */
    public final static int TRADING_DAY = 1; //交易日
    public final static int EVERY_DAY = 2; // 每天
    public final static int secondsBeginOfDay = 0;
    public final static int secondsEndOfDay = 24 * 60 * 60;

    /**
     * Attributes
     */
    int beginTimeOffset; // 起始时间 seconds (0:00 - 24:00)
    int endTimeOffset; // 结束时间 seconds (0:00 - 24:00)

    long interval; // 间隔多久执行一次, second

    int actionType; // 默认 1 - TRADING_DAY 每个交易日

    long beginDateTime = 0; // 开始日期

    long endDateTime = System.currentTimeMillis() * 2; // 结束日期
    
    DayHelper dayHelper;

    public FixRateConf(int beginTimeOffset, int endTimeOffset, long interval, int actionType) {
        this.beginTimeOffset = beginTimeOffset;
        this.endTimeOffset = endTimeOffset;
        this.interval = interval;
        this.actionType = actionType;
    }

    public FixRateConf(int beginTimeOffset, int endTimeOffset, long interval, int actionType, String actionDays) {
        this.beginTimeOffset = beginTimeOffset;
        this.endTimeOffset = endTimeOffset;
        this.interval = interval;
        this.actionType = actionType;
        this.dayHelper = DayHelper.getInstanceByCode(actionType, actionDays);
    }
    
    public FixRateConf(){
        
    }

    /**
     * confStr format: 'beginTimeOffset endTimeOffset interval actionType [actionDays]'
     * beginTimeOffset endTimeOffset interval: seconds
     * @param confStr
     * @throws Exception
     */
    public FixRateConf(String confStr) {
        String[] segs = confStr.split(" ");
        this.beginTimeOffset = Integer.parseInt(segs[0]);
        this.endTimeOffset = Integer.parseInt(segs[1]);
        this.interval = Integer.parseInt(segs[2]);
        this.actionType = Integer.parseInt(segs[3]); // determine using which DayHelper
        String actionDays = segs.length >= 5 ? segs[4] : "";
        this.dayHelper = DayHelper.getInstanceByCode(actionType, actionDays);
    }

    public FixRateConf(int beginTimeOffset, int endTimeOffset, long interval) {
        this(beginTimeOffset, endTimeOffset, interval, TRADING_DAY);
    }

    public FixRateConf(long interval) {
        this(secondsBeginOfDay, secondsEndOfDay, interval, TRADING_DAY);
    }

    public long getBeginTimeOffset() {
        return beginTimeOffset;
    }

    public void setBeginTimeOffset(int beginTimeOffset) {
        this.beginTimeOffset = beginTimeOffset;
    }

    public long getEndTimeOffset() {
        return endTimeOffset;
    }

    public void setEndTimeOffset(int endTimeOffset) {
        this.endTimeOffset = endTimeOffset;
    }

    public long getInterval() {
        return interval;
    }

    public void setInterval(long interval) {
        this.interval = interval;
    }

    public int getActionType() {
        return actionType;
    }

    public void setActionType(int actionType) {
        this.actionType = actionType;
    }

    /**
     * @param fromTime
     * @return Next valid trigger time based on action type;
     */
    public Date getNextValidTimeAfter(Date fromTime) {
//        switch (actionType) {
//            case EVERY_DAY:
//                return getNextValidTimeAfterFromEveryDay(fromTime);
//            case TRADING_DAY:
//                return getNextValidTimeAfterFromTradingDay(fromTime);
//            default:
//                return null;
//        }
        return getNextValidTimeAfterByDayHelper(fromTime);
    }

    public Date getNextValidTimeAfterByDayHelper(Date fromTime) {
        Date nextValidTime = null;
        Date nextValidDay = null;
        if (!dayHelper.isValidDay(fromTime)) {
            nextValidTime = new Date(dayHelper.nextValidDayZero(fromTime).getTime() + beginTimeOffset * 1000L);
        } else {
            Calendar itemDay = Calendar.getInstance();
            itemDay.setTime(fromTime);
            itemDay.set(Calendar.HOUR_OF_DAY, 0);
            itemDay.set(Calendar.MINUTE, 0);
            itemDay.set(Calendar.SECOND, 0);
            itemDay.set(Calendar.MILLISECOND, 0);
            itemDay.add(Calendar.SECOND, beginTimeOffset);
            Date beginTimeToday = itemDay.getTime();

            // get the endtime of this day
            itemDay.set(Calendar.HOUR_OF_DAY, 0);
            itemDay.set(Calendar.MINUTE, 0);
            itemDay.set(Calendar.SECOND, 0);
            itemDay.set(Calendar.MILLISECOND, 0);
            itemDay.add(Calendar.SECOND, endTimeOffset);
            Date endTimeToday = itemDay.getTime();

            if (fromTime.before(beginTimeToday)) {
                nextValidTime = beginTimeToday;
            } else {
                // ensure that the next tirgger time satisfying the interval rules, i.e., rounding
                long gapMilliseconds = interval * 1000 - (fromTime.getTime() - beginTimeToday.getTime()) % (interval * 1000);
                Date nextTimeNoGapFixRate = new Date(fromTime.getTime() + gapMilliseconds);
                nextValidTime = nextTimeNoGapFixRate.after(endTimeToday) ? new Date(dayHelper.nextValidDayZero(fromTime).getTime() + beginTimeOffset * 1000L) : nextTimeNoGapFixRate;
            }
        }
        return nextValidTime;
    }

    /**
     * @param fromTime
     * @return Next valid time from following all days;
     */
    public Date getNextValidTimeAfterFromEveryDay(Date fromTime) {
        Date nextValidTime = null;

        // get the begintime of this day
        Calendar itemDay = Calendar.getInstance();
        itemDay.setTime(fromTime);
        itemDay.set(Calendar.HOUR_OF_DAY, 0);
        itemDay.set(Calendar.MINUTE, 0);
        itemDay.set(Calendar.SECOND, 0);
        itemDay.set(Calendar.MILLISECOND, 0);
        itemDay.add(Calendar.SECOND, beginTimeOffset);
        Date beginTimeToday = itemDay.getTime();

        // get the endtime of this day
        itemDay.set(Calendar.HOUR_OF_DAY, 0);
        itemDay.set(Calendar.MINUTE, 0);
        itemDay.set(Calendar.SECOND, 0);
        itemDay.set(Calendar.MILLISECOND, 0);
        itemDay.add(Calendar.SECOND, endTimeOffset);
        Date endTimeToday = itemDay.getTime();
        if (fromTime.before(beginTimeToday)) {
            nextValidTime = beginTimeToday;
        } else {
            // ensure that the next tirgger time satisfying the interval rules, i.e., rounding
            long gapMilliseconds = interval * 1000 - (fromTime.getTime() - beginTimeToday.getTime()) % (interval * 1000);
            Date nextTimeMatchInterval = new Date(fromTime.getTime() + gapMilliseconds);
            nextValidTime = nextTimeMatchInterval.after(endTimeToday) ? new Date(beginTimeToday.getTime() + secondsEndOfDay * 1000) : nextTimeMatchInterval;
        }
        return nextValidTime;
    }

    /**
     * @param fromTime
     * @return Next valid time in following trading days
     */
    public Date getNextValidTimeAfterFromTradingDay(Date fromTime) {
        Date nextValidTradingTime = null;
        Date nextValidTime = getNextValidTimeAfterFromEveryDay(fromTime);
        if (TradingDayUtils.isTradingDayByTreeMap(nextValidTime)) {
            nextValidTradingTime = nextValidTime;
        } else {
            nextValidTradingTime = new Date(TradingDayUtils.getNextTradingDayZeroByTreeMap(fromTime).getTime() + beginTimeOffset * 1000L);
        }
        return nextValidTradingTime;
    }

    @Override
    public String toString() {
        return "FixRateConf{" +
                "beginTime=" + beginTimeOffset +
                ", endTime=" + endTimeOffset +
                ", interval=" + interval +
                ", actionType=" + actionType +
                ", beginDate=" + new Date(beginDateTime) +
                ", endDate=" + new Date(endDateTime) +
                '}';
    }
}
