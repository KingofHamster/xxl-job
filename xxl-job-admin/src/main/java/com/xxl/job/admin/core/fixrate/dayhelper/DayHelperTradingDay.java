package com.xxl.job.admin.core.fixrate.dayhelper;

import com.xxl.job.admin.core.util.TradingDayUtils;

import java.util.Date;

public class DayHelperTradingDay implements DayHelper{
    @Override
    public boolean isValidDay(Date date) {
        return TradingDayUtils.isTradingDayByTreeMap(date);
    }

    @Override
    public Date nextValidDayZero(Date fromDate) {
        return TradingDayUtils.getNextTradingDayZeroByTreeMap(fromDate);
    }
}
