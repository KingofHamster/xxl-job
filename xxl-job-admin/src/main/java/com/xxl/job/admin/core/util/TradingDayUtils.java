package com.xxl.job.admin.core.util;

import com.xxl.job.admin.core.conf.XxlJobAdminConfig;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.SortedSet;
import java.util.TreeSet;


public class TradingDayUtils {
    
    static TreeSet<Integer> tradingDaysTreeMap;
    static SimpleDateFormat yyyyMMddDateFormat = new SimpleDateFormat("yyyyMMdd");

    public static final long secondsOfMinite = 60;
    
    public static final long secondsOfHour = 60 * secondsOfMinite;
    
    public static final long secondsOfDay = 24 * secondsOfHour;
    
    
    public static void initTradingDaysTreeMap() {
        if (tradingDaysTreeMap != null && tradingDaysTreeMap.last() >= Integer.parseInt(yyyyMMddDateFormat.format(new Date(new Date().getTime() + 30L * 24 * 60 * 60 * 1000)))) {
            return;
        }
        tradingDaysTreeMap = new TreeSet<>();
        String sql = "select PHYDATE from GIFTS_UT.TRDCALENDAR where MARKETS is not null";
        
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try (Connection connection = XxlJobAdminConfig.getAdminConfig().getDataSource().getConnection()) {
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                tradingDaysTreeMap.add(resultSet.getInt("PHYDATE"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    public static boolean isTradingDayBySQL(Date date) {
        
        
        String sql = "select count(*) as ROW_COUNT from GIFTS_UT.TRDCALENDAR where PHYDATE = " + yyyyMMddDateFormat.format(date) + " and " + "MARKETS is not null";

        PreparedStatement preparedStatement = null;
        boolean isTradingDay = false;
        ResultSet resultSet = null;
        try {
            Connection connection = XxlJobAdminConfig.getAdminConfig().getDataSource().getConnection();
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            isTradingDay = resultSet.getInt("ROW_COUNT") > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return isTradingDay;
    }

    /**
     * 
     * @param fromDate
     * @return The next trading day from the given fromDate using TreeMap with time complexity being log(n);
     */
    public static Date getNextTradingDayZeroByTreeMap(Date fromDate) {
        // init tradingDaysTreeMap if necessary
        initTradingDaysTreeMap();
        // Pre-assume the NextTradingDay is tomorrow, and correct by the treeMap
        SortedSet<Integer> tailSet = tradingDaysTreeMap.tailSet(Integer.parseInt(yyyyMMddDateFormat.format(new Date(fromDate.getTime() + secondsOfDay * 1000))));
        Date nextTradingDay = null;
        if (!tailSet.isEmpty()) {
            try {
                nextTradingDay = yyyyMMddDateFormat.parse(tailSet.first().toString());
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }
        return nextTradingDay;
    }
    
    public static boolean isTradingDayByTreeMap(Date date) {
        initTradingDaysTreeMap();
        return tradingDaysTreeMap.contains(Integer.parseInt(yyyyMMddDateFormat.format(date)));
    }
}
