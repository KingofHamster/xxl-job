package com.xxl.job.admin.core.util;

import com.xxl.job.admin.core.conf.XxlJobAdminConfig;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.SortedSet;
import java.util.TreeSet;


public class TradingDayUtils {
    
    static TreeSet<Integer> tradingDaysTreeMap;
    static SimpleDateFormat yyyyMMddDateFormat = new SimpleDateFormat("yyyyMMdd");

    public static final long secondsOfMinite = 60;
    
    public static final long secondsOfHour = 60 * secondsOfMinite;
    
    public static final long secondsOfDay = 24 * secondsOfHour;
    
    
    public static void initTradingDaysTreeMap() throws SQLException {
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
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        }
    }
    
    public static boolean isTradingDayBySQL(Date date) throws SQLException {
        
        
        String sql = "select count(*) as ROW_COUNT from GIFTS_UT.TRDCALENDAR where PHYDATE = " + yyyyMMddDateFormat.format(date) + " and " + "MARKETS is not null";

        PreparedStatement preparedStatement = null;
        boolean isTradingDay = false;
        ResultSet resultSet = null;
        Connection connection = null;
        try {
            connection = XxlJobAdminConfig.getAdminConfig().getDataSource().getConnection();
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            isTradingDay = resultSet.getInt("ROW_COUNT") > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
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
        try {
            initTradingDaysTreeMap();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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
        // if there is not a valid following trading day in the database, set the next trigger day to 9999/1/1;
        return nextTradingDay != null ? nextTradingDay : new Date(9999, Calendar.JANUARY, 1);
    }
    
    public static boolean isTradingDayByTreeMap(Date date) {
        try {
            initTradingDaysTreeMap();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return tradingDaysTreeMap.contains(Integer.parseInt(yyyyMMddDateFormat.format(date)));
    }
}
